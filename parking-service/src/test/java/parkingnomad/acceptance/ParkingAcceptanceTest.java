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

    @Test
    @DisplayName("parkingId를 통해 저장된 parking을 조회한다.")
    void findParkingById() {
        //given
        final String address = "address2";
        final long memberId = 2L;
        final int latitude = 40;
        final int longitude = 30;
        final ExtractableResponse<Response> savedParking = saveParking(address, memberId, latitude, longitude);
        final String[] locations = savedParking.header("Location").split("/");
        final String savedId = locations[locations.length - 1];

        //when
        final ExtractableResponse<Response> response = given().log().all()
                .contentType(ContentType.JSON)
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
}
