package com.c1se44.school_connect.service.impl;

import com.c1se44.school_connect.entity.StatusName;
import com.c1se44.school_connect.entity.relationship.memberNotify;
import com.c1se44.school_connect.entity.userEntity;
import com.c1se44.school_connect.repository.IMemberNotifyRepository;
import com.c1se44.school_connect.service.IMemberNotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberNotifyService implements IMemberNotifyService {
	@Autowired
	IMemberNotifyRepository memberNotifyRepository;
	@Override
	public memberNotify save(memberNotify memberNotify) {
		return memberNotifyRepository.save(memberNotify);
	}
	@Override
	public List<memberNotify> saveAll(List<memberNotify> memberNotify) {
		return memberNotifyRepository.saveAll(memberNotify);
	}
	
	@Override
	public List<memberNotify> findAllByUserNotifyId(userEntity user, Pageable pageable) {
		return memberNotifyRepository.findAllByUserNotifyId(user, pageable).getContent();
	}
	
	@Override
	public int countAllByUserNotifyIdAndStatus(userEntity user, StatusName status) {
		return memberNotifyRepository.countAllByUserNotifyIdAndStatus(user,status);
	}
	
	@Override
	public memberNotify findByNotifyId(Long notifyId) {
		return memberNotifyRepository.findByNotifyId(notifyId);
	}
	
	@Override
	public void UpdateStatusByNotifyId(StatusName status, Long Id) {
		memberNotifyRepository.UpdateStatusByNotifyId(status.toString(), Id);
	}
	
	@Override
	public void UpdateStatusByUserId(StatusName status, Long userId) {
		memberNotifyRepository.UpdateStatusByUserId(status.toString(),userId);
	}
	
	@Override
	public void UpdateStatusByUserIdAndStatus(StatusName status1, Long userId, StatusName status2) {
		memberNotifyRepository.UpdateStatusByUserIdAndStatus(status1.toString(),userId,status2.toString());
	}
}
