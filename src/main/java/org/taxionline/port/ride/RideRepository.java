package org.taxionline.port.ride;

import org.taxionline.core.domain.ride.Ride;

import java.util.Optional;

public interface RideRepository {

    void save(Ride ride);

    Optional<Ride> getRide(String identifier);
}
