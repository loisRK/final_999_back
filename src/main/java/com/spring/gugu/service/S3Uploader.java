package com.spring.gugu.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class S3Uploader {
	
	private final AmazonS3Client amazonS3Client;
	 
	@Value("${cloud.aws.s3.bucket}")
	private String bucket;
	
	public String uploadFiles(MultipartFile oneFile, String uploadDir) throws IOException {
		System.out.println("fileupload test#########" + oneFile);
		File uploadFile = convert(oneFile)  // 파일 변환할 수 없으면 에러
                .orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));
		// multipartFile -> File type 변환
//		File uploadFile = new File(oneFile.getOriginalFilename());
//		oneFile.transferTo(uploadFile);
		System.out.println("transferred file ######"+uploadFile);
		return upload(uploadFile, uploadDir);
	}

	public String upload(File uploadFile, String uploadDir) {
        String fileName = uploadDir + "/" + UUID.randomUUID() + uploadFile.getName();   // S3에 저장된 파일 이름
        String uploadImageUrl = putS3(uploadFile, fileName); // s3로 업로드
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    // S3로 업로드
    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // 로컬에 저장된 이미지 지우기
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            System.out.println("File delete success");
            return;
        }
        System.out.println("File delete fail");
    }

    // 로컬에 파일 업로드 하기
    private Optional<File> convert(MultipartFile file) throws IOException {
    	
    	// 폴더가 없다면 새로 생성해 주기
    	String path = "C:\\dev\\gugu\\img"; //폴더 경로
    	File folder = new File(path);

    	// 해당 디렉토리가 없을경우 디렉토리를 생성합니다.
    	if (!folder.exists()) {
    		folder.mkdir(); //폴더 생성합니다.
    	}
    	// s3 업로드 과정에서 로컬에 파일이 저장되어 있지 않으면 에러가 발생하므로 임시 저장소를 만들어 저장한 뒤 s3 업로드 종료 후 파일을 삭제한다.
        File convertFile = new File("C:\\dev\\gugu\\img\\" + file.getOriginalFilename());
        if (convertFile.createNewFile()) { // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못되었다면 생성 불가능)
            try (FileOutputStream fos = new FileOutputStream(convertFile)) { // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }
    
//    public void deleteFile(String fileLink) {
//    	String[] fileLinkSplit = fileLink.split("/gugu-post/");
//    	System.out.println("fileLink 분리#####"+fileLinkSplit);
////    	String filePath = 
////    	amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, "/gugu-post/"+fileLinkSplit[1]));
//    }
}
