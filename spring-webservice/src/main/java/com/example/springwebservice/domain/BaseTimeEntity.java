package com.giung.webservice.domain;
/*
 * Entity의 변화가 생길시 생성일,수정일 등을 관리하는 Entity생성
 * 모든 Entity의 상위 클래스가 되어 Entity들의 createdDate, ModifiedDate를 자동으로 관리하는 역할
 * 
 * @MappedSuperclass
 * -JPA Entity 클래스들이 BaseTimeEntity를 상속할 경우 필드(createdDate, modifiedDate)도 컬럼으로 인식하도록 하는 annotation
 * 
 * @EntityListeners(AuditingEntityListener.class)
 * -BaseTimeEntity클래스에 Auditing기능 포함시키기
 * 
 * @CreatedDate
 * -Entity생성될때 시간 자동 저장
 * 
 * @LastModifiedDate
 * -Entity값이 변경될 때 시간 자동 저장
 * */

import java.time.LocalDateTime;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {

	@CreatedDate
	private LocalDateTime createdDate;
	
	@LastModifiedDate
	private LocalDateTime modifiedDate;
	
	
}
