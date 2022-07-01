package com.c1se44.school_connect.DTO.Response;

import com.c1se44.school_connect.entity.chatMessageEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse extends  baseResponse{
	private  userResponse userSend;
	private Long messageId;
	private String message;
	private String imageMessage;
	public ChatResponse(userResponse user, chatMessageEntity chatMessage){
		this.userSend=user;
		this.message=chatMessage.getMessage();
		this.messageId=chatMessage.getMessageId();
		if(chatMessage.getImage()!=null){
			this.imageMessage=chatMessage.getImage();
		}
		this.setCreateBy(chatMessage.getCreatedby());
		this.setCreateDate(chatMessage.getCreateDate());
		this.setModifiedBy(chatMessage.getModifiedBy());
		this.setModifiedDate(chatMessage.getModifiedDate());
	}
}
