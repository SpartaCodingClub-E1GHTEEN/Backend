package com.sparta.first.project.eighteen.common;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

	@CreatedDate
	@Column(nullable = false)
	private LocalDateTime createdAt;

	// User / String
	@CreatedBy
	@Column(nullable = false)
	private String createdBy;

	@LastModifiedDate
	private LocalDateTime modifiedAt;

	@LastModifiedBy
	private String modifiedBy;

	// 소프트 삭제가 되었는지 (isSoftDeleted?)
	// True - False
	private Boolean isDeleted;
	// 소프트 삭제가 된 날짜 (Null - Not Null)
	private LocalDateTime deletedAt;
	private String deletedBy;
}
