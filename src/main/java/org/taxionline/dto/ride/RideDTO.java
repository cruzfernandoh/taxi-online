package org.taxionline.dto.ride;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.taxionline.domain.entity.ride.RideStatus;
import org.taxionline.dto.account.AccountDTO;
import org.taxionline.domain.entity.position.Position;

import java.util.Collection;

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

    Collection<Position> positions;

    @Builder
    public RideDTO(String identifier, AccountDTO passenger, AccountDTO driver, RideStatus status, Double fare,
                   Double distance, Double fromLat, Double fromLon, Double toLat, Double toLon, String date, Collection<Position> positions) {
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
        this.positions = positions;
    }
}
