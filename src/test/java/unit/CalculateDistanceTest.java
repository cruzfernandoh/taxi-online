package unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.taxionline.domain.entity.ride.Coordinate;
import org.taxionline.domain.service.position.CalculateDistance;

public class CalculateDistanceTest {

    @Test
    void calculateDistance() {
        Coordinate from = new Coordinate(-18.93906840331504, -48.30752306347402);
        Coordinate to = new Coordinate(-18.952004905817855, -48.3535568383149);
        var distance = CalculateDistance.calculate(from, to);
        Assertions.assertEquals(5, distance);
    }

    @Test
    void calculateDistance2() {
        Coordinate from = new Coordinate(-18.93906840331504, -48.30752306347402);
        Coordinate to = new Coordinate(-18.952004905817855, -48.3535568383149);
        var distance = CalculateDistance.calculate(from, to);
        distance += CalculateDistance.calculate(to, from);
        Assertions.assertEquals(10, distance);
    }
}
