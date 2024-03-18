package parkingnomad.application.port.out;

import java.util.Optional;

public interface AddressLocator {
    Optional<String> convertToAddress(final double latitude, final double longitude);
}
