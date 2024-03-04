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
- 동작은 하지만 과연 OCP, DIP를 잘 준수한 코드인가?

```java
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository = new MemoryMemberRepository(); // 추상화에도 의존하고, 구체화에도 의존한다.
    ...
```

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
- 테스트 결과 동작을 한다. 그러나 정액할인이 정률할인으로 할인정책이 변경되었다고 했을 때 유연하게 코드를 변경할 수 있을까?

#### OrderServiceImpl

```java
public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
  ...
}
```
</details>
