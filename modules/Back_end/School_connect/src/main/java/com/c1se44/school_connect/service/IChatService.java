package com.c1se44.school_connect.service;

import com.c1se44.school_connect.entity.chatMessageEntity;
import com.c1se44.school_connect.entity.roomChatEntity;
import com.c1se44.school_connect.entity.userEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IChatService {
	chatMessageEntity save(chatMessageEntity chatMessageEntity);
	List<chatMessageEntity> findByRoomChatMessageId(roomChatEntity roomChatId, Pageable pageable);
	void deleteByChatId(Long messageId,userEntity userId);
	List<chatMessageEntity> findByRoomChatMessageIdAndIdChatBigThan(Long roomChatId,Long chatId);
	Boolean existsByMessageIdAndUserSendMessageId(Long chatId, userEntity user);
	
	chatMessageEntity findByMessageId(Long messageId);
	List<chatMessageEntity> findByRoomChatMessageIdAndIdChatBigThan2(roomChatEntity roomChatId,Long chatId);
}
