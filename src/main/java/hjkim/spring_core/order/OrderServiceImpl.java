package hjkim.spring_core.order;

import hjkim.spring_core.discount.DiscountPolicy;
import hjkim.spring_core.member.Member;
import hjkim.spring_core.member.MemberRepository;
import hjkim.spring_core.member.MemoryMemberRepository;

    public class OrderServiceImpl implements OrderService {

        private final MemberRepository memberRepository;
        private final DiscountPolicy discountPolicy;

        public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
            this.memberRepository = memberRepository;
            this.discountPolicy = discountPolicy;
        }

        @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        /*
        OrderService 입장에서는 discountPrice 값이 무엇인지 모르겠으니 DiscountPolicy 에게 연산을 맡긴다.
        단일 체계 원칙을 올바르게 준수한 케이스
         */
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
