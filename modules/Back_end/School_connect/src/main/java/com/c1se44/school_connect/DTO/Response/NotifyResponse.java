package com.c1se44.school_connect.DTO.Response;

import com.c1se44.school_connect.entity.relationship.memberNotify;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotifyResponse extends baseResponse{
	private Long notifyId;
	private Long postId;
	private ForumResponse forumResponse;
	private String message;
	private String statusMessage;
	private int numberNotifyNotSeen;
	private int numberMessageNotSeen;
	public NotifyResponse(memberNotify memberNotify){
		this.notifyId=memberNotify.getNotifyId();
		this.setCreateBy(memberNotify.getCreatedby());
		this.setCreateDate(memberNotify.getCreateDate());
		this.setModifiedBy(memberNotify.getModifiedBy());
		this.setModifiedDate(memberNotify.getModifiedDate());
	}
}
