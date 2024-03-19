package parkingnomad.adaptor.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import parkingnomad.application.port.in.FindParkingByIdUseCase;
import parkingnomad.application.port.in.SaveParkingUseCase;
import parkingnomad.application.port.in.dto.ParkingResponse;
import parkingnomad.application.port.in.dto.SaveParkingRequest;
import parkingnomad.resolver.auth.AuthMember;

import java.net.URI;

@RestController
@RequestMapping("/api/parkings")
public class ParkingController {
    private final SaveParkingUseCase saveParkingUseCase;
    private final FindParkingByIdUseCase findParkingByIdUseCase;

    public ParkingController(
            final SaveParkingUseCase saveParkingUseCase,
            final FindParkingByIdUseCase findParkingByIdUseCase
    ) {
        this.saveParkingUseCase = saveParkingUseCase;
        this.findParkingByIdUseCase = findParkingByIdUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> saveParking(
            @AuthMember final Long memberId,
            @RequestBody final SaveParkingRequest saveParkingRequest
    ) {
        final Long savedId = saveParkingUseCase.saveParking(memberId, saveParkingRequest);
        return ResponseEntity.created(URI.create("/api/parkings/" + savedId)).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ParkingResponse> findParking(@PathVariable final Long id) {
        final ParkingResponse response = findParkingByIdUseCase.findParkingById(id);
        return ResponseEntity.ok(response);
    }
}
