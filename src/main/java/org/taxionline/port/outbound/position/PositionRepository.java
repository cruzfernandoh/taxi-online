package org.taxionline.port.outbound.position;

import org.taxionline.domain.entity.position.Position;
import org.taxionline.domain.entity.ride.Ride;

import java.util.List;

public interface PositionRepository {

    void save(Position position);

    void update(Position position);

    List<Position> getPositionByRide(Ride ride);
}
