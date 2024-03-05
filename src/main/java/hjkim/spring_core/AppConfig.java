package hjkim.spring_core;

import hjkim.spring_core.discount.FixDiscountPolicy;
import hjkim.spring_core.member.MemberService;
import hjkim.spring_core.member.MemberServiceImpl;
import hjkim.spring_core.member.MemoryMemberRepository;
import hjkim.spring_core.order.OrderService;
import hjkim.spring_core.order.OrderServiceImpl;

/**
 * 애플리케이션의 실제 동작에 필요한 구현 객체를 생성
 */
public class AppConfig {
    public MemberService memberService() {
        return new MemberServiceImpl(new MemoryMemberRepository()); // 생성자 주입
    }

    public OrderService orderService() {
        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
    }
}
