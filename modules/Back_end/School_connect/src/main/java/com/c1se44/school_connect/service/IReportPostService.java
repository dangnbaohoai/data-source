package com.c1se44.school_connect.service;

import com.c1se44.school_connect.entity.forumsEntity;
import com.c1se44.school_connect.entity.postsEntity;
import com.c1se44.school_connect.entity.reportChatEntity;
import com.c1se44.school_connect.entity.reportPostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IReportPostService {
	reportPostEntity save(reportPostEntity reportChatEntity);
	List<reportPostEntity> findAll(Pageable pageable);
	List<reportPostEntity> findAllByForumPostReportId(forumsEntity forumId, Pageable pageable);
	void deleteByReportPostId(Long reportPostId);
	reportPostEntity findByReportPostId(Long id);
	Boolean existsByReportPostId(Long id);
	void deleteByPostReportId(postsEntity reportPostId);
	int countAllByForumPostReportId(forumsEntity forumId);
}
