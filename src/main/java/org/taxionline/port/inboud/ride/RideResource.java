package org.taxionline.port.inboud.ride;

import io.javalin.http.Context;

public interface RideResource {

    void requestRide(Context ctx);

    void getRideByIdentifier(Context ctx);

    void acceptRide(Context ctx);

    void startRide(Context ctx);
}
