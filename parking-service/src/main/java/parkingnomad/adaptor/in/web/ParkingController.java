package parkingnomad.adaptor.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import parkingnomad.application.port.in.FindLatestParkingByMemberIdUseCase;
import parkingnomad.application.port.in.FindParkingByIdAndMemberIdUseCase;
import parkingnomad.application.port.in.SaveParkingUseCase;
import parkingnomad.application.port.in.dto.ParkingResponse;
import parkingnomad.application.port.in.dto.SaveParkingRequest;
import parkingnomad.resolver.auth.AuthMember;

import java.net.URI;

@RestController
@RequestMapping("/api/parkings")
public class ParkingController {
    private final SaveParkingUseCase saveParkingUseCase;
    private final FindParkingByIdAndMemberIdUseCase findParkingByIdAndMemberIdUseCase;
    private final FindLatestParkingByMemberIdUseCase findLatestParkingByMemberIdUseCase;

    public ParkingController(
            final SaveParkingUseCase saveParkingUseCase,
            final FindParkingByIdAndMemberIdUseCase findParkingByIdAndMemberIdUseCase,
            final FindLatestParkingByMemberIdUseCase findLatestParkingByMemberIdUseCase
    ) {
        this.saveParkingUseCase = saveParkingUseCase;
        this.findParkingByIdAndMemberIdUseCase = findParkingByIdAndMemberIdUseCase;
        this.findLatestParkingByMemberIdUseCase = findLatestParkingByMemberIdUseCase;
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
    public ResponseEntity<ParkingResponse> findParkingById(
            @PathVariable final Long id,
            @AuthMember final Long memberId
    ) {
        final ParkingResponse response = findParkingByIdAndMemberIdUseCase.findParkingByIdAndMemberId(id, memberId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/latest")
    public ResponseEntity<ParkingResponse> findLatestParking(@AuthMember final Long memberId) {
        final ParkingResponse response = findLatestParkingByMemberIdUseCase.findLatestParkingByMemberId(memberId);
        return ResponseEntity.ok(response);
    }
}
