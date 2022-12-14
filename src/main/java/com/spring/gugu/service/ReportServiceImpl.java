package com.spring.gugu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.gugu.dto.ReportDTO;
import com.spring.gugu.entity.Report;
import com.spring.gugu.repository.ReportRepository;

@Service
public class ReportServiceImpl implements ReportService{
	
	@Autowired
	ReportRepository reportRepo;
	
	public void insertReport(ReportDTO reportDTO) {
		Report report = ReportDTO.dtoToEntity(reportDTO);
		
		reportRepo.save(report);
	}

}
