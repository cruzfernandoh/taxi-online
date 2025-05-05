package org.taxionline.model.ride;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.taxionline.model.account.Account;
import org.taxionline.model.base.IdModelBase;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Ride extends IdModelBase {

    @ManyToOne
    @JoinColumn( nullable = false )
    Account passenger;

    @ManyToOne
    @JoinColumn( nullable = false )
    Account driver;

    String status;

    Double fare;

    Double distance;

    Double fromLat;

    Double fromLon;

    Double toLat;

    Double toLong;

    LocalDateTime date;

    @Builder
    public Ride( Account passenger, Account driver, String status, Double fare, Double distance, Double fromLat, Double fromLon, Double toLat, Double toLong, LocalDateTime date ) {
        this.passenger = passenger;
        this.driver = driver;
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
