package com.spring.gugu.dto;

import com.spring.gugu.entity.Report;
import com.spring.gugu.entity.Room;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ReportDTO {

	private long roomNo;
	private String message;
	private long reporterId;
	private long reportedId;
	
	public static Report dtoToEntity(ReportDTO reportDTO) {
		Report report = Report.builder()
							  .roomNo(reportDTO.getRoomNo())
							  .message(reportDTO.getMessage())
							  .reporterId(reportDTO.getReporterId())
							  .reportedId(reportDTO.getReportedId())
							  .build();
		return report;
	}
	
}
