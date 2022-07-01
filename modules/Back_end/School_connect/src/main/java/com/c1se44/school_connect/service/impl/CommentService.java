package com.c1se44.school_connect.service.impl;

import com.c1se44.school_connect.entity.commentEntity;
import com.c1se44.school_connect.entity.postsEntity;
import com.c1se44.school_connect.entity.userEntity;
import com.c1se44.school_connect.repository.ICommentRepository;
import com.c1se44.school_connect.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService implements ICommentService {
	@Autowired
	ICommentRepository commentRepository;
	@Override
	public commentEntity save(commentEntity commentEntity) {
		return commentRepository.save(commentEntity);
	}
	
	@Override
	public List<commentEntity> findByPostCommentId(postsEntity post, Pageable pageable) {
		return commentRepository.findByPostCommentId(post,pageable).getContent();
	}
	
	@Override
	public int countAllByPostCommentIdAndReply(postsEntity post, Long replyId) {
		return commentRepository.countAllByPostCommentIdAndReply(post,replyId);
	}
	
	@Override
	public List<commentEntity> findByPostCommentIdAndReply(postsEntity post, Long replyId, Pageable pageable) {
		return commentRepository.findByPostCommentIdAndReply(post,replyId,pageable).getContent();
	}
	
	@Override
	public commentEntity findByCommentId(Long commentId) {
		return commentRepository.findByCommentId(commentId);
	}
	
	@Override
	public commentEntity findByCommentIdAndUserCommentId(Long commentId, userEntity user) {
		return commentRepository.findByCommentIdAndUserCommentId(commentId,user);
	}
	
	@Override
	public void deleteByCommentIdAndUserCommentId(Long commentId, userEntity user) {
		commentRepository.deleteByCommentIdAndUserCommentId(commentId,user);
	}
	
	@Override
	public void deleteByCommentId(Long commentId) {
		commentRepository.deleteByCommentId(commentId);
	}
	
	@Override
	public void deleteByReply(Long replyId) {
		commentRepository.deleteByReply(replyId);
	}
	
	@Override
	public List<commentEntity> findByPostCommentIdAndReply(postsEntity post, Long replyId) {
		return commentRepository.findByPostCommentIdAndReply(post,replyId);
	}
	
	@Override
	public int countAllByPostCommentId(postsEntity postsEntity) {
		return commentRepository.countAllByPostCommentId(postsEntity);
	}
	
	@Override
	public void updateNumberLikeByCommentId(int numberLike, Long commentId) {
		commentRepository.updateNumberLikeByCommentId(numberLike,commentId);
	}
	
	@Override
	public Boolean existsByCommentId(Long commentId) {
		return commentRepository.existsByCommentId(commentId);
	}
}
