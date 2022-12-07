package com.spring.gugu.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spring.gugu.dto.FileDTO;
import com.spring.gugu.service.DiaryServiceImpl;
import com.spring.gugu.service.FileServiceImpl;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin(origins = {"*"})
public class FileController {
	
	@Autowired
	FileServiceImpl fileService;
	
	@Autowired
	DiaryServiceImpl diaryService;
	
	@GetMapping(path = "/file-download")
	public ResponseEntity<Resource> downloadFile(@RequestParam("fileName") String fileName){
		System.out.println("fileName : " + fileName);
		// File Resource 객체 - 저장할 파일의 경로를 지정
		Path path = Paths.get("C:\\dev2\\05.spring\\step15_Diary\\files\\" + fileName);
		System.out.println("path: " + path);
		// 위에 지정한 경로로 Files를 이용해 resource 객체로 변환하여 저장
		Resource resource = null;
		try{
			resource = new InputStreamResource(Files.newInputStream(path));
			System.out.println(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// 다운로드시, HTTP Header에 다운로드 파일 정보 전달
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDisposition(ContentDisposition.builder("attachment")
														.filename(fileName)
														.build());
		
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	}
	
	
	@GetMapping("/fileInfo")
	public List<FileDTO> getFileByDiaryno(@RequestParam("diaryno") Long diaryNo) {
		List<FileDTO> fileDTOs = fileService.getByDiaryNo(diaryNo);
		System.out.println(fileDTOs);
		return fileDTOs;
	}
	
	@PostMapping("/fileUpload")
	public void fileUpload(@RequestParam("file") List<MultipartFile> files) {
		
	}
	
	@GetMapping("/deleteFile")
	public void deleteFile(@RequestParam("fileNo") Long fileNo) {
		fileService.deleteFile(fileNo);
	}
}
