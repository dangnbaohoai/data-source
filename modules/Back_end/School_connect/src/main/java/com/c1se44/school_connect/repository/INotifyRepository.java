package com.c1se44.school_connect.repository;

import com.c1se44.school_connect.entity.notifyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface INotifyRepository extends JpaRepository<notifyEntity,Long> {
}
