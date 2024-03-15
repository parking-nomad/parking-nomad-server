package parkingnomad.application.port.out.persistence;

import parkingnomad.domain.Member;

import java.util.Optional;

public interface MemberRepository {
    Member saveMember(final Member member);

    Optional<Member> findById(final Long id);

    Optional<Member> findBySub(final String sub);
}
