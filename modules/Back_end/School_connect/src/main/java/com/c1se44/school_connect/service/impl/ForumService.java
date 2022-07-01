package com.c1se44.school_connect.service.impl;

import com.c1se44.school_connect.entity.StatusName;
import com.c1se44.school_connect.entity.forumsEntity;
import com.c1se44.school_connect.entity.userEntity;
import com.c1se44.school_connect.repository.IForumRepository;
import com.c1se44.school_connect.service.IForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForumService implements IForumService {
	@Autowired
	IForumRepository forumRepository;
	
	@Override
	public Boolean existsByForumname(String forumName) {
		return forumRepository.existsByNameAndStatus(forumName, StatusName.is_active);
	}
	
	@Override
	public Boolean existsByForumAndStatus(String forumName, StatusName status) {
		return forumRepository.existsByName(forumName);
	}
	
	@Override
	public List<forumsEntity> findAll(StatusName status, Pageable pageable) {
		List<forumsEntity> forums = forumRepository.findByStatus(status, pageable).getContent();
		return forums;
	}
	
	@Override
	public Boolean existsByCensorId(StatusName status, userEntity CensorId) {
		return forumRepository.existsByStatusAndCensorId(status, CensorId);
	}
	
	@Override
	public forumsEntity save(forumsEntity save) {
		return forumRepository.save(save);
	}
	
	@Override
	public forumsEntity findByForumId(Long forumId) {
		return forumRepository.findByForumIdAndStatus(forumId, StatusName.is_active);
	}
	
	@Override
	public List<Long> findCensors(StatusName status) {
		return forumRepository.findCensors(status.toString());
	}
	
	@Override
	public List<forumsEntity> findByStatusAndNameLike(String forumName, Pageable pageable) {
		return forumRepository.findByStatusAndNameContaining(StatusName.is_active, forumName, pageable).getContent();
	}
	
	@Override
	public int countByStatusAndNameContaining(StatusName status, String forumName) {
		return forumRepository.countByStatusAndNameContaining(status,forumName);
	}
	
	@Override
	public void updateStatusById(StatusName status, Long userId) {
		forumRepository.updateStatusById(status.toString(), userId);
	}
	
	@Override
	public Page<forumsEntity> findAllByStatusAndName(StatusName status, String Name, Pageable pageable) {
		return forumRepository.findByStatusAndNameContaining(status, Name, pageable);
	}
	
	@Override
	public Boolean existsByStatusAndForumId(StatusName status, Long forumId) {
		return forumRepository.existsByStatusAndForumId(status, forumId);
	}
	
	@Override
	public Boolean existsByStatusAndForumIdAndCensorId(StatusName status, Long forumId, userEntity CensorId) {
		return forumRepository.existsByStatusAndForumIdAndCensorId(status, forumId, CensorId);
	}
	
	@Override
	public List<Object[]> findForumIdAndName(StatusName status) {
		return forumRepository.findAllForumIdAndNameByStatus(status);
	}
	
	@Override
	public int countAll(StatusName status) {
		return forumRepository.countAllForumIds(status.toString());
	}
	
	@Override
	public List<forumsEntity> findByStatusAndForumIdIn(StatusName status, List<Long> forumId) {
		return forumRepository.findByStatusAndForumIdIn(status,forumId);
	}
	
	@Override
	public List<forumsEntity> findAll() {
		return forumRepository.findAll();
	}
	
}
