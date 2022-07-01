package com.c1se44.school_connect.repository;

import com.c1se44.school_connect.entity.postsEntity;
import com.c1se44.school_connect.entity.relationship.memberEvent;
import com.c1se44.school_connect.entity.userEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface IMemberEventRepository extends JpaRepository<memberEvent,Long> {
	@Modifying
	@Query(value = "DELETE FROM member_event WHERE user_id=? AND event_id=?",nativeQuery = true)
	@Transactional
	void deleteByUserAndEvent(Long userId,Long eventId);
	
	@Query(value = "select m FROM memberEvent m WHERE m.userEventId =?1")
	List<memberEvent> findEventsByUserId(userEntity userId);
	
	@Query(value = "select m.* FROM member_event m WHERE m.user_id=? AND month(m.date_of_event)=? AND year(m.date_of_event)=?",nativeQuery = true)
	List<memberEvent> findEventsByUserIdAndDateOfEvent(Long userId,int mont,int year);
	Boolean existsByUserEventIdAndEventUserId(userEntity user, postsEntity event);
}
