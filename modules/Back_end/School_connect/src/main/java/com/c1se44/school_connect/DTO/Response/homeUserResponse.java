package com.c1se44.school_connect.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class homeUserResponse {
	private List<ForumResponse> forumResponseList=new ArrayList<>();
	private List<PostResponse> postResponseList=new ArrayList<>();
}
