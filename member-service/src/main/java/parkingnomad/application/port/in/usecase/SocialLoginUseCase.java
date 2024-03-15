package parkingnomad.application.port.in.usecase;

import parkingnomad.application.port.in.usecase.dto.TokenResponse;
import parkingnomad.domain.oauth.provider.OAuthProvider;

public interface SocialLoginUseCase {
    TokenResponse socialLogin(String code, OAuthProvider oAuthProvider);
}
