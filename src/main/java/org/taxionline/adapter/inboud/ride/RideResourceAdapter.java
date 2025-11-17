package org.taxionline.adapter.inboud.ride;

import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.taxionline.domain.business.ride.RideBusiness;
import org.taxionline.dto.ride.CreateRideDTO;
import org.taxionline.infra.di.BeanInjection;
import org.taxionline.port.inboud.ride.RideResource;

import java.util.Collections;

public class RideResourceAdapter implements RideResource {

    private static final Logger logger = LoggerFactory.getLogger(RideResourceAdapter.class);

    @BeanInjection
    RideBusiness business;

    @Override
    public void requestRide(Context ctx) {
        var dto = ctx.bodyValidator(CreateRideDTO.class).get();
        logger.info("Creating ride for: {}", dto);
        var response = business.requestRide(dto);
        ctx.json(Collections.singletonMap("identifier", response));
    }

    @Override
    public void getRideByIdentifier(Context ctx) {
        var identifier = ctx.pathParam("identifier");
        logger.info("Fetching ride by identifier: {}", identifier);
        var response = business.getRide(identifier);
        ctx.json(response);
    }

    @Override
    public void acceptRide(Context ctx) {
        var rideIdentifier = ctx.pathParam("ride-identifier");
        var driverIdentifier = ctx.pathParam("driver-identifier");
        logger.info("Driver {} accepting ride {}", rideIdentifier, driverIdentifier);
        business.acceptRide(rideIdentifier, driverIdentifier);
    }

    @Override
    public void startRide(Context ctx) {
        var identifier = ctx.pathParam("identifier");
        logger.info("Starting ride {}", identifier);
        business.startRide(identifier);
    }

}
