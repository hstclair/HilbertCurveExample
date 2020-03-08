package com.halstclair.hilbertsample;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class HilbertCurveImplTest {

    @Test
    public void tryDistanceToCoords() {

        long[] expectedCoords = { 8, 7, 6 };

        HilbertCurve h = new HilbertCurveImpl(3);

        long distance = 933;

        long[] actualCoords = h.distanceToCoords(distance);

        assertArrayEquals(expectedCoords, actualCoords);
    }

    @Test
    public void tryCoordsToDistance() {

        long expectedDistance = 933;

        long[] coords = { 8, 7, 6 };

        HilbertCurve h = new HilbertCurveImpl(3);

        long actualDistance = h.coordsToDistance(coords);

        assertEquals(expectedDistance, actualDistance);
    }

    @Test
    public void sweepTestDistanceToCoordsAndBack() {

        HilbertCurve h = new HilbertCurveImpl(3);

        for (long expectedDistance = 0; expectedDistance < 3000000; expectedDistance += 333) {

            long[] coords = h.distanceToCoords(expectedDistance);

            long actualDistance = h.coordsToDistance(coords);

            assertEquals(expectedDistance, actualDistance);
        }
    }
}
