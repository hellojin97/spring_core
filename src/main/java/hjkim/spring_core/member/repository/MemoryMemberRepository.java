package hjkim.spring_core.member.repository;

import hjkim.spring_core.member.domain.Member;

import java.util.HashMap;
import java.util.Map;

public class MemoryMemberRepository implements MemberRepository {

    //Concurrent 해시맵이 동시성 해결을 위해 사용하는게 좋지만 예제니까...
    private static Map<Long, Member> store = new HashMap<>();

    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
    }

    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }
}
