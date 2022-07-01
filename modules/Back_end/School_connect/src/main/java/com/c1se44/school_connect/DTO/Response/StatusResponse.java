package com.c1se44.school_connect.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusResponse {
	private int numberForum;
	private int numberUser;
	private int numberPosts;
	private int numberChatReport;
	private int numberMembers;
	private int numberMembersNotCensor;
	private int numberPostsNotCensor;
	private int numberPostsReport;
	private int numberCommentsReport;
	private int numberComments;
	private List<DataChartResponse> dataBlockChat=new ArrayList<>();
}
