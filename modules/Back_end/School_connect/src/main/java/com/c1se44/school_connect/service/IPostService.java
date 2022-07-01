package com.c1se44.school_connect.service;

import com.c1se44.school_connect.entity.postsEntity;
import com.c1se44.school_connect.entity.userEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.Entity;
import java.util.List;

public interface IPostService {
	Boolean existsByPostIdAndUserPostId(Long postId, userEntity user);
	
	postsEntity save(postsEntity postsEntity);
	
	postsEntity findByPostId(Long postId);
	
	List<postsEntity> findByPostIdIn(List<Long> postId);
	void deleteByPostIdAndUserPostId(Long postId, userEntity user);
	int countAll();
	List<postsEntity> findAllByYearAndMonthAndPostIdIN(int year, int month, List<Long> postId);
	List<postsEntity> findByUserPostId(userEntity user, Pageable pageable);
	int countAllPostIdByUserPostId(Long userCreateId);
	void deleteByPostId(Long postId);
	void updateNumberLikeByPostId(int numberLike, Long postId);
}
