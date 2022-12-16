                                 package com.spring.gugu.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.spring.gugu.common.dto.PageRequestDTO;
import com.spring.gugu.common.dto.PageResultDTO;
import com.spring.gugu.dto.DiaryDTO;
import com.spring.gugu.entity.Diary;
import com.spring.gugu.repository.DiaryRepository;

@Service
public class DiaryServiceImpl implements DiaryService {
	
	@Autowired
	DiaryRepository diaryRepo;
	
	@Autowired
	FileService fileService;

	@Override
	public Long insertDiary(String title, String content) {
		DiaryDTO diaryDTO = new DiaryDTO(title, content);
		Diary diary = DiaryDTO.dtoToEntity(diaryDTO);
		return diaryRepo.save(diary).getNo();
	}

	@Override
	public DiaryDTO getDiaryByNo(Long diaryNo) throws NoSuchElementException {
		// diaryRepo.findById(diaryNo)로 불러온 결과가 null일 수도 있기 때문에 optional 객체를 사용해서 예외처리를 해준 것
		// orElseThrow를 통해 예외 처리를 해주었기 때문에 optional type이 아닌 Diary 타입으로 값을 반환받을 수 있음
		Diary diary = diaryRepo.findById(diaryNo).orElseThrow(NoSuchElementException::new);
		DiaryDTO diaryDTO = Diary.entityToDTO(diary);
		return diaryDTO;
	}

	@Override
	public void insertBatchData(List<DiaryDTO> diaryList) {
		List<Diary> entities = diaryList.stream()
										.map(diaryDTO -> diaryDTO.dtoToEntity(diaryDTO))
										.collect(Collectors.toList());
		diaryRepo.saveAll(entities);	
	}

	public PageResultDTO<DiaryDTO, Diary> getList(PageRequestDTO requestDTO) {
		Pageable pageable = requestDTO.getPageable();
		
		Page<Diary> result = diaryRepo.findAll(pageable);
		
		// Diary 타입을 DiaryDTO 타입으로 변경해서  저장
		Function<Diary, DiaryDTO> fn = (diary -> diary.entityToDTO(diary));
		
		return new PageResultDTO<DiaryDTO, Diary>(result, fn);
	}

	@Override
	@Transactional
	public void DiaryDTOUpdate(Long diaryNo, String title, String content) throws NoSuchElementException {
		// TODO Auto-generated method stub
		Diary diary = diaryRepo.findById(diaryNo).orElseThrow(NoSuchElementException::new);
		diary.updateDiary(title, content);
	}

	@Override
	public void deleteDiary(Long diaryNo) {
		diaryRepo.deleteById(diaryNo);
	}
	
	@Override
	@Transactional
	public Long insertDiary(DiaryDTO diaryDTO) {
		// DTO로 넘어온 데이터를 Entity로 바꿔주고 DB에 insert를 한 후, diaryNo를 return
		// controller -> service -> repository로 DB에 변경
		Diary diary = DiaryDTO.dtoToEntity(diaryDTO);
		return diaryRepo.save(diary).getNo();
	}

	

}
