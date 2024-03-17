package parkingnomad.adaptor.out.addresslocator;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record KakaoAddressResponse(@JsonProperty("documents") List<Documents> documents) {

    public String getAddressName() {
        return documents.get(0).roadAddress.addressName;
    }

    private record Documents(@JsonProperty("road_address") RoadAddress roadAddress) {

    }

    private record RoadAddress(@JsonProperty("address_name") String addressName) {
    }
}
