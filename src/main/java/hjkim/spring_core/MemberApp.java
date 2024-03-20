package hjkim.spring_core;

import hjkim.spring_core.member.domain.Grade;
import hjkim.spring_core.member.domain.Member;
import hjkim.spring_core.member.service.MemberService;
import hjkim.spring_core.member.service.MemberServiceImpl;

public class MemberApp {

    public static void main(String[] args) {

        // 순수한 자바 개발
        // main 메서드를 통해서 테스트를 하는 것은 한계가 존재
        MemberService memberService = new MemberServiceImpl();
        Member memberA = new Member(1L, "memberA", Grade.VIP);
        memberService.join(memberA);

        Member findMember = memberService.findMember(1L);
        System.out.println("newMember = " + memberA.getName());
        System.out.println("findMember = " + findMember.getName());
    }
}
