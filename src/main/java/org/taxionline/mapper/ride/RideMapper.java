package org.taxionline.mapper.ride;

import org.taxionline.infra.di.BeanInjection;
import org.taxionline.domain.entity.account.Account;
import org.taxionline.domain.entity.position.Position;
import org.taxionline.dto.ride.CreateRideDTO;
import org.taxionline.domain.entity.ride.Ride;
import org.taxionline.dto.ride.RideDTO;
import org.taxionline.domain.entity.ride.RideStatus;
import org.taxionline.mapper.BaseMapper;
import org.taxionline.mapper.account.AccountMapper;
import org.taxionline.util.DateUtils;

import java.util.List;
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

    public RideDTO toDTO(Ride ride, List<Position> positions) {
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
                        .distance(null == t.getDistance() ? t.calculateDistance(positions) : t.getDistance())
                        .positions(positions)
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
