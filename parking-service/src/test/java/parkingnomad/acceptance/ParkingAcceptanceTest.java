package parkingnomad.acceptance;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import parkingnomad.application.port.in.dto.ParkingResponse;
import parkingnomad.application.port.in.dto.SaveParkingRequest;
import parkingnomad.application.port.out.AddressLocator;
import parkingnomad.application.port.out.MemberLoader;
import parkingnomad.support.BaseTestWithContainers;

import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ParkingAcceptanceTest extends BaseTestWithContainers {

    @LocalServerPort
    int port;

    @MockBean
    MemberLoader memberLoader;

    @MockBean
    AddressLocator addressLocator;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("parking을 저장한다.")
    void saveParking() {
        //given
        final long memberId = 1L;
        final int latitude = 20;
        final int longitude = 30;
        final String address = "address";
        final ExtractableResponse<Response> response = saveParking(address, memberId, latitude, longitude);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    private ExtractableResponse<Response> saveParking(final String address, final long memberId, final int latitude, final int longitude) {
        when(memberLoader.isExistedMember(anyLong())).thenReturn(true);
        when(addressLocator.convertToAddress(anyDouble(), anyDouble())).thenReturn(Optional.of(address));

        //when
        return given().log().all()
                .contentType(ContentType.JSON)
                .header("X-Member-Id", memberId)
                .body(new SaveParkingRequest(latitude, longitude))
                .when().post("/api/parkings")
                .then().log().all()
                .extract();
    }

    private static String parseSavedParkingId(final ExtractableResponse<Response> savedParking) {
        final String[] locations = savedParking.header("Location").split("/");
        return locations[locations.length - 1];
    }

    @Test
    @DisplayName("parkingId를 통해 저장된 parking을 조회한다.")
    void findParkingById() {
        //given
        final String address = "address2";
        final long memberId = 2L;
        final int latitude = 40;
        final int longitude = 30;
        final ExtractableResponse<Response> savedParking = saveParking(address, memberId, latitude, longitude);
        final String savedId = parseSavedParkingId(savedParking);

        //when
        final ExtractableResponse<Response> response = given().log().all()
                .contentType(ContentType.JSON)
                .header("X-Member-Id", memberId)
                .pathParam("id", savedId)
                .when().get("/api/parkings/{id}")
                .then().log().all()
                .extract();

        //then
        final ParkingResponse parkingResponse = response.as(ParkingResponse.class);
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(parkingResponse.id()).isEqualTo(Long.parseLong(savedId));
            softAssertions.assertThat(parkingResponse.memberId()).isEqualTo(memberId);
            softAssertions.assertThat(parkingResponse.latitude()).isEqualTo(latitude);
            softAssertions.assertThat(parkingResponse.longitude()).isEqualTo(longitude);
            softAssertions.assertThat(parkingResponse.address()).isEqualTo(address);
        });
    }

    @Test
    @DisplayName("회원의 가장 최근 주차정보를 조회한다.")
    void findLatestParkingByMemberId() throws InterruptedException {
        //given
        final long memberId = 1L;

        final int latitude1 = 20;
        final int longitude1 = 70;
        final String address1 = "address1";
        saveParking(address1, memberId, latitude1, longitude1);

        final int latitude2 = 30;
        final int longitude2 = 80;
        final String address2 = "address2";
        saveParking(address2, memberId, latitude2, longitude2);

        final int latitude3 = 40;
        final int longitude3 = 90;
        final String address3 = "address3";
        final ExtractableResponse<Response> target = saveParking(address3, memberId, latitude3, longitude3);
        final long targetId = Long.parseLong(parseSavedParkingId(target));

        saveParking(address2, 3L, 11, 22);
        saveParking(address2, 4L, 33, 44);

        //when
        Thread.sleep(1000);
        final ExtractableResponse<Response> response = given().log().all()
                .header("X-Member-Id", memberId)
                .when().get("/api/parkings/latest")
                .then().log().all()
                .extract();

        //then
        final ParkingResponse parkingResponse = response.as(ParkingResponse.class);
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(parkingResponse.id()).isEqualTo(targetId);
            softAssertions.assertThat(parkingResponse.memberId()).isEqualTo(memberId);
            softAssertions.assertThat(parkingResponse.latitude()).isEqualTo(latitude3);
            softAssertions.assertThat(parkingResponse.longitude()).isEqualTo(longitude3);
            softAssertions.assertThat(parkingResponse.address()).isEqualTo(address3);
        });
    }
}
