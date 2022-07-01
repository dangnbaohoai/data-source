package com.c1se44.school_connect.repository;

import com.c1se44.school_connect.entity.commentEntity;
import com.c1se44.school_connect.entity.postsEntity;
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
public interface ICommentRepository extends JpaRepository<commentEntity, Long> {
	Page<commentEntity> findByPostCommentId(postsEntity post, Pageable pageable);
	
	int countAllByPostCommentIdAndReply(postsEntity post, Long replyId);
	
	List<commentEntity> findByPostCommentIdAndReply(postsEntity post, Long replyId);
	
	Page<commentEntity> findByPostCommentIdAndReply(postsEntity post, Long replyId, Pageable pageable);
	
	commentEntity findByCommentId(Long commentId);
	
	commentEntity findByCommentIdAndUserCommentId(Long commentId, userEntity user);
	
	int countAllByPostCommentId(postsEntity postsEntity);
	
	Boolean existsByCommentId(Long commentId);
	
	@Modifying
	@Transactional
	void deleteByCommentIdAndUserCommentId(Long commentId, userEntity user);
	
	@Modifying
	@Transactional
	void deleteByReply(Long replyId);
	
	@Modifying
	@Transactional
	//@Query(value = "delete from comment where id=?", nativeQuery = true)
	void deleteByCommentId(Long commentId);
	
	@Modifying
	@Transactional
	@Query(value = "update comment SET  like_comment=? where id=?", nativeQuery = true)
	void updateNumberLikeByCommentId(int numberLike, Long commentId);
}
