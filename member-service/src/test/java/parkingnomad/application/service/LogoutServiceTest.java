package parkingnomad.application.service;

import parkingnomad.application.port.out.persistence.RefreshTokenRepository;
import parkingnomad.support.BaseTestWithContainers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class LogoutServiceTest extends BaseTestWithContainers {

    @Autowired
    LogoutService logoutService;

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Test
    @DisplayName("로그아웃을 하면 리프레시토큰을 삭제한다.")
    void logout() {
        //given
        final String refreshToken = "refreshToken";
        refreshTokenRepository.save(refreshToken, 20L);

        //when
        logoutService.logout(refreshToken);

        //then
        final Optional<Long> memberId = refreshTokenRepository.findMemberIdByRefreshToken(refreshToken);
        assertThat(memberId).isNotPresent();
    }
}
