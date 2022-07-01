package com.c1se44.school_connect.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForumRequest {
	private Long forumId;
	private Long censerId;
	private String censerName;
	private String nameForum;
	private String description;
	private String coverImage;
}
