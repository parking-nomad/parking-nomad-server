package parkingnomad.adaptor.out.memberloader;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import parkingnomad.dto.member.MemberResponse;

@FeignClient(name = "member-service", path = "/api/members")
public interface MemberLoaderAdaptor {

    @GetMapping("{id}")
    MemberResponse findMemberById(@RequestHeader("X-Member-Id") final Long loginMemberId, @PathVariable final Long id);
}
