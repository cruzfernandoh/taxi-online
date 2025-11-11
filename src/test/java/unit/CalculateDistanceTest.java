package unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.taxionline.core.domain.ride.Coordinate;
import org.taxionline.util.CalculateDistanceUtils;

public class CalculateDistanceTest {

    @Test
    void calculateDistance() {
        Coordinate from = new Coordinate(-18.93906840331504, -48.30752306347402);
        Coordinate to = new Coordinate(-18.952004905817855, -48.3535568383149);
        var distance = CalculateDistanceUtils.calculate(from, to);
        Assertions.assertEquals(5, distance);
    }

    @Test
    void calculateDistance2() {
        Coordinate from = new Coordinate(-18.93906840331504, -48.30752306347402);
        Coordinate to = new Coordinate(-18.952004905817855, -48.3535568383149);
        var distance = CalculateDistanceUtils.calculate(from, to);
        distance += CalculateDistanceUtils.calculate(to, from);
        Assertions.assertEquals(10, distance);
    }
}
