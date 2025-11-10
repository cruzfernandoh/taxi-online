package org.taxionline.core.domain.ride;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    Double fromLat;

    Double fromLon;

    Double toLat;

    Double toLon;

    LocalDateTime date;

    @Builder
    public Ride(Account passenger, Double fromLat, Double fromLon, Double toLat, Double toLon, RideStatus status) {
        this.passenger = passenger;
        this.fromLat = fromLat;
        this.fromLon = fromLon;
        this.toLat = toLat;
        this.toLon = toLon;
        this.date = LocalDateTime.now();
        this.status = status;
    }
}
