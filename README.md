# 디자인 패턴
** 자주 사용하는 설계 패턴을 정형화 해서 이를 유형별로 가장 최적의 방법으로 개발을 할 수 있도록 정해둔 
설계 알고리즘과 유사하지만 명확하게 정답이 있는 형태는 아니며, 프로젝트의 상황에 맞추어 적용이 가능하다.**
## 디자인 패턴의 장점
1. 개발자 간의 원활한 소통
2. 소프트웨어 구조 파악 용이
3. 재사용을 통한 개발 시간 단축
4. 설계 변경 요청에 대한 유연한 대처

## 디자인 패턴의 단점
1. 객체 지향 설계 / 구현
2. 초기 투자 비용 부담

# Singleton pattern
**Singleton 패턴은 어떠한 클래스가 유일하게 1개만 존재 할 때 사용한다.
이를 주로 사용하는 곳은 서로 자원을 공유 할 때 사용하는데, 실물 세계에서는 프린터가 해당되며,
실제 프로그래밍에서는 TCP Socket 통신에서 서버와 연결된 connect 객체에 주로 사용한다.
<img src="Singleton.jpeg" width="300">
