package com.halstclair.hilbertsample;

/**
 * functions for converting to / from Gray code
 */
public class GrayCode {

    /**
     * encode a 2's complement binary value to the equivalent Gray code
     * @param value the value to be encoded
     * @return the corresponding Gray code value
     */
    public long encode(long value) {

        return value ^ (value >> 1);
    }

    /**
     * Decode a Gray code value
     * @param value the Gray code value to decode
     * @return the corresponding 2's complement binary value
     */
    public long decode(long value) {

        if (value == 0 || value == 1)
            return value;

        long mask = Long.highestOneBit(value);

        long bit = mask >> 1;

        long result = value;

        while (mask != 0) {

            result ^= bit;

            mask >>= 1;

            bit = (result & mask) >> 1;

        }

        return result;
    }
}
