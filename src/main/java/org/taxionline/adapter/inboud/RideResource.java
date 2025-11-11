package org.taxionline.adapter.inboud;

import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.taxionline.config.di.BeanInjection;
import org.taxionline.core.business.ride.RideBusiness;
import org.taxionline.core.domain.ride.CreateRideDTO;

import java.util.Collections;

public class RideResource {

    private static final Logger logger = LoggerFactory.getLogger(RideResource.class);

    @BeanInjection
    RideBusiness business;

    public void requestRide(Context ctx) {
        var dto = ctx.bodyValidator(CreateRideDTO.class).get();
        logger.info("Creating ride for: {}", dto);
        var response = business.requestRide(dto);
        ctx.json(Collections.singletonMap("identifier", response));
    }

    public void getRideByIdentifier(Context ctx) {
        var identifier = ctx.pathParam("identifier");
        logger.info("Fetching ride by identifier: {}", identifier);
        var response = business.getRide(identifier);
        ctx.json(response);
    }

    public void acceptRide(Context ctx) {
        var rideIdentifier = ctx.pathParam("ride-identifier");
        var driverIdentifier = ctx.pathParam("driver-identifier");
        logger.info("Driver {} accepting ride {}", rideIdentifier, driverIdentifier);
        business.acceptRide(rideIdentifier, driverIdentifier);
    }

    public void startRide(Context ctx) {
        var identifier = ctx.pathParam("identifier");
        logger.info("Starting ride {}", identifier);
        business.startRide(identifier);
    }

}
