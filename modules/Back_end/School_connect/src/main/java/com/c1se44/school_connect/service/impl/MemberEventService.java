package com.c1se44.school_connect.service.impl;

import com.c1se44.school_connect.entity.postsEntity;
import com.c1se44.school_connect.entity.relationship.memberEvent;
import com.c1se44.school_connect.entity.userEntity;
import com.c1se44.school_connect.repository.IMemberEventRepository;
import com.c1se44.school_connect.service.IMemberEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberEventService implements IMemberEventService {
	@Autowired
	IMemberEventRepository memberEventRepository;
	@Override
	public memberEvent save(memberEvent memberEvent) {
		return memberEventRepository.save(memberEvent);
	}
	
	@Override
	public void deleteByUserAndEvent(Long userId, Long eventId) {
		memberEventRepository.deleteByUserAndEvent(userId,eventId);
	}
	
	@Override
	public List<memberEvent> findEventsByUserId(userEntity userId) {
		return memberEventRepository.findEventsByUserId(userId);
	}
	
	@Override
	public List<memberEvent> findEventsByUserIdAndDateOfEvent(Long userId, int month, int year) {
		return memberEventRepository.findEventsByUserIdAndDateOfEvent(userId,month,year);
	}
	
	@Override
	public Boolean existsByUserEventIdAndEventUserId(userEntity user, postsEntity event) {
		return memberEventRepository.existsByUserEventIdAndEventUserId(user,event);
	}
}
