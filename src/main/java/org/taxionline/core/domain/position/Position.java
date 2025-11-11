package org.taxionline.core.domain.position;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.taxionline.core.domain.base.IdModelBase;
import org.taxionline.core.domain.ride.Ride;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Position extends IdModelBase {

    @ManyToOne
    @JoinColumn(nullable = false)
    Ride ride;

    Double lat;

    Double lon;

    LocalDateTime date;

    @Builder
    public Position(Ride ride, Double lat, Double lon, LocalDateTime date) {
        this.ride = ride;
        this.lat = lat;
        this.lon = lon;
        this.date = date;
    }
}
