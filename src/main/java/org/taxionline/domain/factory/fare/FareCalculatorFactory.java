package org.taxionline.domain.factory.fare;

import org.taxionline.domain.service.ride.FareCalculator;
import org.taxionline.domain.service.ride.impl.NormalFareCalculator;
import org.taxionline.domain.service.ride.impl.OvernightFareCalculator;
import org.taxionline.domain.service.ride.impl.SpecialDayFareCalculator;

import java.time.LocalDateTime;

public class FareCalculatorFactory {

    public static FareCalculator create(LocalDateTime date) {
        if (date.getDayOfMonth() == 1) return new SpecialDayFareCalculator();
        if (date.getHour() > 22 || date.getHour() < 6) return new OvernightFareCalculator();
        return new NormalFareCalculator();
    }
}
