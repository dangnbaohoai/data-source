package com.c1se44.school_connect.service;

import com.c1se44.school_connect.entity.commentEntity;
import com.c1se44.school_connect.entity.postsEntity;
import com.c1se44.school_connect.entity.userEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICommentService {
	commentEntity save(commentEntity commentEntity);
	List<commentEntity> findByPostCommentId(postsEntity post, Pageable pageable);
	int countAllByPostCommentIdAndReply(postsEntity post, Long replyId);
	List<commentEntity> findByPostCommentIdAndReply(postsEntity post,Long replyId, Pageable pageable);
	commentEntity findByCommentId(Long commentId);
	commentEntity findByCommentIdAndUserCommentId(Long commentId, userEntity user);
	void deleteByCommentIdAndUserCommentId(Long commentId,userEntity user);
	void deleteByCommentId(Long commentId);
	void deleteByReply(Long replyId);
	List<commentEntity> findByPostCommentIdAndReply(postsEntity post,Long replyId);
	int countAllByPostCommentId(postsEntity postsEntity);
	void updateNumberLikeByCommentId(int numberLike, Long commentId);
	Boolean existsByCommentId(Long commentId);
}
