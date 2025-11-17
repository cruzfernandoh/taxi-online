package org.taxionline.dto.position;

import java.time.LocalDateTime;

public record UpdatePositionDTO(String rideIdentifier, Double lat, Double lon, LocalDateTime date) {

    public UpdatePositionDTO(String rideIdentifier, Double lat, Double lon) {
        this(rideIdentifier, lat, lon, LocalDateTime.now());
    }
}
