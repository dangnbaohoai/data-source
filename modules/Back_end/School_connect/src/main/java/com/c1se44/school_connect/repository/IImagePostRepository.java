package com.c1se44.school_connect.repository;

import com.c1se44.school_connect.entity.imagePostEntity;
import com.c1se44.school_connect.entity.postsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface IImagePostRepository extends JpaRepository<imagePostEntity,Long> {
	List<imagePostEntity> findByPostImageId(postsEntity postsEntity);
	
	@Query(value = "select id from image_post where post_id=?",nativeQuery = true)
	List<Long> findByPostImageId(Long postsId);
	
	@Modifying
	@Transactional
	void deleteByImageId(Long id);
	
	@Modifying
	@Transactional
	void deleteByPostImageId(postsEntity id);
}
