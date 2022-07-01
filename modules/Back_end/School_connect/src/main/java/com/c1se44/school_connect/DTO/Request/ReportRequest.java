package com.c1se44.school_connect.DTO.Request;

import lombok.Data;

@Data

public class ReportRequest {
	private Long forumId;
	private String reason;
	private Long messageReport;
	private String userNameReport;
	private Long postId;
	private Long commentId;
	private Long roomChatId;

}
