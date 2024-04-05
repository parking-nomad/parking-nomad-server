package parkingnomad.adaptor.out.persistence;

import jakarta.persistence.*;
import org.locationtech.jts.geom.Polygon;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity(name = "favorite_areas")
@EntityListeners(AuditingEntityListener.class)
public class JpaFavoriteAreaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    private String name;

    private Polygon area;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    protected JpaFavoriteAreaEntity() {
    }

    public JpaFavoriteAreaEntity(final Long id, final Long memberId, final String name, final Polygon area, final LocalDateTime createdAt, final LocalDateTime updatedAt) {
        this.id = id;
        this.memberId = memberId;
        this.name = name;
        this.area = area;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public JpaFavoriteAreaEntity(final Long memberId, final String name, final Polygon area) {
        this(null, memberId, name, area, null, null);
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public Polygon getArea() {
        return area;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
