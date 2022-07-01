package com.c1se44.school_connect.service;

import com.c1se44.school_connect.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IReportCommentService {
	reportCommentEntity save(reportCommentEntity reportCommentEntity);
	List<reportCommentEntity> findAll(Pageable pageable);
	void deleteByReportCommentId(Long reportCommentId);
	reportCommentEntity findByReportCommentId(Long reportId);
	Boolean existsByReportCommentId(Long reportId);
	List<reportCommentEntity> findAllByForumCommentReportId(forumsEntity forumId, Pageable pageable);
	int countAllByForumCommentReportId(forumsEntity forumId);
	void deleteByCommentsReportId(commentEntity CommentId);
}
