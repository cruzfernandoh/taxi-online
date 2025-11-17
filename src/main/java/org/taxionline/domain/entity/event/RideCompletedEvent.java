package org.taxionline.domain.entity.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RideCompletedEvent {

    public static final String eventName = "rideCompleted";

    private String rideIdentifier;
    private Double amount;

}
