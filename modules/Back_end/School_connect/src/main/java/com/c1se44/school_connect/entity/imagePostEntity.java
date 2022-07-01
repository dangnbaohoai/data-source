package com.c1se44.school_connect.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name="image_post")
public class imagePostEntity extends baseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long imageId;
	
	@Column(name = "link_image",columnDefinition = "TEXT")
	private String linkImage;
	
	@ManyToOne
	@JoinColumn(name = "post_id",foreignKey=@ForeignKey(name = "Fk_image_post"))
	private postsEntity postImageId;
	

	
	
}
