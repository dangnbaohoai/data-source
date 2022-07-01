package com.c1se44.school_connect.entity.relationship;

import com.c1se44.school_connect.entity.*;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "post_forum")
@Data
public class postForum extends baseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name = "post_id", referencedColumnName="id", foreignKey=@ForeignKey(name = "Fk_post_forum"))
	private postsEntity postForumsId;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name = "forum_id",referencedColumnName="id", foreignKey=@ForeignKey(name = "Fk_forum_post"))
	private forumsEntity forumPostId;
	
	@Enumerated(EnumType.STRING)
	@Column(name="status")
	private StatusName status;
}
