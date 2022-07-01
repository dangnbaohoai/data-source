package com.c1se44.school_connect.DTO.Response;

import com.c1se44.school_connect.entity.commentEntity;
import com.c1se44.school_connect.entity.postsEntity;
import com.c1se44.school_connect.entity.userEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse extends baseResponse {
	private Long commentId;
	private Long postId;
	private Long userId;
	private String userName;
	private String avatarOfUser;
	private String content;
	private String imageComment;
	private Long replyId;
	private int numberReplyCount;
	
	public CommentResponse(commentEntity commentEntity, userEntity user, postsEntity posts){
		this.commentId=commentEntity.getCommentId();
		this.content=commentEntity.getContent();
		this.userId=user.getUserid();
		this.userName=user.getUsername();
		this.avatarOfUser=user.getAvatar();
		this.postId=posts.getPostId();
		if(commentEntity.getReply()!=null){
			this.replyId=commentEntity.getReply();
		}
		if(commentEntity.getLinkImage()!=null){
			this.imageComment=commentEntity.getLinkImage();
		}
		this.setCreateBy(commentEntity.getCreatedby());
		this.setCreateDate(commentEntity.getCreateDate());
		this.setModifiedBy(commentEntity.getModifiedBy());
		this.setModifiedDate(commentEntity.getModifiedDate());
	}
}
