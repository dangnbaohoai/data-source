package com.c1se44.school_connect.repository;

import com.c1se44.school_connect.entity.StatusName;
import com.c1se44.school_connect.entity.roomChatEntity;
import com.c1se44.school_connect.entity.userEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface IRoomChatRepository extends JpaRepository<roomChatEntity, Long> {
	@Query(value = "select  count(*) from room_chat where (user_id1=? and user_id2=?) or (user_id1=? and user_id2=?)", nativeQuery = true)
	int existsUserChatId1AndUserChatId2(Long user1, Long user2, Long user12, Long user21);
	@Query(value = "select  * from room_chat where (user_id1=? and user_id2=?) or (user_id1=? and user_id2=?)", nativeQuery = true)
	roomChatEntity findUserChatId1AndUserChatId2(Long user1, Long user2, Long user12, Long user21);
	// oder with create_date
	@Query(value = "SELECT * FROM room_chat r WHERE r.status_room = :status AND  (r.user_id1=:user_id1 or r.user_id2=:user_id2)",
				countQuery = "SELECT count(*) FROM room_chat r WHERE r.status_room = :status AND  (r.user_id1=:user_id1 or r.user_id2=:user_id2)",
				nativeQuery = true)
	Page<roomChatEntity> findUserChatId1orUserChatId2(@Param("status") String status, @Param("user_id1") Long userId1, @Param("user_id2") Long userId2, Pageable pageable);
	
	// oder with createDate
	@Query(value = "SELECT r FROM roomChatEntity r WHERE  (r.userChatId1=:user_id1 or r.userChatId2=:user_id2)")
	Page<roomChatEntity> findUserChatId1orUserChatId22( @Param("user_id1") userEntity userId1, @Param("user_id2") userEntity userId2, Pageable pageable);
	
	@Query(value = "SELECT r FROM roomChatEntity r WHERE r.status = :status AND  (r.userChatId1=:user_id1 or r.userChatId2=:user_id2)")
	Page<roomChatEntity> findUserStatusChatId1orUserChatId22(@Param("status") StatusName status, @Param("user_id1") userEntity userId1, @Param("user_id2") userEntity userId2, Pageable pageable);
	
	@Query(value = "SELECT r FROM roomChatEntity r WHERE r.status = :status AND (r.userChatId1 IN (:recipient) OR r.userChatId2 IN (:recipient)) AND (r.userChatId1=:user_id1 or r.userChatId2=:user_id2)")
	Page<roomChatEntity> findStatusUserChatId1orUserChatId22Like(@Param("status") StatusName status, @Param("recipient") List<userEntity> userName, @Param("user_id1") userEntity userId1, @Param("user_id2") userEntity userId2, Pageable pageable);
	@Query(value = "SELECT r FROM roomChatEntity r WHERE (r.userChatId1 IN (:recipient) OR r.userChatId2 IN (:recipient)) AND (r.userChatId1=:user_id1 or r.userChatId2=:user_id2)")
	Page<roomChatEntity> findUserChatId1orUserChatId22Like(@Param("recipient") List<userEntity> userName, @Param("user_id1") userEntity userId1, @Param("user_id2") userEntity userId2, Pageable pageable);
	
	Page<roomChatEntity> findAllByStatus(StatusName status, Pageable pageable);
	
	int countAllByStatus(StatusName status);
	
	Boolean existsByRoomIdAndStatus(Long roomId, StatusName status);
	
	roomChatEntity findByRoomIdAndStatus(Long roomId, StatusName status);
	
	@Modifying
	@Transactional
	@Query(value = "update room_chat set status_room=? where id=?", nativeQuery = true)
	void updateStatusById(String status, Long roomId);
	
	@Modifying
	@Transactional
	@Query(value = "update room_chat r set r.status_message= :statusSet  WHERE r.status_message = :status AND  r.message_recipient= :userName", nativeQuery = true)
	void updateStatusMessageByStatusAndUserId(@Param("statusSet") String statusSet, @Param("status") String status, @Param("userName") String userId1);
	@Modifying
	@Transactional
	@Query(value = "update room_chat r set r.status_message= :statusSet  WHERE r.status_message = :status AND  r.message_recipient= :userName AND r.id!=:roomId", nativeQuery = true)
	void updateStatusMessageByStatusAndUserIdAndIdNot(@Param("statusSet") String statusSet, @Param("status") String status, @Param("userName") String userId1, @Param("roomId") Long roomId);
	
	@Modifying
	@Transactional
	@Query(value = "update room_chat r set r.status_message= :statusSet  WHERE r.status_message = :status AND  r.id= :roomId", nativeQuery = true)
	void updateStatusMessageByStatusAndRoomId(@Param("statusSet") String statusSet, @Param("status") String status, @Param("roomId") Long roomId);
	
	@Query(value = "select count(*) from room_chat r where r.status_message=? AND r.message_recipient=?", nativeQuery = true)
	int countAllByStatusAndUserName(String statusMessage, String userName);
	Boolean existsByMessageRecipientAndStatusMessage(String userName,StatusName status);
	
	List<roomChatEntity> findByStatusMessageAndMessageRecipientOrderByModifiedDateAsc(StatusName status,String messageRecipient);
}
