package org.taxionline.domain.entity.ride;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class Coordinate {

    private Double lon;

    private Double lat;

    @Builder
    public Coordinate(Double lon, Double lat) {
        this.lon = lon;
        this.lat = lat;
    }
}
