package hjkim.spring_core.member.service;


import hjkim.spring_core.member.domain.Member;

public interface MemberService {

    void join(Member member);

    Member findMember(Long memberId);
}
