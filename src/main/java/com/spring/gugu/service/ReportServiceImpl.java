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
	public int insertReport(ReportDTO reportDTO) {
		Report report = ReportDTO.dtoToEntity(reportDTO);
		
		// 이미 같은 방에서 같은 사람이 같은 사람을 신고했는지 확인
		Report prevReport = reportRepo.getByRoomNoAndReporterIdAndReportedId(reportDTO.getRoomNo(), reportDTO.getReporterId(), reportDTO.getReportedId());
		System.out.println("######### prevReport : " + prevReport);
		
		// 같은 정보가 없으면 신고 테이블에 내용 저장
		if(prevReport == null) {
			reportRepo.saveAndFlush(report);
		}
		
		return getReportNum(reportDTO.getRoomNo(), reportDTO.getReportedId());
	}

	@Override
	public int getReportNum(Long roomNo, Long reportedId) {
		List<Report> reports = reportRepo.getByRoomNoAndReportedId(roomNo, reportedId);
		System.out.println("###### reports : " + reports);
		
		return reports.size();
	}

}
