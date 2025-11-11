package org.taxionline.adapter.outbound.position;

import org.taxionline.config.repository.BaseRepository;
import org.taxionline.core.domain.position.Position;
import org.taxionline.core.domain.ride.Ride;
import org.taxionline.port.outbound.position.PositionRepository;

import java.util.List;

public class PositionRepositoryAdapter extends BaseRepository<Position> implements PositionRepository {

    @Override
    public void save(Position position) {
        persist(position);
    }

    @Override
    public void update(Position position) {
        merge(position);
    }

    @Override
    public List<Position> getPositionByRide(Ride ride) {
        return find("ride = ?1", ride).list();
    }
}
