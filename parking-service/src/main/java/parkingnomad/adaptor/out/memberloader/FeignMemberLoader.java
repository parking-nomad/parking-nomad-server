package parkingnomad.adaptor.out.memberloader;

import feign.FeignException;
import org.springframework.stereotype.Component;
import parkingnomad.application.port.out.MemberLoader;
import parkingnomad.dto.member.MemberResponse;

import static java.util.Objects.isNull;

@Component
public class FeignMemberLoader implements MemberLoader {

    private final MemberLoaderAdaptor memberLoaderAdaptor;

    public FeignMemberLoader(MemberLoaderAdaptor memberLoaderAdaptor) {
        this.memberLoaderAdaptor = memberLoaderAdaptor;
    }

    @Override
    public boolean isExistedMember(final Long memberId) {
        try {
            MemberResponse memberResponse = memberLoaderAdaptor.findMemberById(memberId, memberId);
            return !isNull(memberResponse);
        } catch (FeignException.FeignClientException exception) {
            return false;
        }
    }
}
