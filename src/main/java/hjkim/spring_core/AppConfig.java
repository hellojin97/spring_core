package hjkim.spring_core;

import hjkim.spring_core.discount.DiscountPolicy;
import hjkim.spring_core.discount.FixDiscountPolicy;
import hjkim.spring_core.discount.RateDiscountPolicy;
import hjkim.spring_core.member.MemberRepository;
import hjkim.spring_core.member.MemberService;
import hjkim.spring_core.member.MemberServiceImpl;
import hjkim.spring_core.member.MemoryMemberRepository;
import hjkim.spring_core.order.OrderService;
import hjkim.spring_core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 애플리케이션의 실제 동작에 필요한 구현 객체를 생성
 */
@Configuration
public class AppConfig {
    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(getMemberRepository()); // 생성자 주입
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
