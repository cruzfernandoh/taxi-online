package org.taxionline.model;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.taxionline.model.base.IdModelBase;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Ride extends IdModelBase {

    String passengerId;

    String driverId;

    String status;

    Double fare;

    Double distance;

    Double fromLat;

    Double fromLon;

    Double toLat;

    Double toLong;

    LocalDateTime date;

    @Builder
    public Ride( String passengerId, String driverId, String status, Double fare, Double distance, Double fromLat, Double fromLon, Double toLat, Double toLong, LocalDateTime date ) {
        this.passengerId = passengerId;
        this.driverId = driverId;
        this.status = status;
        this.fare = fare;
        this.distance = distance;
        this.fromLat = fromLat;
        this.fromLon = fromLon;
        this.toLat = toLat;
        this.toLong = toLong;
        this.date = date;
    }
}
