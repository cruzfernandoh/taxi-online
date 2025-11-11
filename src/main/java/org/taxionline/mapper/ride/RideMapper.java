package org.taxionline.mapper.ride;

import org.taxionline.config.di.BeanInjection;
import org.taxionline.core.domain.account.Account;
import org.taxionline.core.domain.ride.CreateRideDTO;
import org.taxionline.core.domain.ride.Ride;
import org.taxionline.core.domain.ride.RideDTO;
import org.taxionline.core.domain.ride.RideStatus;
import org.taxionline.mapper.BaseMapper;
import org.taxionline.mapper.account.AccountMapper;
import org.taxionline.util.DateUtils;

import java.util.Optional;

public class RideMapper implements BaseMapper<Ride, RideDTO> {

    @BeanInjection
    AccountMapper accountMapper;

    @Override
    public Ride toEntity(RideDTO rideDTO) {
        return null;
    }

    @Override
    public RideDTO toDTO(Ride ride) {
        return Optional.ofNullable(ride)
                .map(t -> RideDTO.builder()
                        .identifier(t.getIdentifier())
                        .passenger(accountMapper.toDTO(t.getPassenger()))
                        .driver(accountMapper.toDTO(t.getDriver()))
                        .fare(t.getFare())
                        .distance(t.getDistance())
                        .toLat(t.getTo().getLat())
                        .fromLat(t.getFrom().getLat())
                        .toLon(t.getTo().getLon())
                        .fromLon(t.getFrom().getLon())
                        .date(Optional.ofNullable(t.getDate()).map(DateUtils::formatDate).orElse(null))
                        .status(t.getStatus())
                        .distance(t.getDistance())
                        .build())
                .orElse(null);
    }

    public Ride buildRideFromCreateDTO(Account passenger, CreateRideDTO dto) {
        return Ride.builder()
                .passenger(passenger)
                .fromLat(dto.fromLat())
                .toLat(dto.toLat())
                .fromLon(dto.fromLon())
                .toLon(dto.toLon())
                .status(RideStatus.REQUESTED)
                .build();
    }
}
