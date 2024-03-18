package parkingnomad.application.port.in.usecase;

import parkingnomad.dto.member.MemberResponse;

public interface FindMemberUseCase {
    MemberResponse findMember(final Long longMemberId, final Long id);
}
