package com.c1se44.school_connect.service.impl;

import com.c1se44.school_connect.entity.StatusName;
import com.c1se44.school_connect.entity.forumsEntity;
import com.c1se44.school_connect.entity.relationship.memberForums;
import com.c1se44.school_connect.entity.userEntity;
import com.c1se44.school_connect.repository.IMemberForumRepository;
import com.c1se44.school_connect.service.IMemberForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberForumService implements IMemberForumService {
	@Autowired
	IMemberForumRepository memberForumRepository;
	@Override
	public List<Long> findByUserId(Long userId,StatusName status) {
		return memberForumRepository.findByUserId(userId,status.toString());
	}
	
	@Override
	public List<Long> findByForumId(Long forumId, StatusName status) {
		return memberForumRepository.findByForumId(forumId,status.toString());
	}
	
	@Override
	public memberForums save(memberForums memberForums) {
		return memberForumRepository.save(memberForums);
	}
	
	@Override
	public Boolean existsByUserForumIdAndForumUserId(userEntity userId, forumsEntity forumId) {
		return memberForumRepository.existsByUserForumIdAndForumUserId(userId,forumId);
	}
	
	@Override
	public Boolean existsByUserForumIdAndForumUserIdAndStatus(userEntity userId, forumsEntity forumId, StatusName status) {
		return memberForumRepository.existsByUserForumIdAndForumUserIdAndStatus(userId,forumId,status);
	}
	
	@Override
	public List<memberForums> findByForumUserIdAndStatus(forumsEntity forumId, StatusName status, Pageable pageable) {
		return memberForumRepository.findByForumUserIdAndStatus(forumId,status,pageable).getContent();
	}
	
	@Override
	public List<memberForums> findByForumUserIdAndStatus(forumsEntity forumId, StatusName status) {
		return memberForumRepository.findByForumUserIdAndStatus(forumId,status);
	}
	
	@Override
	public void UpdateStatus(StatusName status, Long userId, Long forumId) {
		memberForumRepository.UpdateStatusByUserIdAndForumId(status.toString(),userId,forumId);
	}
	
	@Override
	public void UpdateUserByUserIdAndForumId(Long userChange, Long userCurrent, Long forumId) {
		memberForumRepository.UpdateUserByUserIdAndForumId(userChange,userCurrent,forumId);
	}
	
	@Override
	public int countAllByForumIdsAndStatus(Long forumId, StatusName status) {
		return memberForumRepository.countAllByForumIdsAndStatus(forumId,status.toString());
	}
	
	@Override
	public void DeleteStatusByUserIdAndForumId(Long userId, Long forumId) {
		memberForumRepository.DeleteStatusByUserIdAndForumId(userId,forumId);
	}
}
