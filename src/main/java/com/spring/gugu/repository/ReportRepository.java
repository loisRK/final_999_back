package com.spring.gugu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.gugu.entity.Report;


public interface ReportRepository extends JpaRepository<Report, Long>{

	@Query(value="SELECT r FROM Report r WHERE r.roomNo = :roomNo AND r.reportedId = :reported")
	List<Report> getByRoomNoAndReportedId(@Param("roomNo") Long roomNo, @Param("reported") Long reportedId);

	@Query(value="SELECT r FROM Report r WHERE r.roomNo = :roomNo AND r.reporterId = :reporter AND r.reportedId = :reported")
	Report getByRoomNoAndReporterIdAndReportedId(@Param("roomNo") Long roomNo, @Param("reporter") Long reporterId, @Param("reported") Long reportedId);

}
