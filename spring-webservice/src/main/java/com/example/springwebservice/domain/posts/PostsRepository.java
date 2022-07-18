package com.giung.webservice.domain.posts;

import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/*
 * iBatis/MyBatis에서 DAO(Data Access Object) 를 JPA에서는 Repository라고 부르며 인터페이스로 생성한다.
 * - 인터페이스 생성
 * - JpaRepository<Entity클래스, PK타입>상속 -> 기본적인 CRUD메소드 자동 생성
 * - @Repository필요 없음
 * 
 * 어느정도 규모가 있는 서비스의 경우 Entity클래스만으로 조회처리가 어려워 조회용 프레임워크를 사용한다.(querydsl 추천)
 * 
 * */
public interface PostsRepository extends JpaRepository<Posts, Long>{
	
	@Query("SELECT p " +
			"FROM Posts p " +
			"ORDER BY p.id DESC")
	Stream<Posts> findAllDesc();
}
