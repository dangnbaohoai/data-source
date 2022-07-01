package com.c1se44.school_connect.DTO.Response;

import com.c1se44.school_connect.entity.baseEntity;
import com.c1se44.school_connect.entity.imagePostEntity;
import com.c1se44.school_connect.entity.postsEntity;
import com.c1se44.school_connect.entity.userEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse extends baseResponse {
	private Long forumId;
	private String forumName;
	private Long userId;
	private Long postId;
	private String userName;
	private String avatar;
	private String position;
	private String content;
	private String addressOfEvent;
	private LocalDate dateOfEvent;
	private String nameOfEvent;
	private Time timeOfEvent;
	private int numberLike;
	private int numberComments;
	private int statusJoinEventOfUser=0;
	private List<String> linkImage;
	private List<Long> listForumId = new ArrayList<>();
	private List<CommentResponse> commentList =new ArrayList<>();
	private List<ForumResponse> forumList =new ArrayList<>();
	public PostResponse(postsEntity postsEntity, userEntity userEntity,List<imagePostEntity> listImages) {
		this.userId=userEntity.getUserid();
		this.userName=userEntity.getUsername();
		this.avatar=userEntity.getAvatar();
		this.position=userEntity.getPosition();
		this.postId=postsEntity.getPostId();
		this.content = postsEntity.getContent();
		if (listImages != null){
			List<String> linkimage = new ArrayList<>();
			listImages.forEach(image->{
				if(!linkimage.contains(image.getLinkImage()))
				linkimage.add(image.getLinkImage());
			});
			this.linkImage = linkimage;
		}
		if (postsEntity.getAddressOfEvent() != null)
			this.addressOfEvent = postsEntity.getAddressOfEvent();
		if (postsEntity.getDateOfEvent()!= null)
			this.dateOfEvent = postsEntity.getDateOfEvent();
		if (postsEntity.getNameEvent() != null)
			this.nameOfEvent = postsEntity.getNameEvent();
		if (postsEntity.getTimeOfEvent()!= null)
			this.timeOfEvent = postsEntity.getTimeOfEvent();
		this.numberLike= postsEntity.getNumberLike();
		
		this.setCreateDate(postsEntity.getCreateDate());
		this.setCreateBy(postsEntity.getCreatedby());
		this.setModifiedDate(postsEntity.getModifiedDate());
		this.setModifiedBy(postsEntity.getModifiedBy());
	}
}
