package com.c1se44.school_connect.DTO.Response;

import com.c1se44.school_connect.entity.reportChatEntity;
import com.c1se44.school_connect.entity.reportCommentEntity;
import com.c1se44.school_connect.entity.reportPostEntity;
import com.c1se44.school_connect.entity.userEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponse extends baseResponse{
	private Long reportId;
	private Long commentId;
	private Long postId;
	private Long roomId;
	private String reason;
	private userResponse user;
	private userResponse userChat1;
	private userResponse userChat2;
	private PostResponse post;
	private CommentResponse comment;
	private RoomChatResponse roomChat;
	private userResponse reportedPerson;
	private ChatResponse messageIsReported;
	public ReportResponse(reportCommentEntity reportCommentEntity,  userResponse user, CommentResponse comment){
		this.reportId= reportCommentEntity.getReportCommentId();
		this.reason = reportCommentEntity.getReason();
		this.user=user;
		this.comment=comment;
		this.setCreateDate(reportCommentEntity.getCreateDate());
		this.setCreateBy(reportCommentEntity.getCreatedby());
		this.setModifiedDate(reportCommentEntity.getModifiedDate());
		this.setModifiedBy(reportCommentEntity.getModifiedBy());
	}
	
	public ReportResponse(reportPostEntity reportPostEntity, userResponse user, PostResponse post){
		this.reportId= reportPostEntity.getReportPostId();
		this.reason = reportPostEntity.getReason();
		this.user=user;
		this.post=post;
		this.setCreateDate(reportPostEntity.getCreateDate());
		this.setCreateBy(reportPostEntity.getCreatedby());
		this.setModifiedDate(reportPostEntity.getModifiedDate());
		this.setModifiedBy(reportPostEntity.getModifiedBy());
	}
	
	public ReportResponse(reportChatEntity reportChatEntity,ChatResponse messageIsReported ,userResponse user, RoomChatResponse room){
		this.reportId= reportChatEntity.getReportRoomId();
		this.reason = reportChatEntity.getReason();
		this.user=user;
		this.roomChat=room;
		this.messageIsReported=messageIsReported;
		this.setCreateDate(reportChatEntity.getCreateDate());
		this.setCreateBy(reportChatEntity.getCreatedby());
		this.setModifiedDate(reportChatEntity.getModifiedDate());
		this.setModifiedBy(reportChatEntity.getModifiedBy());
	}
}
