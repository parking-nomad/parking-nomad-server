package parkingnomad.application.service;

import org.springframework.stereotype.Service;
import parkingnomad.application.port.in.usecase.FindMemberUseCase;
import parkingnomad.dto.member.MemberResponse;
import parkingnomad.application.port.out.persistence.MemberRepository;
import parkingnomad.domain.Member;
import parkingnomad.exception.member.NonExistentMemberException;

import static parkingnomad.exception.member.MemberExceptionCode.NON_EXISTENT_MEMBER;

@Service
public class FindMemberService implements FindMemberUseCase {

    private final MemberRepository memberRepository;

    public FindMemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public MemberResponse findMember(Long id) {
        final Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NonExistentMemberException(NON_EXISTENT_MEMBER.getCode(), id));
        return new MemberResponse(member.getId(), member.getName());
    }
}
