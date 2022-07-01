package com.c1se44.school_connect.DTO.Response;

import com.c1se44.school_connect.entity.roomChatEntity;
import com.c1se44.school_connect.entity.userEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomChatResponse extends baseResponse{
	private Long roomId;
	private String messageNew;
	private String statusRoom;
	private String userRecipient;
	private String statusNewMessage;
	private userResponse userResponse1;
	private userResponse userResponse2;
	public RoomChatResponse(roomChatEntity roomChat, userEntity user){
		this.roomId = roomChat.getRoomId();
		this.userResponse1=new userResponse(user);
		this.messageNew=roomChat.getNewMessage();
		if(roomChat.getStatusMessage()!=null){
			this.statusNewMessage =roomChat.getStatusMessage().toString();
		}
		this.userRecipient=roomChat.getMessageRecipient();
		this.statusRoom=roomChat.getStatus().toString();
		this.setCreateDate(roomChat.getCreateDate());
		this.setCreateBy(roomChat.getCreatedby());
		this.setModifiedDate(roomChat.getModifiedDate());
		this.setModifiedBy(roomChat.getModifiedBy());
	}
	public RoomChatResponse(roomChatEntity roomChatEntity,userResponse user1,userResponse user2){
		this.roomId = roomChatEntity.getRoomId();
		this.userResponse1=user1;
		this.userResponse2=user2;
		this.statusRoom=roomChatEntity.getStatus().toString();
		this.setCreateDate(roomChatEntity.getCreateDate());
		this.setCreateBy(roomChatEntity.getCreatedby());
		this.setModifiedDate(roomChatEntity.getModifiedDate());
		this.setModifiedBy(roomChatEntity.getModifiedBy());
	}
}
