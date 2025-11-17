package org.taxionline.domain.entity.ride;

import jakarta.persistence.*;
import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.taxionline.domain.entity.account.Account;
import org.taxionline.domain.entity.base.IdModelBase;
import org.taxionline.domain.entity.position.Position;
import org.taxionline.domain.factory.fare.FareCalculatorFactory;
import org.taxionline.domain.service.position.CalculateDistance;

import java.time.LocalDateTime;
import java.util.List;

@Getter
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
        this.driver = driver;
        this.status = RideStatus.ACCEPTED;
    }

    public void start() {
        if (!RideStatus.ACCEPTED.equals(this.getStatus())) throw new ValidationException("Ride is not ready to start");
        this.status = RideStatus.IN_PROGRESS;
    }

    public void finish(List<Position> positions) {
        if (!RideStatus.IN_PROGRESS.equals(this.getStatus()))
            throw new ValidationException("Ride is not in valid status to finish");
        calculateDistanceAndFare(positions);
        this.status = RideStatus.COMPLETED;
    }

    public void calculateDistanceAndFare(List<Position> positions) {
        var distanceTotal = 0.0;
        var fare = 0.0;
        for (int i = 0; i < positions.size() - 1; i++) {
            var pos = positions.get(i);
            var next = positions.get(i + 1);
            double distance = CalculateDistance.calculate(pos.getCoordinate(), next.getCoordinate());
            distanceTotal += distance;
            fare += FareCalculatorFactory.create(pos.getDate()).calculate(distance);
        }
        this.distance = distanceTotal;
        this.fare = fare;
    }

    public double calculateDistance(List<Position> positions) {
        var distance = 0.0;
        for (int i = 0; i < positions.size() - 1; i++) {
            var pos = positions.get(i);
            var next = positions.get(i + 1);
            distance += CalculateDistance.calculate(pos.getCoordinate(), next.getCoordinate());
        }
        return distance;
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
