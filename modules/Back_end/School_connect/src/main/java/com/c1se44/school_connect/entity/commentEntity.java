package com.c1se44.school_connect.entity;

import com.c1se44.school_connect.DTO.Request.CommentRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "comment")
@AllArgsConstructor
@NoArgsConstructor
public class commentEntity extends baseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long commentId;
	
	@Column(name = "comment_time")
	private LocalDateTime time;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "link_image",columnDefinition = "TEXT")
	private String linkImage;
	
	@Column(name = "like_comment",columnDefinition = "INT")
	private int like;
	
	@Column(name="reply_comment_id")
	private Long reply;
	
	@ManyToOne
	@JoinColumn(name="post_id",referencedColumnName="id", foreignKey=@ForeignKey(name = "Fk_post_comment"))
	private postsEntity postCommentId;
	
	@ManyToOne
	@JoinColumn(name = "user_id",referencedColumnName="id", foreignKey=@ForeignKey(name = "Fk_user_comment"))
	private userEntity userCommentId;
	
	@OneToOne(mappedBy = "commentsReportId", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private reportCommentEntity reportCommentEntity;
	
	public commentEntity(CommentRequest commentRequest, userEntity user,postsEntity post){
		if(commentRequest.getTimeComment()!= null){
			this.time=commentRequest.getTimeComment();
		}
		this.content=commentRequest.getContent();
		if (commentRequest.getImageComment()!=null){
			this.linkImage=commentRequest.getImageComment();
		}
		if (commentRequest.getReplyId()!=null){
			this.reply=commentRequest.getReplyId();
		}
		this.like=0;
		this.postCommentId=post;
		this.userCommentId=user;
	}
	public commentEntity(CommentRequest commentRequest){
		if(commentRequest.getTimeComment()!= null){
			this.time=commentRequest.getTimeComment();
		}
		this.content=commentRequest.getContent();
		if (commentRequest.getImageComment()!=null){
			this.linkImage=commentRequest.getImageComment();
		}
		if (commentRequest.getReplyId()!=null){
			this.reply=commentRequest.getReplyId();
		}
		this.like=0;
	}
}
