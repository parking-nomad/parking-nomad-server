package parkingnomad.adaptor.out.memberloader;

import org.springframework.stereotype.Component;
import parkingnomad.application.port.out.memberloader.MemberLoader;
import parkingnomad.dto.member.MemberResponse;

import static java.util.Objects.isNull;

@Component
public class FeignMemberLoader implements MemberLoader {

    private final MemberLoaderAdaptor memberLoaderAdaptor;

    public FeignMemberLoader(final MemberLoaderAdaptor memberLoaderAdaptor) {
        this.memberLoaderAdaptor = memberLoaderAdaptor;
    }

    @Override
    public boolean isExistedMember(final Long memberId) {
        MemberResponse memberResponse = memberLoaderAdaptor.findMemberById(memberId, memberId);
        return !isNull(memberResponse);
    }
}
