package com.ev.obd.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.ev.obd.client.ObdSpeed;
import com.ev.obd.client.Speed;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class AverageAccelerationTest {

    @Test
    public void testCalculateAverageAcceleration() {
        ObdSpeed obdSpeed = Mockito.mock(ObdSpeed.class);
        LocalDate testDate = LocalDate.of(2024, 7, 28);

        List<Speed> mockSpeeds = Arrays.asList(
                new Speed(LocalDateTime.of(2024, 7, 28, 0, 0, 1), 10),
                new Speed(LocalDateTime.of(2024, 7, 28, 0, 0, 2), 15),
                new Speed(LocalDateTime.of(2024, 7, 28, 0, 0, 3), 20),
                new Speed(LocalDateTime.of(2024, 7, 28, 0, 0, 4), 25),
                new Speed(LocalDateTime.of(2024, 7, 28, 0, 0, 5), 25),
                new Speed(LocalDateTime.of(2024, 7, 28, 0, 0, 6), 20),
                new Speed(LocalDateTime.of(2024, 7, 28, 0, 0, 7), 15),
                new Speed(LocalDateTime.of(2024, 7, 28, 0, 0, 8), 10)
        );

        when(obdSpeed.speeds(testDate)).thenReturn(mockSpeeds);

        AverageAcceleration averageAcceleration = new AverageAcceleration(obdSpeed);
        double result = averageAcceleration.calculateAverageAcceleration(testDate);

        assertEquals(0.0, result, 0.001);
    }

    @Test
    public void testCalculateAverageAccelerationNonZero() {
        ObdSpeed obdSpeed = Mockito.mock(ObdSpeed.class);
        LocalDate testDate = LocalDate.of(2024, 7, 28);

        List<Speed> mockSpeeds = Arrays.asList(
                new Speed(LocalDateTime.of(2024, 7, 28, 0, 0, 1), 10),
                new Speed(LocalDateTime.of(2024, 7, 28, 0, 0, 2), 20),
                new Speed(LocalDateTime.of(2024, 7, 28, 0, 0, 3), 30),
                new Speed(LocalDateTime.of(2024, 7, 28, 0, 0, 4), 25),
                new Speed(LocalDateTime.of(2024, 7, 28, 0, 0, 5), 30),
                new Speed(LocalDateTime.of(2024, 7, 28, 0, 0, 6), 40)
        );

        when(obdSpeed.speeds(testDate)).thenReturn(mockSpeeds);

        AverageAcceleration averageAcceleration = new AverageAcceleration(obdSpeed);
        double result = averageAcceleration.calculateAverageAcceleration(testDate);

        // Manually calculate expected average acceleration
        double[] accelerations = {
                (20.0 * 1000 / 3600 - 10.0 * 1000 / 3600) / 1,
                (30.0 * 1000 / 3600 - 20.0 * 1000 / 3600) / 1,
                (25.0 * 1000 / 3600 - 30.0 * 1000 / 3600) / 1,
                (30.0 * 1000 / 3600 - 25.0 * 1000 / 3600) / 1,
                (40.0 * 1000 / 3600 - 30.0 * 1000 / 3600) / 1
        };
        double expectedAverageAcceleration = Arrays.stream(accelerations).average().orElse(0);

        assertEquals(expectedAverageAcceleration, result, 0.001);
    }
}
