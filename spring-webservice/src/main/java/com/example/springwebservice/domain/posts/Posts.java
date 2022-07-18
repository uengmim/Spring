package com.giung.webservice.domain.posts;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.giung.webservice.domain.BaseTimeEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 * Posts클래스는 실제 DB테이블과 매칭될 클래스 => Entity 클래스
 * JPA를 사용하면 DB에 접근할 경우 Entity 클래스를 통해 작업한다.
 * 
 * @Entity
 * -테이블과 링크될 클래스임을 나타낸다
 * -언더스코어 네이밍(_)으로 이름을 매칭한다.
 * ex) SalesManager.java -> sales_manager table
 * 
 * @id
 * -해당 테이블의 PK필드를 나타낸다.
 * 
 * @GeneratedValue
 * -PK의 생성 규칙을 나타낸다.
 * -기본값은 auto로 자동 증가하는 정수형이 된다.
 * -SpringBoot2.0(공용 시퀀스 테이블을 사용하기 때문)에서는 추가 옵션을 설정하여야 auto_increment가 된다.
 * 
 * @Column
 * -설정하지 않아도 클래스의 필드가 컬럼에 매칭되지만 기본값 이외에 추가 변경이 필요한 옵션이 있을경우 명시하여 사용한다.
 * -문자열 기본값을 500으로 늘리거나(title) 타입을 text로 변경(content)하고 싶을경우
 * 
 * @NoArgsConstructor, @Getter, @Builder
 * -롬복 라이브러리 어노테이션
 * -@NoArgsConstructor : 기본 생성자 자동 추가
 * 		* access = AcessLevel.PROTECTED : 기본 생성자의 접근 권한을 PROTECTED로 제한
 * 		* protected Posts(){}와 같은 효과
 * 		# Entity 클래스를 프로젝트 코드 상에서 기본 생성자로 생성하는 것은 막되, JPA에서 Entity 클래스를 생성하는 것은 허용하기 위해 추가
 * 		* @Getter : 클래스 내 모든 필드의 Getter 메소드 자동 생성
 *      * @Builder : 해당 클래스의 빌더패턴 클래스를 생성
 *      
 *      * 무분별한 setter메서드를 생성하는 것 보다는 목적과 의미가 명확한 메서드를 선언하고 그 안에 setter메서드를 생성하는것이 유지보수 측면에서 효율적이다.
 *      ex) 
 *      	(X)
 *      	public class Order{
 *      		public void setStatus(boolean status){
 *      			this.status = status;
 *      		}
 *      	}
 *      
 *      	public void 주문서비스의_취소메서드(){
 *      		order.setStatus(false);
 *      	}
 *      	
 *      	(O)
 *      	public class Order{
 *      		public void cancelOrder(){
 *      			this.status = false;
 *      		} 
 *      	}
 *     
 *     		public void 주문서비스의_취소메서드(){
 *     			order.cancelOrder();
 *     		}
 * 
 * 		
 * 		DB에 insert하는 구조는 생성자를 통해 최종 값을 채운 후에 DB에 insert하며 값이 변경할 필요가 있을 경우
 * 		public 메소드를 호출하여 변경하는 방식으로 한다.
 * 		
 * 		생성자대신 builder를 사용하는 이유는 변경하는 필드가 무엇인지 명확히 지정할수 있기 때문에 사용한다.
 * 		ex) 
 * 			public make(String a, String b){
 * 				this.a = a;
 * 				this.b = b;
 * 			}
 *				-> 문제는 make(b,a)와 같이 되어도 문제를 쉽게 찾을 수 없다.
 * 	
 * 			하지만 builder를 사용하면
 * 			make.builder()
 * 				.a(a)
 * 				.b(b)
 * 				.build();
 * 			와 같이 어느 필드에 어떠한 값을 넣어야 할지 명확히 파악할 수 있다.
 * 
 * 
 * 	*Entity클래스(서비스/비지니스 로직들에 사용) / DTO(뷰단에서 사용) 을 분리하여 사용해야 한다. 
 * 	 DTO는 빈번하게 수정이 일어나게 되는대 Entity클래스를 사용하게 되면 여러 로직에 영향이 가기 때문이다.
 * 
 * */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Posts extends BaseTimeEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 500, nullable = false)
	private String title;
	
	@Column(columnDefinition = "TEXT", nullable = false)
	private String content;
	
	private String author;
	
	@Builder
	public Posts(String title, String content, String author) {
		this.title = title;
		this.content = content;
		this.author = author;
	}
	
}
