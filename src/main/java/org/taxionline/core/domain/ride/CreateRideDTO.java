package org.taxionline.core.domain.ride;

public record CreateRideDTO(String passengerIdentifier, Double fromLat, Double toLat, Double fromLon, Double toLon) {
}
