package com.spring.gugu.service;

import com.spring.gugu.dto.ReportDTO;

public interface ReportService {
	
	public void insertReport(ReportDTO reportDTO);
	
	public int getReportNum(Long roomNo, Long reportedId);
}
