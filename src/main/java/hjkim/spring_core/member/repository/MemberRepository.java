package hjkim.spring_core.member.repository;

import hjkim.spring_core.member.domain.Member;

public interface MemberRepository {

    void save(Member member);

    Member findById(Long memberId);
}
