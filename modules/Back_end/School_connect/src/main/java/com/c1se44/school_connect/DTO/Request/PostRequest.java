package com.c1se44.school_connect.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
	private List<Long> forumId = new ArrayList<>();
	private Long forumIdForUser;
	private Long postId;
	private Long userId;
	private String content;
	private LocalDate dateOfEvent;
	private String addressOfEvent;
	private String nameEvent;
		private Time timeOfEvent;
	private List<String> linkImage = new ArrayList<>();
}
