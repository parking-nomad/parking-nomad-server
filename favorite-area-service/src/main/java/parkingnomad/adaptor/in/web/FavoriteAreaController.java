package parkingnomad.adaptor.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import parkingnomad.application.port.in.CreateFavoriteAreaUseCase;
import parkingnomad.application.port.in.dto.CreateFavoriteAreaRequest;
import parkingnomad.resolver.auth.AuthMember;

import java.net.URI;

@RestController
@RequestMapping("/api/favorite-areas")
public class FavoriteAreaController {

    private final CreateFavoriteAreaUseCase createFavoriteAreaUseCase;

    public FavoriteAreaController(final CreateFavoriteAreaUseCase createFavoriteAreaUseCase) {
        this.createFavoriteAreaUseCase = createFavoriteAreaUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> createFavoriteArea(
            @RequestBody final CreateFavoriteAreaRequest request,
            @AuthMember final Long memberId
    ) {
        final Long savedId = createFavoriteAreaUseCase.createFavoriteArea(memberId, request);
        return ResponseEntity.created(URI.create("/api/favorite-areas/" + savedId)).build();
    }
}
