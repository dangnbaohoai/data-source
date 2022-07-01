package com.c1se44.school_connect.entity.relationship;

import com.c1se44.school_connect.entity.StatusName;
import com.c1se44.school_connect.entity.baseEntity;
import com.c1se44.school_connect.entity.forumsEntity;
import com.c1se44.school_connect.entity.userEntity;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "member_forums")
@Data
public class memberForums extends baseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name = "user_id",referencedColumnName="id", foreignKey=@ForeignKey(name = "Fk_user_forum"))
	private userEntity userForumId;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name = "forum_id",referencedColumnName="id", foreignKey=@ForeignKey(name = "Fk_forum_user"))
	private forumsEntity forumUserId;
	
	@Enumerated(EnumType.STRING)
	@Column(name="status")
	private StatusName status;

}
