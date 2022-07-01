package com.c1se44.school_connect.entity;

import com.c1se44.school_connect.DTO.Request.MessageChatRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "chat_message")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class chatMessageEntity extends baseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long messageId;
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "Fk_user_send_message"))
	private userEntity userSendMessageId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "Fk_chat_message"))
	private roomChatEntity roomChatMessageId;
	
	@Column(name = "message", columnDefinition = "TEXT")
	private String message;
	@Column(name = "image_message", columnDefinition = "TEXT")
	private String image;
	
	@OneToOne(mappedBy = "messageReport", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private reportChatEntity reportChatEntity;
	
	public chatMessageEntity(userEntity userSend, roomChatEntity roomChatEntity, MessageChatRequest messageChatRequest) {
		this.userSendMessageId = userSend;
		this.roomChatMessageId = roomChatEntity;
		this.message = messageChatRequest.getMessage();
		if (messageChatRequest.getImageMessage() != null) {
			this.image = messageChatRequest.getImageMessage();
		}
		
	}
}

