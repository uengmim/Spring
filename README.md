# Spring

## Spring의 특징
![1](https://user-images.githubusercontent.com/72143238/170435992-70a0384d-c1a1-4560-8894-62ed7fb3815b.JPG)
<br>
**POJO - 자바 프레임의 클래스**

### IoC/DI
**IoC (Inversion Of Control)** <br>
스프링에서는 일반적인 Java객체를 new로 생성하여 개발자가 관리하는 것이 아닌 Spring Container에 모두 맡긴다.<br>
즉, 개발자에서 -> 프레임워크로 제어의 객체 관리의 권한이 넘어갔음으로 제어의 역전이라고 한다.<br>

**DI (Dependency Injenction)**
- 의존선으로부터 격리시켜 코드 테스트에 용이.
- DI를 통하여, 불가능한 상황을 Mock와 같은 기술을 통해 안정적으로 테스트 가능
- 코드를 확장하거나 변경할 때 영향을 최소화함.
- 순환 참조를 막을 수 있음.
<br>
### AOP <br>
**Aop (Aspect Oriented Programming, 관전지향 프로그램)**<br>
스프링 어플리케이션은 대부분 ㅌMVC웹 어플리케이션에서는 Web Layer, Business Layer, Data Layer로 정의 <br>
*Web Layer: Rest API를 제공하며 Client 중심의 로직 적용* <br>
*Business Layer : 내부 정책에 따른 logic를 개발하며, 주로 해당 부분을 개발* <br>
*Data Layer : 데이터 베이스 및 외부와의 연동을 처리* <br>

![image](https://user-images.githubusercontent.com/72143238/170861464-95df511b-60a0-47a2-a06f-efd2733ca469.png)
