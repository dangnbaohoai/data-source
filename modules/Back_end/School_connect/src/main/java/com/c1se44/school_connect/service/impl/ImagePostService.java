package com.c1se44.school_connect.service.impl;

import com.c1se44.school_connect.entity.imagePostEntity;
import com.c1se44.school_connect.entity.postsEntity;
import com.c1se44.school_connect.repository.IImagePostRepository;
import com.c1se44.school_connect.service.IImagePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImagePostService implements IImagePostService {
	@Autowired
	IImagePostRepository imagePostRepository;
	
	@Override
	public imagePostEntity save(imagePostEntity imagePostEntity) {
		return imagePostRepository.save(imagePostEntity);
	}
	@Override
	public List<imagePostEntity> saveAll(List<imagePostEntity> imagePostEntity) {
		return imagePostRepository.saveAll(imagePostEntity);
	}
	
	@Override
	public List<imagePostEntity> findByPostImageId(postsEntity postsEntity) {
		return imagePostRepository.findByPostImageId(postsEntity);
	}
	
	@Override
	public List<Long> findByPostImageId(Long postsId) {
		return imagePostRepository.findByPostImageId(postsId);
	}
	
	@Override
	public void deleteByImageId(Long id) {
		imagePostRepository.deleteByImageId(id);
	}
	
	@Override
	public void deleteByPostImageId(postsEntity id) {
		imagePostRepository.deleteByPostImageId(id);
	}
}
