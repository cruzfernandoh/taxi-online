package org.taxionline.domain.service.position;

import org.taxionline.domain.entity.ride.Coordinate;

public class CalculateDistance {

    public static long calculate(Coordinate from, Coordinate to) {
        var earthRadius = 6371;
        var degreesToRadius = Math.PI / 180;
        var deltaLat = (to.getLat() - from.getLat()) * degreesToRadius;
        var deltaLon = (to.getLon() - from.getLon()) * degreesToRadius;
        var a = Math.sin(deltaLat / 2) *
                Math.sin(deltaLat / 2) +
                Math.cos(from.getLat() * degreesToRadius) *
                Math.cos(to.getLat() * degreesToRadius) *
                Math.sin(deltaLon / 2) *
                Math.sin(deltaLon / 2);
        var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        var distance = earthRadius * c;
        return Math.round(distance);
    }
}
