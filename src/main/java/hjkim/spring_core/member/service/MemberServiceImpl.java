package hjkim.spring_core.member.service;

import hjkim.spring_core.member.domain.Member;
import hjkim.spring_core.member.repository.MemberRepository;
import hjkim.spring_core.member.repository.MemoryMemberRepository;

public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository = new MemoryMemberRepository();

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
