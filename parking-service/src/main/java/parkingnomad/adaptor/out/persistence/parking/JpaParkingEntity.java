package parkingnomad.adaptor.out.persistence.parking;

import jakarta.persistence.*;
import org.locationtech.jts.geom.Point;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity(name = "parkings")
@EntityListeners(AuditingEntityListener.class)
public class JpaParkingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    private Point coordinate;

    private String address;

    private String image;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    protected JpaParkingEntity() {
    }

    public JpaParkingEntity(final Long memberId, final Point coordinate, final String address, final String image) {
        this.memberId = memberId;
        this.coordinate = coordinate;
        this.address = address;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Point getCoordinate() {
        return coordinate;
    }

    public String getAddress() {
        return address;
    }

    public String getImage() {
        return image;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
