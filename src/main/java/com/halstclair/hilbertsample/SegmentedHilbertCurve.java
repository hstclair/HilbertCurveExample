package com.halstclair.hilbertsample;

/**
 * Implementation of the HilbertCurve interface generated from
 * the output of multiple instances of the HilbertCurveSegment class
 */
public class SegmentedHilbertCurve implements HilbertCurve {

    /** the minimum number of dimensions in which it is mathematically possible to construct a Hilbert curve */
    private static final int MinDimensionValue = 2;

    /** the maximum number of dimensions in which there exists enough precision to construct anything more than a first-order Hilbert curve */
    private static final int MaxDimensionValue = 32;

    /** the dimension of this Hilbert curve */
    private final int dimension;

    /**
     * the order of the Hilbert curve
     * (the first-order curve is simply the set of
     * coordinates directly derived by mapping the
     * distance to a Gray code value and then mapping
     * the bits of the Gray code value to the array
     * of coordinates so that every coordinate value
     * is either 0 or 1)
     **/
    private final int order;

    /**
     * the initial first order Hilbert curve on which the
     * entire output will be based.
     * We initialize this to the non-rotated, non-reflected
     * first-order Hilbert curve starting at (0, ... , 0)
     * and ending at (1, ... , 0)
     */
    private final HilbertCurveSegment initialSegment;

    /**
     * constructor
     * @param dimension the number of dimensions to use when constructing
     *                  this Hilbert curve
     *                  (this value must be in the range 2 to 32)
     */
    public SegmentedHilbertCurve(int dimension) {

        if (dimension < MinDimensionValue)
            throw new IllegalArgumentException(String.format("Parameter 'dimension' has value %s, which is below its minimum allowable value of %s", dimension, MinDimensionValue));

        if (dimension > MaxDimensionValue)
            throw new IllegalArgumentException(String.format("Parameter 'dimension' has value %s, which is above its maximum allowable value of %s", dimension, MaxDimensionValue));

        this.dimension = dimension;

        this.order = ((Long.SIZE + dimension - 1) / dimension);

        initialSegment = new HilbertCurveSegment(dimension);
    }

    @Override
    public long[] distanceToCoords(long distance) {

        long[] coords = new long[dimension];

        long mask = (1L << dimension) - 1;

        HilbertCurveSegment segment = this.initialSegment;

        for (int currentDegree = 0; currentDegree < order; currentDegree++) {

            long segmentDistance = (distance >>> (dimension * ((order - 1) - currentDegree))) & mask;

            long vector = segment.apply(segmentDistance);

            segment = segment.getInnerFrame(segmentDistance);

            for (int index = 0; index < coords.length; index++) {
                coords[index] = (coords[index] << 1) | (vector & 1);
                vector >>= 1;
            }
        }

        return coords;
    }

    @Override
    public long coordsToDistance(long[] coords) {

        long mask = (1L << coords.length) - 1;

        long distance = 0;

        HilbertCurveSegment segment = this.initialSegment;

        for (int currentDegree = 0; currentDegree < order; currentDegree++) {

            distance <<= coords.length;

            distance |= segment.getCoordinatesVector(coords, ((order - 1) - currentDegree));

            segment = segment.getInnerFrame(distance & mask);
        }

        return distance;
    }
}
