package org.taxionline.core.business.position;

import org.taxionline.config.di.BeanInjection;
import org.taxionline.core.domain.position.Position;
import org.taxionline.core.domain.position.UpdatePositionDTO;
import org.taxionline.core.domain.ride.Coordinate;
import org.taxionline.port.outbound.position.PositionRepository;
import org.taxionline.port.outbound.ride.RideRepository;

public class PositionBusiness {

    @BeanInjection
    PositionRepository repository;

    @BeanInjection
    RideRepository rideRepository;

    public void updatePosition(UpdatePositionDTO dto) {
        var ride = rideRepository.getRide(dto.rideIdentifier()).orElseThrow();
        var position = Position.builder().ride(ride).coordinate(new Coordinate(dto.lon(), dto.lat())).build();
        repository.save(position);
    }
}
