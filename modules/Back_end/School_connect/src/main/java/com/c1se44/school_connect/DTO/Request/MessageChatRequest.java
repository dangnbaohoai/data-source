package com.c1se44.school_connect.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageChatRequest {
	private String message;
	private String imageMessage;
	private Long roomChatId;
	
}
