package com.c1se44.school_connect.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "report_comments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class reportCommentEntity extends baseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long reportCommentId;
	@ManyToOne
	@JoinColumn(name = "user_id",referencedColumnName="id", foreignKey=@ForeignKey(name = "Fk_user_comment_report"))
	private userEntity userCommentsReportId;
	
	@OneToOne
	@JoinColumn(name = "comment_id",referencedColumnName="id", foreignKey=@ForeignKey(name = "Fk_comment_report"))
	private commentEntity commentsReportId;;
	
	@ManyToOne
	@JoinColumn(name = "forum_id",referencedColumnName="id", foreignKey=@ForeignKey(name = "Fk_forum_comment_report"))
	private forumsEntity forumCommentReportId;
	
	@Column(name = "reason")
	private String reason;
	public reportCommentEntity(commentEntity comment,userEntity user,forumsEntity forums,String reason){
		this.commentsReportId=comment;
		this.userCommentsReportId=user;
		this.forumCommentReportId=forums;
		this.reason=reason;
	}
}
