package org.taxionline.core.business.position;

import org.taxionline.config.di.BeanInjection;
import org.taxionline.port.outbound.ride.RideRepository;

public class PositionBusiness {

    @BeanInjection
    RideRepository repository;
}
