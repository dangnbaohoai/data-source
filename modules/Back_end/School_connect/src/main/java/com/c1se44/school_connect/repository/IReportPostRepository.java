package com.c1se44.school_connect.repository;

import com.c1se44.school_connect.entity.forumsEntity;
import com.c1se44.school_connect.entity.postsEntity;
import com.c1se44.school_connect.entity.reportPostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface IReportPostRepository extends JpaRepository<reportPostEntity,Long> {
	Page<reportPostEntity> findAll(Pageable pageable);
	Page<reportPostEntity> findAllByForumPostReportId(forumsEntity forumId, Pageable pageable);
	reportPostEntity findByReportPostId(Long id);
	Boolean existsByReportPostId(Long id);
	
	@Modifying
	@Transactional
	@Query(value = "delete from report_post where id=?", nativeQuery = true)
	void deleteByReportPostId(Long reportPostId);
	
	int countAllByForumPostReportId(forumsEntity forumId);
	
	@Modifying
	@Transactional
	void deleteByPostReportId(postsEntity reportPostId);
}
