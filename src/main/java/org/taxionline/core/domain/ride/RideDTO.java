package org.taxionline.core.domain.ride;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.taxionline.core.domain.account.AccountDTO;

@Getter
@Setter
@NoArgsConstructor
public class RideDTO {

    String identifier;

    AccountDTO passenger;

    AccountDTO driver;

    RideStatus status;

    Double fare;

    Double distance;

    Double fromLat;

    Double fromLon;

    Double toLat;

    Double toLon;

    String date;

    @Builder
    public RideDTO(String identifier, AccountDTO passenger, AccountDTO driver, RideStatus status, Double fare, Double distance, Double fromLat, Double fromLon, Double toLat, Double toLon, String date) {
        this.identifier = identifier;
        this.passenger = passenger;
        this.driver = driver;
        this.status = status;
        this.fare = fare;
        this.distance = distance;
        this.fromLat = fromLat;
        this.fromLon = fromLon;
        this.toLat = toLat;
        this.toLon = toLon;
        this.date = date;
    }
}
