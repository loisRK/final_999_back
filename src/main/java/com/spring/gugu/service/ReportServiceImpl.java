package com.spring.gugu.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.gugu.dto.ReportDTO;
import com.spring.gugu.entity.Report;
import com.spring.gugu.repository.ReportRepository;

@Service
public class ReportServiceImpl implements ReportService{
	
	@Autowired
	ReportRepository reportRepo;
	
	@Override
	@Transactional
	public void insertReport(ReportDTO reportDTO) {
		Report report = ReportDTO.dtoToEntity(reportDTO);
		
		Report prevReport = reportRepo.getByRoomNoAndReporterIdAndReportedId(reportDTO.getRoomNo(), reportDTO.getReporterId(), reportDTO.getReportedId());
		reportRepo.save(report);
	}

	@Override
	@Transactional
	public int getReportNum(Long roomNo, Long reportedId) {
		List<Report> reports = reportRepo.getByRoomNoAndReportedId(roomNo, reportedId);
		
		return reports.size();
	}

}
