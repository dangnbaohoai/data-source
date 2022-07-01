package com.c1se44.school_connect.entity;

import com.c1se44.school_connect.DTO.Request.ForumRequest;
import com.c1se44.school_connect.entity.relationship.memberForums;
import com.c1se44.school_connect.entity.relationship.postForum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "forums", uniqueConstraints = {
			@UniqueConstraint(columnNames = {
						"forum_name"
			})
	
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class forumsEntity extends baseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long forumId;
	
	@Column(name = "forum_name")
	private String name;
	
	@Column(name = "description", columnDefinition = "TEXT")
	private String description;
	
	@Column(name = "cover_image")
	private String Image;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private StatusName status;
	
	@ManyToOne
	@JoinColumn(name = "censor_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "Fk_censor_forum"))
	private userEntity censorId;
	
	@OneToMany(mappedBy = "forumPostId",cascade = CascadeType.ALL)
	private List<postForum> postForum = new ArrayList<>();
	
	@OneToMany(mappedBy = "forumUserId",cascade = CascadeType.ALL)
	private List<memberForums> memberForums = new ArrayList<>();
	
	@OneToMany(mappedBy = "forumPostReportId",cascade = CascadeType.ALL)
	private List<reportPostEntity> reportPostEntity = new ArrayList<>();
	
	@OneToMany(mappedBy = "forumCommentReportId",cascade = CascadeType.ALL)
	private List<reportCommentEntity> reportCommentEntity = new ArrayList<>();
	
	@OneToMany(mappedBy = "forumNotify", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<notifyEntity> notifyEntity = new ArrayList<>();;
	
	public forumsEntity(ForumRequest forumRequest, userEntity userEntity) {
		this.censorId = userEntity;
		this.name = forumRequest.getNameForum();
		this.description = forumRequest.getDescription();
		this.Image = forumRequest.getCoverImage();
		this.status = StatusName.is_active;
	}
}
