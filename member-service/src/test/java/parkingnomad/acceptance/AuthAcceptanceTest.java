package parkingnomad.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import parkingnomad.application.port.out.persistence.MemberRepository;
import parkingnomad.application.port.out.persistence.RefreshTokenRepository;
import parkingnomad.domain.Member;
import parkingnomad.domain.UserInfo;
import parkingnomad.domain.oauth.clients.OAuthClients;
import parkingnomad.support.BaseTestWithContainers;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class AuthAcceptanceTest extends BaseTestWithContainers {
    @LocalServerPort
    int port;

    @MockBean
    OAuthClients oAuthClients;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    RefreshTokenRepository refreshTokenAdaptor;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("kakao 소셜 로그인을 한다.")
    void kakaoSocialLogin() {
        //given
        final String provider = "kakao";
        final UserInfo userInfo = UserInfo.of("sub", provider, "nickname");
        when(oAuthClients.getUserInfo(anyString(), eq(provider))).thenReturn(userInfo);

        // when
        final Response response = socialLogin(provider);

        //then
        assertSoftly(softAssertions -> {
            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(response.cookie("refresh_token")).isNotBlank();
            assertThat(response.jsonPath().getString("accessToken")).isNotBlank();
        });
    }

    @Test
    @DisplayName("refreshToken을 통해 토큰을 새로 발급한다.")
    void refreshToken() {
        // given
        final Member member = memberRepository.saveMember(Member.createWithoutId("asdfasdf", "nickname"));
        refreshTokenAdaptor.save("refresh_token", member.getId());

        //when
        final Response response = given().log().all()
                .cookie("refresh_token", "refresh_token")
                .when()
                .get("/api/auth/refresh");

        //then
        assertSoftly(softAssertions -> {
            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(response.cookie("refresh_token")).isNotBlank();
            assertThat(response.jsonPath().getString("accessToken")).isNotBlank();
        });
    }

    @Test
    @DisplayName("로그아웃을 한다.")
    void logout() {
        //given
        final String provider = "kakao";
        final UserInfo userInfo = UserInfo.of("sub", provider, "nickname");
        when(oAuthClients.getUserInfo(anyString(), eq(provider))).thenReturn(userInfo);

        final Response socialLoginResponse = socialLogin(provider);
        final String refreshToken = socialLoginResponse.cookie("refresh_token");

        //when
        final Response response = given().log().all()
                .cookie("refresh_token", refreshToken)
                .when()
                .get("/api/auth/logout");

        //then
        assertThat(response.cookie(refreshToken)).isBlank();
    }

    public static Response socialLogin(final String provider) {
        return given().log().all()
                .queryParam("code", "code")
                .pathParam("provider", provider)
                .when()
                .get("/api/auth/{provider}");
    }
}
