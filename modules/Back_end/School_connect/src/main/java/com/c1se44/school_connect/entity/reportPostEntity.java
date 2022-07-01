package com.c1se44.school_connect.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "report_post")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class reportPostEntity extends baseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long reportPostId;
	@ManyToOne
	@JoinColumn(name = "user_id",referencedColumnName="id", foreignKey=@ForeignKey(name = "Fk_user_report_post"))
	private userEntity userPostReportId;
	
	@OneToOne
	@JoinColumn(name = "post_id",referencedColumnName="id", foreignKey=@ForeignKey(name = "Fk_post_report"))
	private postsEntity postReportId;
	
	@ManyToOne
	@JoinColumn(name = "forum_id",referencedColumnName="id", foreignKey=@ForeignKey(name = "Fk_forum_post_report"))
	private forumsEntity forumPostReportId;
	
	@Column(name = "reason")
	private String reason;
	public reportPostEntity(postsEntity post,userEntity user,forumsEntity forums,String reason){
		this.postReportId=post;
		this.userPostReportId=user;
		this.forumPostReportId=forums;
		this.reason=reason;
	}
	
}
