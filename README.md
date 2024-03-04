# Spring Core Project

## Goal

> 순수 자바 + 스프링을 통해서 객체지향에 좀 더 접근한다.

## Stack

- OpenJDK21(Java 21), Spring 3.2.3, Gradle

## History

<details>
<summary>DIP, OCP를 준수했는가</summary>

- 회원 서비스를 만들어서 회원가입과 회원조회 기능을 구현
  - [MemberServiceImpl](./src/main/java/hjkim/spring_core/member/MemberServiceImpl.java)
- 테스트를 진행했을 때 두 가지 기능 모두 정상 작동
- 동작은 하지만 과연 OCP, DIP를 잘 준수한 코드인가?
```java
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository = new MemoryMemberRepository(); // 추상화에도 의존하고, 구체화에도 의존한다.
    ...
```


</details>