package parkingnomad.adaptor.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import parkingnomad.application.port.in.usecase.FindMemberUseCase;
import parkingnomad.dto.member.MemberResponse;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final FindMemberUseCase findMemberUseCase;

    public MemberController(FindMemberUseCase findMemberUseCase) {
        this.findMemberUseCase = findMemberUseCase;
    }

    @GetMapping("{id}")
    public ResponseEntity<MemberResponse> findMember(@PathVariable final Long id) {
        MemberResponse member = findMemberUseCase.findMember(id);
        return ResponseEntity.ok(member);
    }
}
