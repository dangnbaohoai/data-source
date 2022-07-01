package com.c1se44.school_connect.service;

import com.c1se44.school_connect.entity.StatusName;
import com.c1se44.school_connect.entity.chatMessageEntity;
import com.c1se44.school_connect.entity.reportChatEntity;
import com.c1se44.school_connect.entity.roomChatEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IReportRoomService {
	reportChatEntity save(reportChatEntity reportChatEntity);
	List<reportChatEntity> findAll(Pageable pageable);
	void deleteByReportRoomId(Long reportRoomId);
	int countAll(StatusName status);
	Boolean existsByReportRoomId(Long id);
	Boolean existsByReportRoomIdAndStatus(Long id,StatusName status);
	reportChatEntity findByReportRoomId(Long id);
	List<reportChatEntity> findAllByStatus(StatusName status, Pageable pageable);
	void UpdateStatusByReportRoomId(StatusName status, Long reportRoomId);
	int countAll(StatusName status,int mont,int year);
	Boolean existsByRoomChatReportId(roomChatEntity roomId);
	Boolean existsByRoomChatReportIdAndMessageReport( chatMessageEntity message);
}
