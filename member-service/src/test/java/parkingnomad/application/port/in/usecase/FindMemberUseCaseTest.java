package parkingnomad.application.port.in.usecase;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import parkingnomad.application.port.out.persistence.MemberRepository;
import parkingnomad.domain.Member;
import parkingnomad.dto.member.MemberResponse;
import parkingnomad.support.BaseTestWithContainers;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

class FindMemberUseCaseTest extends BaseTestWithContainers {

    @Autowired
    FindMemberUseCase findMemberUseCase;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("저장된 멤버를 조회한다.")
    void findMember() {
        //given
        Member savedMember = memberRepository.saveMember(Member.createWithoutId("sub", "member"));

        //when
        MemberResponse member = findMemberUseCase.findMember(savedMember.getId());

        //then
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(member.id()).isEqualTo(savedMember.getId());
            softAssertions.assertThat(member.name()).isEqualTo(savedMember.getName());
        });
    }
}