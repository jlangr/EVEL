package com.ev.obd.util;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class ConvertersTest {

    @Test
    public void testKmPerHourToMetersPerSecond() {
        assertEquals(26.3888, Converters.kmPerHourToMetersPerSecond(95), 0.0001);
    }
}