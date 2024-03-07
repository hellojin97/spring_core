# Spring Core Project

## Goal

> 순수 자바 + 스프링을 통해서 객체지향에 좀 더 접근한다.

## Stack

- OpenJDK21(Java 21), Spring 3.2.3, Gradle

## History

<details>
<summary>DIP, OCP를 준수했는가</summary>

### 회원(Member)

- 회원 서비스를 만들어서 회원가입과 회원조회 기능을 구현
    - [MemberServiceImpl](./src/main/java/hjkim/spring_core/member/MemberServiceImpl.java)
- 테스트를 진행했을 때 두 가지 기능 모두 정상 작동
- **_동작은 하지만 과연 OCP, DIP를 잘 준수한 코드인가?_**

```java
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository = new MemoryMemberRepository(); // 추상화에도 의존하고, 구체화에도 의존한다.
    ...
```

---

### 주문(Order) 과 할인정책(DiscountPolicy)

- 할인정책은 2가지로 정의
  1. 정액할인(VIP 경우 1000원 할인)
  2. 정률할인(VIP 경우 10% 할인)

#### 정액할인(FixDiscountPolicy)

```java
public class FixDiscountPolicy implements DiscountPolicy{

    private int discountFixAmount = 1000;

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return discountFixAmount;
        } else {
            return 0;
        }
    }
}
```

- 추상체인 [OrderService](./src/main/java/hjkim/spring_core/order/OrderService.java)를
  구현하는 [OrderServiceImpl](./src/main/java/hjkim/spring_core/order/OrderServiceImpl.java)를 생성
- 테스트 결과 동작을 한다. 그러나 정액할인이 정률할인으로 **_할인정책이 변경되었다고 했을 때 유연하게 코드를 변경할 수 있을까?_**

#### OrderServiceImpl

```java
public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
  ...
}
```
</details>

<details>
<summary>DIP, OCP 개선(관심자 분리)</summary>

> 배우는 배우의 역할만 충실히 하면 된다. 남배우가 여배우의 캐스팅까지 책임질 필요는 없다.  
> 이러한 역할 분리를 위한 캐스팅 디렉터가 필요하다.

#### 기존 코드의 문제점
```java
public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
  ...
}
```
- 서비스 구현부에서는 추상화 객체를 **지향**해야 하고 구체화 객체를 **지양**해야 한다.
- 그러나, `memberRepository`와 `discountPolicy`의 선언 시 초기화는 각각의 구체화된 객체를 선언한다.
- 이는 구현체의 의존성까지 가지게 되기에 **DIP** 위반

#### 코드 개선
```java
public class AppConfig {
    public MemberService memberService() {
        return new MemberServiceImpl(new MemoryMemberRepository());
    }

    public OrderService orderService() {
        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
    }
}
```
- 애플리케이션의 실제 동작에 필요한 구현 객체를 따로 생성하는 [AppConfig](./src/main/java/hjkim/spring_core/AppConfig.java)생성
- 각 서비스 구현부에서는 생성자를 통한 추상화 초기화로 정리하면 아래와 같이 구체화된 내용은 사라지고 추상화(인터페이스)만 남게 됨.

```java
public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }
}
```
</details>


<details>
<summary>스프링 컨테이너 생성</summary>

- AppConfig ← `@Configuration` 을 통해 스프링 컨테이너의 구성 정보를 넣는다고 명시
  - AppConfig 내에 구체화되는 메서드 ← `@Bean`

      ```java
      @Configuration
      public class AppConfig {
          @Bean
          public MemberService memberService() {
              return new MemberServiceImpl(getMemberRepository());
          }
      
          @Bean
          public OrderService orderService() {
              return new OrderServiceImpl(getMemberRepository(), discountPolicy());
          }
      
          @Bean
          public MemberRepository getMemberRepository() {
              return new MemoryMemberRepository();
          }
      
          @Bean
          public DiscountPolicy discountPolicy() {
              return new RateDiscountPolicy();
          }
      
      }
      ```

- AppConfig ← `@Configuration` 을 통해 스프링 컨테이너의 구성 정보를 넣는다고 명시
  - AppConfig 내에 구체화되는 메서드 ← `@Bean`

      ```java
      @Configuration
      public class AppConfig {
          @Bean
          public MemberService memberService() {
              return new MemberServiceImpl(getMemberRepository());
          }
      
          @Bean
          public OrderService orderService() {
              return new OrderServiceImpl(getMemberRepository(), discountPolicy());
          }
      
          @Bean
          public MemberRepository getMemberRepository() {
              return new MemoryMemberRepository();
          }
      
          @Bean
          public DiscountPolicy discountPolicy() {
              return new RateDiscountPolicy();
          }
      
      }
      ```

- ApplicationContext : 스프링 컨테이너
  - AnnotationConfigApplicationContext 인스턴스를 생성하여 AppConfig.class 를 주입

      ```java
      /*
      ApplicationContext : 스프링 컨테이너
       */
      ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
      MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
      OrderService orderService = applicationContext.getBean("orderService", OrderService.class);
      
      Long memberId = 1L;
      Member member = new Member(memberId, "memberA", Grade.VIP);
      memberService.join(member);
      
      Order order = orderService.createOrder(memberId, "itemA", 10000);
      
      System.out.println("order = " + order); 
      ```

    - `AnnotationConfigApplicationContext(AppConfig.class)`
      - AppConfig 내 @Configuration → @Bean 을 인식, Bean 객체들 모두 생성
  - AppConfig.서비스메서드 → applicationContext.getBean

      ```java
      MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
      OrderService orderService = applicationContext.getBean("orderService", OrderService.class);
      ```
    - *getBean(”메서드 이름”, 불러오는 타입 클래스)*
</details>