package com.c1se44.school_connect.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class baseEntity {
	@Column(name = "innitiated_date",updatable = false ,columnDefinition = "datetime")
	// khai báo annotation để JPA auditing set dữ liệu createDate 1 cách tự động
	@CreatedDate
	private LocalDateTime createDate;
	
	@Column(name = "modification_date",columnDefinition = "datetime")
	// khai báo annotation để JPA auditing set dữ liệu modifiedDate 1 cách tự động
	@LastModifiedDate
	private LocalDateTime modifiedDate;
	
	@Column(name = "creator",updatable = false)
	// khai báo annotation để JPA auditing set dữ liệu createdby 1 cách tự động
	@CreatedBy
	private String createdby;
	
	@Column(name = "repairer")
	// khai báo annotation để JPA auditing set dữ liệu modifiedBy 1 cách tự động
	@LastModifiedBy
	private String modifiedBy;
}
