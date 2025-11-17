package org.taxionline.dto.ride;

public record CreateRideDTO(String passengerIdentifier, Double fromLat, Double toLat, Double fromLon, Double toLon) {
}
