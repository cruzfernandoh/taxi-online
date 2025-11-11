package org.taxionline.core.domain.ride;

import jakarta.persistence.*;
import jakarta.validation.ValidationException;
import lombok.*;
import org.taxionline.core.domain.account.Account;
import org.taxionline.core.domain.base.IdModelBase;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Ride extends IdModelBase {

    @ManyToOne
    @JoinColumn(nullable = false)
    Account passenger;

    @ManyToOne
    Account driver;

    RideStatus status;

    Double fare;

    Double distance;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "lat", column = @Column(name = "from_lat")),
            @AttributeOverride(name = "lon", column = @Column(name = "from_lon"))
    })
    private Coordinate from;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "lat", column = @Column(name = "to_lat")),
            @AttributeOverride(name = "lon", column = @Column(name = "to_lon"))
    })
    private Coordinate to;

    LocalDateTime date;

    public void accept(Account driver) {
        if (!RideStatus.REQUESTED.equals(this.getStatus()))
            throw new ValidationException("Ride is not valid to accept");
        this.setDriver(driver);
        this.setStatus(RideStatus.ACCEPTED);
    }

    public void start() {
        if (!RideStatus.ACCEPTED.equals(this.getStatus())) throw new ValidationException("Ride is not ready to start");
        this.setStatus(RideStatus.IN_PROGRESS);
    }

    @Builder
    public Ride(Account passenger, Double fromLat, Double fromLon, Double toLat, Double toLon, RideStatus status) {
        this.passenger = passenger;
        this.to = new Coordinate(toLon, toLat);
        this.from = new Coordinate(fromLon, fromLat);
        this.date = LocalDateTime.now();
        this.status = status;
    }
}
