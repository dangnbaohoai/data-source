package com.c1se44.school_connect.repository;

import com.c1se44.school_connect.entity.StatusName;
import com.c1se44.school_connect.entity.forumsEntity;
import com.c1se44.school_connect.entity.postsEntity;
import com.c1se44.school_connect.entity.relationship.postForum;
import com.c1se44.school_connect.entity.userEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;

@Repository
public interface IPostForumRepository extends JpaRepository<postForum, Long> {
	@Query(value = "Select p from postForum p where p.forumPostId=?1 And p.status=?2")
	Page<postForum> findByForumId(forumsEntity forumId, StatusName status, Pageable pageable);
	
	@Query(value = "Select * from post_forum where post_id=? and status=?", nativeQuery = true)
	List<postForum> findByPostId(Long postId, String status);
	
	@Modifying
	@Query(value = "Update post_forum Set status=? where post_id=? and forum_id=?", nativeQuery = true)
	@Transactional
	void UpdateStatusByPostIdAndForumId(String status, Long postId, Long forumId);
	
	Boolean existsByStatusAndForumPostIdAndPostForumsId(StatusName status, forumsEntity forumId, postsEntity postId);
	
	Boolean existsByForumPostIdAndPostForumsId(forumsEntity forumId, postsEntity postId);
	
	Integer countByStatusAndForumPostId(StatusName status, forumsEntity forumId);
	
	@Modifying
	@Query(value = "DELETE FROM  post_forum where post_id=? and forum_id=?", nativeQuery = true)
	@Transactional
	void DeleteStatusByPostIdAndForumId(Long postId, Long forumId);
	
	@Modifying
	@Transactional
	void deleteByPostForumsId(postsEntity postId);
	
	postForum findByForumPostIdAndPostForumsId(forumsEntity forumId, postsEntity postId);
	
	@Query(value = "select count(*) from post_forum where forum_id=? and status=?", nativeQuery = true)
	int countAllByForumIdsAndStatus(Long forumId, String status);
	
	@Query(value = "select f from postForum f, postsEntity p where f.forumPostId =:forumId and f.status=:status and p.postId=f.postForumsId.postId and p.nameEvent IS NOT NULL order by p.dateOfEvent desc ")
	Page<postForum> findByDateEvent(@Param("forumId") forumsEntity forumId,@Param("status") StatusName status , Pageable pageable);
	
	@Query(value = "select count(*) from post_forum f where f.forum_id =:forumId and HOUR(f.create_date)=:hour and MONTH(f.create_date)=:month and YEAR(f.create_date)=:year ",nativeQuery = true)
	int countByForumPostIdAndHoursAndMonth(@Param("forumId") Long forumId,@Param("hour") int hour,@Param("month") int month,@Param("year") int year );
}
