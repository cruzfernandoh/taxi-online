package org.taxionline.core.business.ride;

import jakarta.validation.ValidationException;
import org.taxionline.config.di.BeanInjection;
import org.taxionline.config.exception.ResourceNotFoundException;
import org.taxionline.core.domain.ride.CreateRideDTO;
import org.taxionline.core.domain.ride.RideDTO;
import org.taxionline.mapper.ride.RideMapper;
import org.taxionline.port.outbound.account.AccountRepository;
import org.taxionline.port.outbound.ride.RideRepository;

public class RideBusiness {

    @BeanInjection
    RideRepository repository;

    @BeanInjection
    AccountRepository accountRepository;

    @BeanInjection
    RideMapper mapper;

    public String requestRide(CreateRideDTO dto) {
        var passenger = accountRepository.findByIdentifier(dto.passengerIdentifier())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Passenger with id [%s] not found", dto.passengerIdentifier())));
        if (passenger.isDriver()) throw new ValidationException("User is not passenger");
        var ride = mapper.buildRideFromCreateDTO(passenger, dto);
        repository.save(ride);
        return ride.getIdentifier();
    }

    public RideDTO getRide(String identifier) {
        var ride = repository.getRide(identifier)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Ride with id [%s] not found", identifier)));
        return mapper.toDTO(ride);
    }
}
