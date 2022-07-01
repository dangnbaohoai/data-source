package com.c1se44.school_connect.service.impl;

import com.c1se44.school_connect.entity.forumsEntity;
import com.c1se44.school_connect.entity.postsEntity;
import com.c1se44.school_connect.entity.reportPostEntity;
import com.c1se44.school_connect.repository.IReportPostRepository;
import com.c1se44.school_connect.service.IReportPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportPostService implements IReportPostService {
	@Autowired
	IReportPostRepository reportPostRepository;
	@Override
	public reportPostEntity save(reportPostEntity reportChatEntity) {
		return reportPostRepository.save(reportChatEntity);
	}
	
	@Override
	public List<reportPostEntity> findAll(Pageable pageable) {
		return reportPostRepository.findAll(pageable).getContent();
	}
	
	@Override
	public List<reportPostEntity> findAllByForumPostReportId(forumsEntity forumId, Pageable pageable) {
		return reportPostRepository.findAllByForumPostReportId(forumId,pageable).getContent();
	}
	
	@Override
	public void deleteByReportPostId(Long reportPostId) {
		reportPostRepository.deleteByReportPostId(reportPostId);
	}
	
	@Override
	public reportPostEntity findByReportPostId(Long id) {
		return reportPostRepository.findByReportPostId(id);
	}
	
	@Override
	public Boolean existsByReportPostId(Long id) {
		return reportPostRepository.existsByReportPostId(id);
	}
	
	@Override
	public void deleteByPostReportId(postsEntity reportPostId) {
		reportPostRepository.deleteByPostReportId(reportPostId);
	}
	
	@Override
	public int countAllByForumPostReportId(forumsEntity forumId) {
		return reportPostRepository.countAllByForumPostReportId(forumId);
	}
}
