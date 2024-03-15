package parkingnomad.application.port.in.usecase;


import parkingnomad.application.port.in.usecase.dto.TokenResponse;

public interface RefreshTokensUseCase {
    TokenResponse refreshTokens(final String refreshToken);
}
