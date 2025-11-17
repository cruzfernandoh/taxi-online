package org.taxionline.domain.service.ride.impl;

import org.taxionline.domain.service.ride.FareCalculator;

public class NormalFareCalculator implements FareCalculator {

    @Override
    public double calculate(double distance) {
        return distance * 2.1;
    }
}
