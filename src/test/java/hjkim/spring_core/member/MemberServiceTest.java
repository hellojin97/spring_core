package hjkim.spring_core.member;

import hjkim.spring_core.member.domain.Grade;
import hjkim.spring_core.member.domain.Member;
import hjkim.spring_core.member.service.MemberService;
import hjkim.spring_core.member.service.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {
    MemberService memberService = new MemberServiceImpl();

    @Test
    @DisplayName("회원가입 테스트")
    void join() {
        //given
        Member member = new Member(1L, "memberA", Grade.VIP);
        //when
        memberService.join(member);
        Member member1 = memberService.findMember(1L);
        //then
        Assertions.assertThat(member).isEqualTo(member1);
    }
}
