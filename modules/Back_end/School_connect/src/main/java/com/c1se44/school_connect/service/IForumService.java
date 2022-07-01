package com.c1se44.school_connect.service;

import com.c1se44.school_connect.entity.StatusName;
import com.c1se44.school_connect.entity.forumsEntity;
import com.c1se44.school_connect.entity.userEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IForumService {
	Boolean existsByForumname(String forumName);
	Boolean existsByForumAndStatus(String forumName,StatusName status);
	List<forumsEntity> findAll(StatusName status, Pageable pageable);
	
	Boolean existsByCensorId(StatusName status, userEntity CensorId);
	
	forumsEntity save(forumsEntity save);
	
	forumsEntity findByForumId(Long forumId);
	
	List<Long> findCensors(StatusName status);
	
	List<forumsEntity> findByStatusAndNameLike(String forumName, Pageable pageable);
	int countByStatusAndNameContaining(StatusName status, String forumName);
	void updateStatusById(StatusName status, Long userId);
	
	Page<forumsEntity> findAllByStatusAndName(StatusName status, String Name, Pageable pageable);
	
	Boolean existsByStatusAndForumId(StatusName status, Long forumId);
	
	Boolean existsByStatusAndForumIdAndCensorId(StatusName status, Long forumId, userEntity CensorId);
	
	List<Object[]> findForumIdAndName(StatusName status);
	
	int countAll(StatusName status);
	
	List<forumsEntity> findByStatusAndForumIdIn(StatusName status, List<Long> forumId);
	List<forumsEntity> findAll();
}
