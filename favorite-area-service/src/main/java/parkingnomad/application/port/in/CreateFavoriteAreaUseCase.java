package parkingnomad.application.port.in;

import parkingnomad.application.port.in.dto.CreateFavoriteAreaRequest;

public interface CreateFavoriteAreaUseCase {

    Long createFavoriteArea(final CreateFavoriteAreaRequest createFavoriteAreaRequest);
}
