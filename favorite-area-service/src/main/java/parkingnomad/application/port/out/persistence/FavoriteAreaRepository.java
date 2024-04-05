package parkingnomad.application.port.out.persistence;

import parkingnomad.domain.FavoriteArea;

import java.util.Optional;

public interface FavoriteAreaRepository {
    FavoriteArea saveFavoriteArea(final FavoriteArea favoriteArea);

    Optional<FavoriteArea> findById(final Long id);
}
