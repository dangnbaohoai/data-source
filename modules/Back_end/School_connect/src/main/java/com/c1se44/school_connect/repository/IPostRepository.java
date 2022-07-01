package com.c1se44.school_connect.repository;

import com.c1se44.school_connect.entity.postsEntity;
import com.c1se44.school_connect.entity.userEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface IPostRepository extends JpaRepository<postsEntity, Long> {
	
	postsEntity findByPostId(Long postId);
	
	@Query(value = "select p from postsEntity p where p.postId in :ids")
	List<postsEntity> findAllByPostIdIsIn(@Param("ids") List<Long> postId);
	
	Boolean existsByPostIdAndUserPostId(Long postId, userEntity user);
	
	Page<postsEntity> findByUserPostId(userEntity user, Pageable pageable);
	
	@Modifying
	@Transactional
	void deleteByPostIdAndUserPostId(Long postId, userEntity user);
	
	@Modifying
	@Transactional
	void deleteByPostId(Long postId);
	
	@Query(value = "select count(*) from post", nativeQuery = true)
	int countAllPostId();
	
	@Query(value = "select count(*) from post where user_post=?", nativeQuery = true)
	int countAllPostIdByUserPostId(Long userCreateId);
	
	@Query(value = "select * from post where YEAR(date_of_event) = ? and MONTH(date_of_event) = ? AND id IN(?)", nativeQuery = true)
	List<postsEntity> findAllByYearAndMonthAndPostIdIN(int year, int month, List<Long> postId);
	
	@Modifying
	@Transactional
	@Query(value = "update post SET  number_like=? where id=?", nativeQuery = true)
	void updateNumberLikeByPostId(int numberLike, Long postId);
}
