package com.c1se44.school_connect.service.impl;

import com.c1se44.school_connect.entity.StatusName;
import com.c1se44.school_connect.entity.chatMessageEntity;
import com.c1se44.school_connect.entity.reportChatEntity;
import com.c1se44.school_connect.entity.roomChatEntity;
import com.c1se44.school_connect.repository.IReportRoomRepository;
import com.c1se44.school_connect.service.IReportRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportRoomService implements IReportRoomService {
	@Autowired
	IReportRoomRepository reportRoomRepository;
	@Override
	public reportChatEntity save(reportChatEntity reportChatEntity) {
		return reportRoomRepository.save(reportChatEntity);
	}
	
	@Override
	public List<reportChatEntity> findAll(Pageable pageable) {
		return reportRoomRepository.findAll(pageable).getContent();
	}
	
	@Override
	public void deleteByReportRoomId(Long reportRoomId) {
		reportRoomRepository.deleteByReportRoomId(reportRoomId);
	}
	
	@Override
	public int countAll(StatusName status) {
		return reportRoomRepository.countAll(status.toString());
	}
	
	@Override
	public Boolean existsByReportRoomId(Long id) {
		return reportRoomRepository.existsByReportRoomIdAndStatus(id,StatusName.have_not_been_censored);
	}
	
	@Override
	public Boolean existsByReportRoomIdAndStatus(Long id, StatusName status) {
		return reportRoomRepository.existsByReportRoomIdAndStatus(id,status);
	}
	
	@Override
	public reportChatEntity findByReportRoomId(Long id) {
		return reportRoomRepository.findByReportRoomId(id);
	}
	
	@Override
	public List<reportChatEntity> findAllByStatus(StatusName status, Pageable pageable) {
		return reportRoomRepository.findAllByStatus(status, pageable).getContent();
	}
	
	@Override
	public void UpdateStatusByReportRoomId(StatusName status, Long reportRoomId) {
		reportRoomRepository.UpdateStatusByReportRoomId(status.toString(),reportRoomId);
		
	}
	
	@Override
	public int countAll(StatusName status, int mont, int year) {
		return reportRoomRepository.countAll(status.toString(), mont, year);
	}
	
	@Override
	public Boolean existsByRoomChatReportId(roomChatEntity roomId) {
		return reportRoomRepository.existsByRoomChatReportIdAndStatus(roomId,StatusName.have_not_been_censored);
	}
	
	@Override
	public Boolean existsByRoomChatReportIdAndMessageReport( chatMessageEntity message) {
		return reportRoomRepository.existsByMessageReport(message);
	}
}
