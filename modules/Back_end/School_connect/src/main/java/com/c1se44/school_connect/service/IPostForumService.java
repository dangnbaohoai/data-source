package com.c1se44.school_connect.service;

import com.c1se44.school_connect.entity.StatusName;
import com.c1se44.school_connect.entity.forumsEntity;
import com.c1se44.school_connect.entity.postsEntity;
import com.c1se44.school_connect.entity.relationship.postForum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import javax.persistence.Entity;
import java.util.List;

public interface IPostForumService {
	postForum save(postForum postForum);
	List<postForum> saveAll(List<postForum> postForum);
	List<postForum> findByForumId(forumsEntity forumId, StatusName status, Pageable pageable);
	
	List<postForum> findByPostId(Long postId, StatusName status);
	
	void UpdateStatusByPostIdAndForumId(StatusName status, Long postId, Long forumId);
	
	Boolean existsByStatusAndForumPostIdAndPostForumsId(StatusName status, forumsEntity forumId, postsEntity postId);
	
	void DeleteStatusByPostIdAndForumId(Long postId, Long forumId);
	
	Boolean existsByForumPostIdAndPostForumsId(forumsEntity forumId, postsEntity postId);
	
	postForum findByForumPostIdAndPostForumsId(forumsEntity forumId, postsEntity postId);
	
	int countAllByForumIdsAndStatus(Long forumId, StatusName status);
	void DeleteStatusByPostForumsId(postsEntity postId);
	List<postForum> findByDateEvent(forumsEntity forumId, StatusName status , Pageable pageable);
	int countByForumPostIdAndHoursAndMonth( Long forumId,int hour,int month, int year );
}
