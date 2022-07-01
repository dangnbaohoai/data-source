package com.c1se44.school_connect.entity;

import com.c1se44.school_connect.entity.relationship.memberNotify;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "notify")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class notifyEntity extends  baseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long notifyId;
	@ManyToOne
	@JoinColumn(name="post_id",referencedColumnName="id", foreignKey=@ForeignKey(name = "Fk_post_notify"))
	private postsEntity postNotify;
	@ManyToOne
	@JoinColumn(name="forum_id",referencedColumnName="id", foreignKey=@ForeignKey(name = "Fk_forum_notify"))
	private forumsEntity forumNotify;

	@Column(name = "message")
	private String message;
	
	@OneToMany(mappedBy = "notifyUserId",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<memberNotify> memberNotify = new ArrayList<>();
	
	public notifyEntity(postsEntity post,forumsEntity forums,String message){
		this.forumNotify=forums;
		this.postNotify=post;
		this.message=message;
	}
	public notifyEntity(forumsEntity forums,String message){
		this.forumNotify=forums;
		this.message=message;
	}
}
