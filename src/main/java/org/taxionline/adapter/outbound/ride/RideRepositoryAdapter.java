package org.taxionline.adapter.outbound.ride;

import org.taxionline.config.repository.BaseRepository;
import org.taxionline.core.domain.ride.Ride;
import org.taxionline.port.outbound.ride.RideRepository;

import java.util.Optional;

public class RideRepositoryAdapter extends BaseRepository<Ride> implements RideRepository {

    @Override
    public void save(Ride ride) {
        persist(ride);
    }

    @Override
    public Optional<Ride> getRide(String identifier) {
        return find("identifier = ?1", identifier).singleResultOptional();
    }
}
