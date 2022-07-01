package com.c1se44.school_connect.repository;

import com.c1se44.school_connect.entity.StatusName;
import com.c1se44.school_connect.entity.forumsEntity;
import com.c1se44.school_connect.entity.relationship.memberForums;
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
public interface IMemberForumRepository extends JpaRepository<memberForums, Long> {
	
	@Query(value = "Select user_id from member_forums where forum_id=? and status=? ", nativeQuery = true)
	List<Long> findByForumId(Long forumId, String status);
	
	@Query(value = "Select forum_id from member_forums where user_id=? and status=?", nativeQuery = true)
	List<Long> findByUserId(Long userId, String status);
	
	Boolean existsByUserForumIdAndForumUserId(userEntity userId, forumsEntity forumId);
	
	Boolean existsByUserForumIdAndForumUserIdAndStatus(userEntity userId, forumsEntity forumId, StatusName status);
	
	List<memberForums> findByUserForumIdAndStatus(userEntity userId, StatusName status, Pageable pageable);
	
	Page<memberForums> findByForumUserIdAndStatus(forumsEntity forumId, StatusName status, Pageable pageable);
	List<memberForums> findByForumUserIdAndStatus(forumsEntity forumId, StatusName status);
	
	Integer countByStatusAndForumUserId(StatusName status,forumsEntity forumId);
	
	@Modifying
	@Query(value = "Update member_forums Set status=? where user_id=? and forum_id=?", nativeQuery = true)
	@Transactional
	void UpdateStatusByUserIdAndForumId(String status, Long userId, Long forumId);
	
	@Modifying
	@Query(value = "Update member_forums Set user_id=? where user_id=? and forum_id=?", nativeQuery = true)
	@Transactional
	void UpdateUserByUserIdAndForumId(Long userChange,Long userCurrent, Long forumId);
	
	@Modifying
	@Query(value = "DELETE FROM  member_forums where user_id=? and forum_id=?", nativeQuery = true)
	@Transactional
	void DeleteStatusByUserIdAndForumId(Long userId, Long forumId);
	
	@Query(value = "select count(*) from member_forums where forum_id=? and status=?", nativeQuery = true)
	int countAllByForumIdsAndStatus(Long forumId,String status);
}
