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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@MappedSuperclass
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

	@CreatedDate
	@Column(nullable = false, updatable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	protected LocalDateTime createdAt;

	@CreatedBy
	@Column(nullable = false, updatable = false)
	protected String createdBy;

	@LastModifiedDate
	@Temporal(value = TemporalType.TIMESTAMP)
	protected LocalDateTime modifiedAt;

	@LastModifiedBy
	protected String modifiedBy;

	// 소프트 삭제가 되었는지 (isSoftDeleted?)
	// True - False
	protected Boolean isDeleted;
	// 소프트 삭제가 된 날짜 (Null - Not Null)
	@Temporal(value = TemporalType.TIMESTAMP)
	protected LocalDateTime deletedAt;
	protected String deletedBy;

	public void delete(boolean flag, String userId) {
		this.deletedAt = LocalDateTime.now();
		this.isDeleted = flag;
		this.deletedBy = userId;
	}
}
