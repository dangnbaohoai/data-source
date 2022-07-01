package com.c1se44.school_connect.repository;

import com.c1se44.school_connect.entity.StatusName;
import com.c1se44.school_connect.entity.forumsEntity;
import com.c1se44.school_connect.entity.userEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface IForumRepository extends JpaRepository<forumsEntity, Long> {
	Boolean existsByNameAndStatus(String forumName, StatusName status);
	Boolean existsByName(String forumName);
	Page<forumsEntity> findByStatusAndNameContaining(StatusName status, String forumName, Pageable pageable);
	int countByStatusAndNameContaining(StatusName status, String forumName);
	
	forumsEntity findByForumIdAndStatus(Long forumId, StatusName status);
	
	Boolean existsByStatusAndCensorId(StatusName status, userEntity CensorId);
	
	Boolean existsByStatusAndForumId(StatusName status, Long forumId);
	
	Boolean existsByStatusAndForumIdAndCensorId(StatusName status, Long forumId, userEntity CensorId);
	
	Page<forumsEntity> findByStatus(StatusName status, Pageable pageable);
	
	//Page<forumsEntity> findByStatusAndNameContaining(StatusName status, String Name, Pageable pageable);
	
	List<forumsEntity> findByStatusAndForumIdIn(StatusName status, List<Long> forumId);
	
	@Query(value = "Select f.censor_id from forums f where  f.status=?", nativeQuery = true)
	@Transactional
	List<Long> findCensors(String status);
	
	@Modifying
	@Query(value = "UPDATE forums SET status = ? where id=?", nativeQuery = true)
	@Transactional
	void updateStatusById(String status, Long userId);
	
	@Query(value = "Select f.id, f.forum_name from forums f", nativeQuery = true)
	List<Object[]> findAllForumIdAndNameByStatus(StatusName status);
	
	@Query(value = "select count(*) from forums where status = ?", nativeQuery = true)
	int countAllForumIds(String status);
	
	List<forumsEntity> findAll();
}
