package org.taxionline.domain.business.payment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.taxionline.domain.entity.event.RideCompletedEvent;

public class ProcessPaymentBusiness {

    private final static Logger logger = LoggerFactory.getLogger(ProcessPaymentBusiness.class);

    public void processPayment(RideCompletedEvent input) {
        logger.info("Processing payment to ride [{}], using {} credit card, payment value: {}",
                input.getRideIdentifier(), "teste", input.getAmount());
    }
}
