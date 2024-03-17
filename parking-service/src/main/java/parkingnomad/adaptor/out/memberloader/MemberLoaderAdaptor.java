package parkingnomad.adaptor.out.memberloader;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import parkingnomad.dto.member.MemberResponse;

@FeignClient(name = "member-service", path = "/api/members")
public interface MemberLoaderAdaptor {

    @GetMapping("{id}")
    MemberResponse findMemberById(@PathVariable final Long id);
}
