package com.c1se44.school_connect.service.impl;

import com.c1se44.school_connect.entity.StatusName;
import com.c1se44.school_connect.entity.roomChatEntity;
import com.c1se44.school_connect.entity.userEntity;
import com.c1se44.school_connect.repository.IRoomChatRepository;
import com.c1se44.school_connect.service.IRoomChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomChatService implements IRoomChatService {
	@Autowired
	IRoomChatRepository roomChatRepository;
	
	@Override
	public Boolean existsUserChatId1AndUserChatId2(Long user1, Long user2, Long user12, Long user21) {
		int count = roomChatRepository.existsUserChatId1AndUserChatId2(user1, user2, user12, user21);
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public roomChatEntity save(roomChatEntity roomChatEntity) {
		return roomChatRepository.save(roomChatEntity);
	}
	
	@Override
	public List<roomChatEntity> findUserChatIdAndStatus(StatusName status, userEntity userId1, Pageable pageable) {
		return roomChatRepository.findUserStatusChatId1orUserChatId22(status, userId1, userId1, pageable).getContent();
	}
	
	@Override
	public List<roomChatEntity> findUserChatId1orUserChatId22(userEntity userId1, Pageable pageable) {
		return roomChatRepository.findUserChatId1orUserChatId22(userId1,userId1,pageable).getContent();
	}
	
	@Override
	public List<roomChatEntity> findUserChatId1orUserChatId2(StatusName status, Long userId1, Pageable pageable) {
		return roomChatRepository.findUserChatId1orUserChatId2(status.toString(),userId1,userId1,pageable).getContent();
	}
	
	@Override
	public Boolean existsByRoomIdAndStatus(Long roomId, StatusName status) {
		return roomChatRepository.existsByRoomIdAndStatus(roomId,status);
	}
	
	@Override
	public roomChatEntity findByRoomIdAndStatus(Long roomId, StatusName status) {
		return roomChatRepository.findByRoomIdAndStatus(roomId,status);
	}
	
	@Override
	public void updateStatusById(StatusName status, Long roomId) {
		roomChatRepository.updateStatusById(status.toString(),roomId);
	}
	
	@Override
	public List<roomChatEntity> findAllByStatus(StatusName status, Pageable pageable) {
		return roomChatRepository.findAllByStatus(status,pageable).getContent();
	}
	
	@Override
	public int countAllByStatus(StatusName status) {
		return roomChatRepository.countAllByStatus(status);
	}
	
	@Override
	public void updateStatusMessageByStatusAndUserIdRoomNot(StatusName statusSet, StatusName status, String userName,Long roomId) {
		roomChatRepository.updateStatusMessageByStatusAndUserIdAndIdNot(statusSet.toString(),status.toString(),userName,roomId);
	}
	
	@Override
	public roomChatEntity findUserChatId1AndUserChatId2(Long user1, Long user2, Long user12, Long user21) {
		return roomChatRepository.findUserChatId1AndUserChatId2(user1,user2,user12,user21);
	}
	
	@Override
	public void updateStatusMessageByStatusAndUserId(StatusName statusSet, StatusName status, String userName) {
		roomChatRepository.updateStatusMessageByStatusAndUserId(statusSet.toString(), status.toString(),userName);
	}
	
	@Override
	public int countAllByStatusAndUser(StatusName statusMessage, String userName) {
		return roomChatRepository.countAllByStatusAndUserName(statusMessage.toString(),userName);
	}
	
	@Override
	public Boolean existsByMessageRecipientAndStatusMessage(String userName, StatusName status) {
		return roomChatRepository.existsByMessageRecipientAndStatusMessage(userName,status);
	}
	
	@Override
	public List<roomChatEntity> findByStatusMessageAndMessageRecipient(StatusName status, String messageRecipient) {
		return roomChatRepository.findByStatusMessageAndMessageRecipientOrderByModifiedDateAsc(status,messageRecipient);
	}
	
	@Override
	public List<roomChatEntity> findUserStatusAndChatId1orUserChatId22Like(StatusName status, List<userEntity> userName, userEntity userId1, userEntity userId2, Pageable pageable) {
		return roomChatRepository.findStatusUserChatId1orUserChatId22Like(status,userName,userId1,userId2,pageable).getContent();
	}
	
	@Override
	public List<roomChatEntity> findUserChatId1orUserChatId22Like(List<userEntity> userName, userEntity userId1, userEntity userId2, Pageable pageable) {
		return roomChatRepository.findUserChatId1orUserChatId22Like(userName,userId1,userId2,pageable).getContent();
	}
	
	@Override
	public void updateStatusMessageByStatusAndRoomId(String statusSet, String status, Long RoomId) {
		roomChatRepository.updateStatusMessageByStatusAndRoomId(statusSet,status,RoomId);
	}
	
	
}
