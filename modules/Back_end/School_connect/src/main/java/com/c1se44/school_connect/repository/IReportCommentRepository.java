package com.c1se44.school_connect.repository;

import com.c1se44.school_connect.entity.commentEntity;
import com.c1se44.school_connect.entity.forumsEntity;
import com.c1se44.school_connect.entity.reportCommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface IReportCommentRepository extends JpaRepository<reportCommentEntity,Long> {
	Page<reportCommentEntity> findAll(Pageable pageable);
	Page<reportCommentEntity> findAllByForumCommentReportId(forumsEntity forumId, Pageable pageable);
	reportCommentEntity findByReportCommentId(Long reportId);
	Boolean existsByReportCommentId(Long reportId);
	int countAllByForumCommentReportId(forumsEntity forumId);
	
	@Modifying
	@Transactional
	@Query(value = "delete from report_comments where id=?", nativeQuery = true)
	void deleteByReportCommentId(Long reportCommentId);
	
	@Modifying
	@Transactional
	void deleteByCommentsReportId(commentEntity CommentId);
}
