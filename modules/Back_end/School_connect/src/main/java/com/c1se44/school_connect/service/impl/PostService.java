package com.c1se44.school_connect.service.impl;


import com.c1se44.school_connect.entity.postsEntity;
import com.c1se44.school_connect.entity.userEntity;
import com.c1se44.school_connect.repository.IPostRepository;

import com.c1se44.school_connect.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService implements IPostService {
	@Autowired
	IPostRepository postRepository;
	
	
	@Override
	public Boolean existsByPostIdAndUserPostId(Long postId, userEntity user) {
		return postRepository.existsByPostIdAndUserPostId(postId,user);
	}
	
	@Override
	public postsEntity save(postsEntity postsEntity) {
		return postRepository.save(postsEntity);
	}
	
	@Override
	public postsEntity findByPostId(Long postId) {
		return postRepository.findByPostId(postId);
	}
	
	@Override
	public List<postsEntity> findByPostIdIn(List<Long> postId) {
		//List<Long> longList = bigIntegerList.stream().map(BigInteger::longValue).collect(Collectors.toList());
		
//		List<postsEntity> posts = new ArrayList<>();
//		postId.forEach(item ->{
//			postsEntity post= postRepository.findByPostId(item.longValue());
//			posts.add(post);
//		});
		return postRepository.findAllByPostIdIsIn(postId);
		
	}
	
	@Override
	public void deleteByPostIdAndUserPostId(Long postId, userEntity user) {
		postRepository.deleteByPostIdAndUserPostId(postId,user);
	}
	
	@Override
	public int countAll() {
		return postRepository.countAllPostId();
	}
	@Override
	public List<postsEntity> findAllByYearAndMonthAndPostIdIN(int year, int month, List<Long> postId) {
		return postRepository.findAllByYearAndMonthAndPostIdIN(year,month,postId);
	}
	
	@Override
	public List<postsEntity> findByUserPostId(userEntity user, Pageable pageable) {
		return postRepository.findByUserPostId(user, pageable).getContent();
	}
	
	@Override
	public int countAllPostIdByUserPostId(Long userCreateId) {
		return postRepository.countAllPostIdByUserPostId(userCreateId);
	}
	
	@Override
	public void deleteByPostId(Long postId) {
		postRepository.deleteByPostId(postId);
	}
	
	@Override
	public void updateNumberLikeByPostId(int numberLike, Long postId) {
		postRepository.updateNumberLikeByPostId(numberLike, postId);
	}
}
