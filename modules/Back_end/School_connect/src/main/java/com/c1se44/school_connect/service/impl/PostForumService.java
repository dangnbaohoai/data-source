package com.c1se44.school_connect.service.impl;

import com.c1se44.school_connect.entity.StatusName;
import com.c1se44.school_connect.entity.forumsEntity;
import com.c1se44.school_connect.entity.postsEntity;
import com.c1se44.school_connect.entity.relationship.postForum;
import com.c1se44.school_connect.repository.IPostForumRepository;
import com.c1se44.school_connect.service.IPostForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostForumService implements IPostForumService {
	@Autowired
	IPostForumRepository postForumRepository;
	@Override
	public postForum save(postForum postForum) {
		return postForumRepository.save(postForum);
	}
	@Override
	public List<postForum> saveAll(List<postForum> postForum) {
		return postForumRepository.saveAll(postForum);
	}
	
	@Override
	public List<postForum> findByForumId(forumsEntity forumId, StatusName status, Pageable pageable) {
		return postForumRepository.findByForumId(forumId,status,pageable).getContent();
	}
	
	@Override
	public List<postForum> findByPostId(Long postId, StatusName status) {
		return postForumRepository.findByPostId(postId,status.toString());
	}
	
	@Override
	public void UpdateStatusByPostIdAndForumId(StatusName status, Long postId, Long forumId) {
		postForumRepository.UpdateStatusByPostIdAndForumId(status.toString(),postId,forumId);
	}
	
	@Override
	public Boolean existsByStatusAndForumPostIdAndPostForumsId(StatusName status, forumsEntity forumId, postsEntity postId) {
		return postForumRepository.existsByStatusAndForumPostIdAndPostForumsId(status,forumId,postId);
	}
	
	@Override
	public void DeleteStatusByPostIdAndForumId( Long postId, Long forumId) {
		postForumRepository.DeleteStatusByPostIdAndForumId(postId,forumId);
	}
	
	@Override
	public Boolean existsByForumPostIdAndPostForumsId(forumsEntity forumId, postsEntity postId) {
		return postForumRepository.existsByForumPostIdAndPostForumsId(forumId,postId);
	}
	
	@Override
	public postForum findByForumPostIdAndPostForumsId(forumsEntity forumId, postsEntity postId) {
		return postForumRepository.findByForumPostIdAndPostForumsId(forumId,postId);
	}
	
	@Override
	public int countAllByForumIdsAndStatus(Long forumId, StatusName status) {
		return postForumRepository.countAllByForumIdsAndStatus(forumId,status.toString());
	}
	
	@Override
	public void DeleteStatusByPostForumsId(postsEntity postId) {
		postForumRepository.deleteByPostForumsId(postId);
	}
	
	@Override
	public List<postForum> findByDateEvent(forumsEntity forumId, StatusName status, Pageable pageable) {
		return postForumRepository.findByDateEvent(forumId,status,pageable).getContent();
	}
	
	@Override
	public int countByForumPostIdAndHoursAndMonth(Long forumId, int hour, int month, int year) {
		return postForumRepository.countByForumPostIdAndHoursAndMonth(forumId,hour,month,year);
	}
	
	
}
