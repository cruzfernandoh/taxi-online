package org.taxionline.port.outbound.position;

import org.taxionline.core.domain.position.Position;
import org.taxionline.core.domain.ride.Ride;

import java.util.List;

public interface PositionRepository {

    void save(Position position);

    void update(Position position);

    List<Position> getPositionByRide(Ride ride);
}
