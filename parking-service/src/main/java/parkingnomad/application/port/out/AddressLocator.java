package parkingnomad.application.port.out;

public interface AddressLocator {
    String convertToAddress(final double latitude, final double longitude);
}
