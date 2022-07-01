package com.c1se44.school_connect.service.impl;

import com.c1se44.school_connect.entity.chatMessageEntity;
import com.c1se44.school_connect.entity.roomChatEntity;
import com.c1se44.school_connect.entity.userEntity;
import com.c1se44.school_connect.repository.IChatMessageRepository;
import com.c1se44.school_connect.service.IChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService implements IChatService {
	@Autowired
	IChatMessageRepository chatMessageRepository;
	
	@Override
	public chatMessageEntity save(chatMessageEntity chatMessageEntity) {
		return chatMessageRepository.save(chatMessageEntity);
	}
	
	@Override
	public List<chatMessageEntity> findByRoomChatMessageId(roomChatEntity roomChatId, Pageable pageable) {
		return chatMessageRepository.findByRoomChatMessageId(roomChatId,pageable).getContent();
	}
	
	@Override
	public void deleteByChatId(Long messageId, userEntity userId) {
		chatMessageRepository.deleteByMessageIdAndUserSendMessageId(messageId,userId);
	}
	
	@Override
	public List<chatMessageEntity> findByRoomChatMessageIdAndIdChatBigThan(Long roomChatId, Long chatId) {
		return chatMessageRepository.findByRoomChatMessageIdAndIdChatBigThan(roomChatId,chatId);
	}
	
	@Override
	public Boolean existsByMessageIdAndUserSendMessageId(Long chatId, userEntity user) {
		return chatMessageRepository.existsByMessageIdAndUserSendMessageId(chatId,user);
	}
	
	@Override
	public chatMessageEntity findByMessageId(Long messageId) {
		return chatMessageRepository.findByMessageId(messageId);
	}
	
	@Override
	public List<chatMessageEntity> findByRoomChatMessageIdAndIdChatBigThan2(roomChatEntity roomChatId, Long chatId) {
		return chatMessageRepository.findByRoomChatMessageIdAndIdChatBigThan2(roomChatId,chatId);
	}
}
