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
import static org.assertj.core.api.Assertions.assertThat;
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
                .header("X-Member-Id", id)
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

    @Test
    @DisplayName("회원id와 일치하는 회원이 없는 경우 400을 응답한다.")
    void findMemberFailNonExistent() {
        //given
        final Long id = 0L;

        //when
        final ExtractableResponse<Response> response = given().log().all()
                .pathParam("id", id)
                .header("X-Member-Id", id)
                .when()
                .get("/api/members/{id}")
                .then().log().all()
                .extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }


    @Test
    @DisplayName("회원id와 로그인한 회원이 일치하지 않는 경우 403을 응답한다.")
    void findMemberFailInvalidAccess() {
        //given
        final Long loginId = 0L;
        final Long targetId = 1L;

        //when
        final ExtractableResponse<Response> response = given().log().all()
                .pathParam("id", targetId)
                .header("X-Member-Id", loginId)
                .when()
                .get("/api/members/{id}")
                .then().log().all()
                .extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }
}
