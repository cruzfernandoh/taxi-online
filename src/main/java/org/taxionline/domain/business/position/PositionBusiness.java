package org.taxionline.domain.business.position;

import org.taxionline.infra.di.BeanInjection;
import org.taxionline.domain.entity.position.Position;
import org.taxionline.dto.position.UpdatePositionDTO;
import org.taxionline.domain.entity.ride.Coordinate;
import org.taxionline.port.outbound.position.PositionRepository;
import org.taxionline.port.outbound.ride.RideRepository;

public class PositionBusiness {

    @BeanInjection
    PositionRepository repository;

    @BeanInjection
    RideRepository rideRepository;

    public void updatePosition(UpdatePositionDTO dto) {
        var ride = rideRepository.getRide(dto.rideIdentifier()).orElseThrow();
        var position = Position
                .builder()
                .ride(ride)
                .coordinate(new Coordinate(dto.lon(), dto.lat()))
                .date(dto.date())
                .build();
        repository.save(position);
    }
}
