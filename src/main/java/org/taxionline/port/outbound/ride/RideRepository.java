package org.taxionline.port.outbound.ride;

import org.taxionline.domain.entity.account.Account;
import org.taxionline.domain.entity.ride.Ride;

import java.util.Optional;

public interface RideRepository {

    void save(Ride ride);

    void update(Ride ride);

    Optional<Ride> getRide(String identifier);

    Optional<Ride> findActiveRideByDriver(Account driver);
}
