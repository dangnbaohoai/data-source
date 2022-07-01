package com.c1se44.school_connect.repository;

import com.c1se44.school_connect.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface IReportRoomRepository extends JpaRepository<reportChatEntity,Long> {
	Page<reportChatEntity> findAllByStatus(StatusName status, Pageable pageable);
	Boolean existsByReportRoomIdAndStatus(Long id,StatusName status);
	Boolean existsByRoomChatReportIdAndStatus(roomChatEntity roomId,StatusName status);
	reportChatEntity findByReportRoomId(Long id);
	@Query(value = "select count(*) from report_room where status=?",nativeQuery = true)
	int countAll(String status);
	@Modifying
	@Transactional
	@Query(value = "delete from report_room where id=?", nativeQuery = true)
	void deleteByReportRoomId(Long reportRoomId);
	@Modifying
	@Query(value = "Update report_room Set status=? where id=? ", nativeQuery = true)
	@Transactional
	void UpdateStatusByReportRoomId(String status, Long reportRoomId);
	@Query(value = "select count(*) from report_room where status=? AND MONTH(create_date)=? and YEAR(create_date)=?",nativeQuery = true)
	int countAll(String status,int mont,int year);
	Boolean existsByMessageReport(chatMessageEntity message);
}
