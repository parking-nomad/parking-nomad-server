package parkingnomad.adaptor.out.persistence;

import org.springframework.stereotype.Component;
import parkingnomad.application.port.out.persistence.FavoriteAreaRepository;
import parkingnomad.domain.FavoriteArea;

import java.util.Optional;

import static java.util.Objects.isNull;

@Component
public class JpaFavoriteAreaRepository implements FavoriteAreaRepository {

    private final JpaFavoriteAreaAdaptor favoriteAreas;
    private final JpaFavoriteAreaMapper mapper;

    public JpaFavoriteAreaRepository(
            final JpaFavoriteAreaAdaptor favoriteAreas,
            final JpaFavoriteAreaMapper mapper
    ) {
        this.favoriteAreas = favoriteAreas;
        this.mapper = mapper;
    }

    @Override
    public FavoriteArea saveFavoriteArea(final FavoriteArea favoriteArea) {
        final JpaFavoriteAreaEntity jpaEntity = mapper.toJpaEntity(favoriteArea);
        favoriteAreas.save(jpaEntity);
        return mapper.toDomainEntity(jpaEntity);
    }

    @Override
    public Optional<FavoriteArea> findById(final Long id) {
        final JpaFavoriteAreaEntity jpaFavoriteAreaEntity = favoriteAreas.findById(id)
                .orElse(null);
        if (isNull(jpaFavoriteAreaEntity)) {
            return Optional.empty();
        }
        return Optional.of(mapper.toDomainEntity(jpaFavoriteAreaEntity));
    }
}
