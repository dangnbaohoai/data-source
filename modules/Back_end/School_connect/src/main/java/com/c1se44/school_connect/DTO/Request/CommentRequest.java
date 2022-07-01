package com.c1se44.school_connect.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {
	private Long postId;
	private String content;
	private String imageComment;
	private LocalDateTime timeComment;
	private Long replyId;
	
}
