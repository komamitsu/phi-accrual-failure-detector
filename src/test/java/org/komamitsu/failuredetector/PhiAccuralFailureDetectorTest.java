package org.komamitsu.failuredetector;

import org.junit.Test;

import static org.junit.Assert.*;

public class PhiAccuralFailureDetectorTest
{
    @Test
    public void test()
    {
        PhiAccuralFailureDetector failureDetector = new PhiAccuralFailureDetector();
        long now = 1420070400000L;
        for (int i = 0; i < 300; i++) {
            long timestampMillis = now + i * 1000;

            if (i > 290) {
                double phi = failureDetector.phi(timestampMillis);
                if (i == 291) {
                    assertTrue(1 < phi && phi < 3);
                    assertTrue(failureDetector.isAvailable(timestampMillis));
                }
                else if (i == 292) {
                    assertTrue(3 < phi && phi < 8);
                    assertTrue(failureDetector.isAvailable(timestampMillis));
                }
                else if (i == 293) {
                    assertTrue(8 < phi && phi < 16);
                    assertTrue(failureDetector.isAvailable(timestampMillis));
                }
                else if (i == 294) {
                    assertTrue(16 < phi && phi < 30);
                    assertFalse(failureDetector.isAvailable(timestampMillis));
                }
                else if (i == 295) {
                    assertTrue(30 < phi && phi < 50);
                    assertFalse(failureDetector.isAvailable(timestampMillis));
                }
                else if (i == 296) {
                    assertTrue(50 < phi && phi < 70);
                    assertFalse(failureDetector.isAvailable(timestampMillis));
                }
                else if (i == 297) {
                    assertTrue(70 < phi && phi < 100);
                    assertFalse(failureDetector.isAvailable(timestampMillis));
                }
                else {
                    assertTrue(100 < phi);
                    assertFalse(failureDetector.isAvailable(timestampMillis));
                }
                continue;
            }
            else if (i > 200) {
                if (i % 5 == 0) {
                    double phi = failureDetector.phi(timestampMillis);
                    assertTrue(0.1 < phi && phi < 0.5);
                    assertTrue(failureDetector.isAvailable(timestampMillis));
                    continue;
                }
            }
            failureDetector.add(timestampMillis);
            assertTrue(failureDetector.phi(timestampMillis) < 0.1);
            assertTrue(failureDetector.isAvailable(timestampMillis));
        }
    }
}