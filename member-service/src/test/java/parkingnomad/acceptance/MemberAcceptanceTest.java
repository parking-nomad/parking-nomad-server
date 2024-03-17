package parkingnomad.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import parkingnomad.domain.UserInfo;
import parkingnomad.domain.oauth.clients.OAuthClients;
import parkingnomad.dto.member.MemberResponse;
import parkingnomad.support.BaseTestWithContainers;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class MemberAcceptanceTest extends BaseTestWithContainers {

    @LocalServerPort
    int port;

    @MockBean
    OAuthClients oAuthClients;

    @BeforeEach
    void setUp() {
        RestAssured.port = this.port;
    }

    @Test
    @DisplayName("회원id를 통해 조회한다.")
    void findMember() {
        //given
        final String provider = "kakao";
        final String nickname = "nickname";
        final UserInfo userInfo = UserInfo.of("sub", provider, nickname);
        when(oAuthClients.getUserInfo(anyString(), eq(provider))).thenReturn(userInfo);
        AuthAcceptanceTest.socialLogin(provider);

        final Long id = 1L;

        //when
        final ExtractableResponse<Response> response = given().log().all()
                .pathParam("id", id)
                .when()
                .get("/api/members/{id}")
                .then().log().all()
                .extract();

        //then
        MemberResponse memberResponse = response.as(MemberResponse.class);
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(memberResponse.id()).isEqualTo(id);
            softAssertions.assertThat(memberResponse.name()).isEqualTo(nickname);
        });

    }

}
