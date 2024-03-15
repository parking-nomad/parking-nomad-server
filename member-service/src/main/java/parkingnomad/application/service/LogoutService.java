package parkingnomad.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parkingnomad.application.port.in.usecase.LogoutUseCase;
import parkingnomad.application.port.out.persistence.RefreshTokenRepository;

@Service
@Transactional(readOnly = true)
public class LogoutService implements LogoutUseCase {

    private final RefreshTokenRepository refreshTokenRepository;

    public LogoutService(final RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    @Transactional
    public void logout(final String refreshToken) {
        refreshTokenRepository.deleteByRefreshToken(refreshToken);
    }
}
