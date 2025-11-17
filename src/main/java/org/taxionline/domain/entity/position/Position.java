package org.taxionline.domain.entity.position;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.taxionline.domain.entity.base.IdModelBase;
import org.taxionline.domain.entity.ride.Coordinate;
import org.taxionline.domain.entity.ride.Ride;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Position extends IdModelBase {

    @ManyToOne
    @JoinColumn(nullable = false)
    Ride ride;

    @Embedded
    Coordinate coordinate;

    LocalDateTime date;

    @Builder
    public Position(Ride ride, Coordinate coordinate, LocalDateTime date) {
        this.ride = ride;
        this.coordinate = coordinate;
        this.date = null == date ? LocalDateTime.now() : date;
    }
}
