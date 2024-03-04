package hjkim.spring_core;

import hjkim.spring_core.member.Grade;
import hjkim.spring_core.member.Member;
import hjkim.spring_core.member.MemberService;
import hjkim.spring_core.member.MemberServiceImpl;
import hjkim.spring_core.order.Order;
import hjkim.spring_core.order.OrderService;
import hjkim.spring_core.order.OrderServiceImpl;

public class OrderApp {

    public static void main(String[] args) {
        MemberService memberService = new MemberServiceImpl();
        OrderService orderService = new OrderServiceImpl();

        Long memberId = 1L;
        Member memberA = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(memberA);


        Order order = orderService.createOrder(memberId, "itemA", 10000);

        System.out.println("order = " + order);
        //System.out.println("order.calculatePrice() = " + order.calculatePrice());

    }
}
