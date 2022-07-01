package com.c1se44.school_connect.DTO.Response;

import com.c1se44.school_connect.entity.forumsEntity;
import com.c1se44.school_connect.entity.userEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForumResponse extends baseResponse{
	private Long forumId;
	private Long censerId;
	private String censerName;
	private String nameForum;
	private String description;
	private String coverImage;
	private String message;
	private String Status;
	private List<PostResponse> listPostResponse = new ArrayList<>();
	private int numberMembers;
	private int numberPosts;
	private int numberMembersNotCensor;
	private int numberPostsNotCensor;
	private int numberReportComments;
	private int numberReportPost;
	private int isCensor=0;
	public ForumResponse(forumsEntity forumsEntity, userEntity userEntity) {
		this.forumId = forumsEntity.getForumId();
		this.censerId = userEntity.getUserid();
		this.censerName = userEntity.getFullName();
		this.nameForum = forumsEntity.getName();
		this.description = forumsEntity.getDescription();
		this.coverImage = forumsEntity.getImage();
		this.setCreateBy(forumsEntity.getCreatedby());
		this.setCreateDate(forumsEntity.getCreateDate());
		this.setModifiedBy(forumsEntity.getModifiedBy());
		this.setModifiedDate(forumsEntity.getModifiedDate());
	}
	
	public ForumResponse(forumsEntity forumsEntity) {
		this.forumId = forumsEntity.getForumId();
		this.nameForum = forumsEntity.getName();
		this.description = forumsEntity.getDescription();
		this.coverImage = forumsEntity.getImage();
		this.setCreateBy(forumsEntity.getCreatedby());
		this.setCreateDate(forumsEntity.getCreateDate());
		this.setModifiedBy(forumsEntity.getModifiedBy());
		this.setModifiedDate(forumsEntity.getModifiedDate());
	}
	
	public ForumResponse(forumsEntity forumsEntity, userEntity userEntity, int postInForum, int postNotCensorInForum, int memberInForum, int memberNotCensorInForum) {
		this.forumId = forumsEntity.getForumId();
		this.censerId = userEntity.getUserid();
		this.censerName = userEntity.getFullName();
		this.nameForum = forumsEntity.getName();
		this.description = forumsEntity.getDescription();
		this.coverImage = forumsEntity.getImage();
		this.numberPosts = postInForum;
		this.numberPostsNotCensor = postNotCensorInForum;
		this.numberMembers = memberInForum;
		this.numberMembersNotCensor = memberNotCensorInForum;
		this.setCreateBy(forumsEntity.getCreatedby());
		this.setCreateDate(forumsEntity.getCreateDate());
		this.setModifiedBy(forumsEntity.getModifiedBy());
		this.setModifiedDate(forumsEntity.getModifiedDate());
	}
}
