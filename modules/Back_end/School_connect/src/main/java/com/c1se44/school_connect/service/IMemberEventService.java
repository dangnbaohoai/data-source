package com.c1se44.school_connect.service;

import com.c1se44.school_connect.entity.postsEntity;
import com.c1se44.school_connect.entity.relationship.memberEvent;
import com.c1se44.school_connect.entity.userEntity;

import java.util.List;

public interface IMemberEventService {
	memberEvent save(memberEvent memberEvent);
	void deleteByUserAndEvent(Long userId,Long EventId);
	List<memberEvent> findEventsByUserId(userEntity userId);
	List<memberEvent> findEventsByUserIdAndDateOfEvent(Long userId,int month,int year);
	Boolean existsByUserEventIdAndEventUserId(userEntity user, postsEntity event);
}
