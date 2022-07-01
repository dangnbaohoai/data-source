package com.c1se44.school_connect.service;

import com.c1se44.school_connect.entity.imagePostEntity;
import com.c1se44.school_connect.entity.postsEntity;

import java.util.List;

public interface IImagePostService {
	imagePostEntity save(imagePostEntity imagePostEntity);
	List<imagePostEntity> findByPostImageId(postsEntity postsEntity);
	List<Long> findByPostImageId(Long postsId);
	void deleteByImageId(Long id);
	void deleteByPostImageId(postsEntity id);
	List<imagePostEntity> saveAll(List<imagePostEntity> postsEntity);
}
