package parkingnomad.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parkingnomad.application.port.in.CreateFavoriteAreaUseCase;
import parkingnomad.application.port.in.dto.CreateFavoriteAreaRequest;
import parkingnomad.application.port.out.memberloader.MemberLoader;
import parkingnomad.application.port.out.persistence.FavoriteAreaRepository;
import parkingnomad.domain.FavoriteArea;
import parkingnomad.exception.NonExistentMemberException;

import static parkingnomad.exception.AreaErrorCode.NON_EXISTENT_MEMBER;

@Service
@Transactional
public class CreateFavoriteAreaService implements CreateFavoriteAreaUseCase {

    private final FavoriteAreaRepository favoriteAreaRepository;
    private final MemberLoader memberLoader;

    public CreateFavoriteAreaService(
            final FavoriteAreaRepository favoriteAreaRepository,
            final MemberLoader memberLoader
    ) {
        this.favoriteAreaRepository = favoriteAreaRepository;
        this.memberLoader = memberLoader;
    }

    @Override
    public Long createFavoriteArea(final Long memberId, final CreateFavoriteAreaRequest createFavoriteAreaRequest) {
        validateMember(memberId);
        final FavoriteArea favoriteArea = createFavoriteAreaRequest.toDomain(memberId);
        final FavoriteArea saved = favoriteAreaRepository.saveFavoriteArea(favoriteArea);
        return saved.getId();
    }

    private void validateMember(final Long memberId) {
        final boolean isExistedMember = memberLoader.isExistedMember(memberId);
        if (!isExistedMember) {
            throw new NonExistentMemberException(NON_EXISTENT_MEMBER, memberId);
        }
    }
}
