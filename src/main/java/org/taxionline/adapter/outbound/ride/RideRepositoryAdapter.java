package org.taxionline.adapter.outbound.ride;

import org.taxionline.infra.repository.BaseRepository;
import org.taxionline.domain.entity.account.Account;
import org.taxionline.domain.entity.ride.Ride;
import org.taxionline.domain.entity.ride.RideStatus;
import org.taxionline.port.outbound.ride.RideRepository;

import java.util.List;
import java.util.Optional;

public class RideRepositoryAdapter extends BaseRepository<Ride> implements RideRepository {

    @Override
    public void save(Ride ride) {
        persist(ride);
    }

    @Override
    public void update(Ride ride) {
        merge(ride);
    }

    @Override
    public Optional<Ride> getRide(String identifier) {
        return find("identifier = ?1", identifier).singleResultOptional();
    }

    @Override
    public Optional<Ride> findActiveRideByDriver(Account driver) {
        return find("driver = ?1 and status in ?2", driver, List.of(RideStatus.ACCEPTED, RideStatus.IN_PROGRESS)).singleResultOptional();
    }
}
