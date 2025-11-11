package org.taxionline.core.business.ride;

import jakarta.validation.ValidationException;
import org.taxionline.config.di.BeanInjection;
import org.taxionline.config.exception.ResourceNotFoundException;
import org.taxionline.core.domain.ride.CreateRideDTO;
import org.taxionline.core.domain.ride.Ride;
import org.taxionline.core.domain.ride.RideDTO;
import org.taxionline.core.domain.ride.RideStatus;
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
        if (!passenger.isPassenger()) throw new ValidationException("User is not passenger");
        var ride = mapper.buildRideFromCreateDTO(passenger, dto);
        repository.save(ride);
        return ride.getIdentifier();
    }

    public RideDTO getRide(String identifier) {
        var ride = findRideByIdentifier(identifier);
        return mapper.toDTO(ride);
    }

    public void acceptRide(String rideIdentifier, String driverIdentifier) {
        var driver = accountRepository.findByIdentifier(driverIdentifier)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Driver with id [%s] not found", driverIdentifier)));
        if (!driver.isDriver()) throw new ValidationException("User is not a driver");
        var activeRide = repository.findActiveRideByDriver(driver);
        if (activeRide.isPresent()) throw new ValidationException("Driver has an active ride");
        var ride = findRideByIdentifier(rideIdentifier);
        ride.accept(driver);
        repository.update(ride);
    }

    public void startRide(String identifier) {
        var ride = findRideByIdentifier(identifier);
        ride.start();
        repository.update(ride);
    }

    public void updatePosition(String identifier, Double lat, Double lon) {
        var ride = findRideByIdentifier(identifier);
        if (!RideStatus.IN_PROGRESS.equals(ride.getStatus())) throw new ValidationException("Ride is not in progress");
    }

    private Ride findRideByIdentifier(String identifier) {
        return repository.getRide(identifier)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Ride with id [%s] not found", identifier)));
    }
}
