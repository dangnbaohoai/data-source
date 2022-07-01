package com.c1se44.school_connect.service;

import com.c1se44.school_connect.entity.StatusName;
import com.c1se44.school_connect.entity.roomChatEntity;
import com.c1se44.school_connect.entity.userEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IRoomChatService {
	Boolean existsUserChatId1AndUserChatId2(Long user1,Long user2,Long user12,Long user21);
	roomChatEntity save(roomChatEntity roomChatEntity);
	List<roomChatEntity> findUserChatIdAndStatus(StatusName status, userEntity userId1, Pageable pageable);
	List<roomChatEntity> findUserChatId1orUserChatId22(userEntity userId1, Pageable pageable);
	
	List<roomChatEntity> findUserChatId1orUserChatId2(StatusName status,  Long userId1, Pageable pageable);
	Boolean existsByRoomIdAndStatus(Long roomId,StatusName status);
	roomChatEntity findByRoomIdAndStatus(Long roomId,StatusName status);
	void updateStatusById(StatusName status,Long roomId);
	List<roomChatEntity> findAllByStatus(StatusName status,Pageable pageable);
	int countAllByStatus(StatusName status);
	void updateStatusMessageByStatusAndUserId(StatusName statusSet,StatusName status,String userName);
	int countAllByStatusAndUser(StatusName statusMessage, String userName);
	Boolean existsByMessageRecipientAndStatusMessage(String userName,StatusName status);
	List<roomChatEntity> findByStatusMessageAndMessageRecipient(StatusName status,String messageRecipient);
	List<roomChatEntity> findUserStatusAndChatId1orUserChatId22Like(StatusName status,List<userEntity> userName,userEntity userId1,userEntity userId2, Pageable pageable);
	List<roomChatEntity> findUserChatId1orUserChatId22Like(List<userEntity> userName, userEntity userId1, userEntity userId2, Pageable pageable);
	
	void updateStatusMessageByStatusAndRoomId( String statusSet, String status, Long roomId);
	void updateStatusMessageByStatusAndUserIdRoomNot(StatusName statusSet, StatusName status, String userName,Long roomId);
	roomChatEntity findUserChatId1AndUserChatId2(Long user1, Long user2, Long user12, Long user21);
}
