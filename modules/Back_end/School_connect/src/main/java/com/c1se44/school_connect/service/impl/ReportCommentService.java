package com.c1se44.school_connect.service.impl;

import com.c1se44.school_connect.entity.commentEntity;
import com.c1se44.school_connect.entity.forumsEntity;
import com.c1se44.school_connect.entity.reportCommentEntity;
import com.c1se44.school_connect.repository.IReportCommentRepository;
import com.c1se44.school_connect.service.IReportCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportCommentService implements IReportCommentService {
	@Autowired
	IReportCommentRepository reportCommentRepository;
	@Override
	public reportCommentEntity save(reportCommentEntity reportCommentEntity) {
		return reportCommentRepository.save(reportCommentEntity);
	}
	
	@Override
	public List<reportCommentEntity> findAll(Pageable pageable) {
		return reportCommentRepository.findAll(pageable).getContent();
	}
	
	@Override
	public void deleteByReportCommentId(Long reportCommentId) {
		reportCommentRepository.deleteByReportCommentId(reportCommentId);
	}
	
	@Override
	public reportCommentEntity findByReportCommentId(Long reportId) {
		return reportCommentRepository.findByReportCommentId(reportId);
	}
	
	@Override
	public Boolean existsByReportCommentId(Long reportId) {
		return reportCommentRepository.existsByReportCommentId(reportId);
	}
	
	@Override
	public List<reportCommentEntity> findAllByForumCommentReportId(forumsEntity forumId, Pageable pageable) {
		return reportCommentRepository.findAllByForumCommentReportId(forumId,pageable).getContent();
	}
	
	@Override
	public int countAllByForumCommentReportId(forumsEntity forumId) {
		return reportCommentRepository.countAllByForumCommentReportId(forumId);
	}
	
	@Override
	public void deleteByCommentsReportId(commentEntity CommentId) {
		reportCommentRepository.deleteByCommentsReportId(CommentId);
	}
}
