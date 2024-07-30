package com.ev.obd.service;

import com.ev.obd.client.ObdSpeedAPI;
import com.ev.obd.client.Speed;
import com.ev.obd.util.Converters;

import java.time.LocalDate;
import java.util.List;

public class AverageAccelerationImpl implements AverageAcceleration {

    private final ObdSpeedAPI obdSpeed;

    public AverageAccelerationImpl(ObdSpeedAPI obdSpeed) {
        this.obdSpeed = obdSpeed;
    }

    @Override
    public double calculate(LocalDate localDate) {
        List<Speed> speeds = obdSpeed.speeds(localDate);

        if (speeds.size() < 2) {
            throw new IllegalArgumentException("Not enough data points to calculate acceleration");
        }

        double totalAcceleration = 0.0;
        int count = 0;

        for (int i = 1; i < speeds.size(); i++) {
            Speed previous = speeds.get(i - 1);
            Speed current = speeds.get(i);

            double previousSpeed = Converters.kmPerHourToMetersPerSecond(previous.kmPerHour());
            double currentSpeed = Converters.kmPerHourToMetersPerSecond(current.kmPerHour());
            double timeInterval = (current.timestamp().getSecond() - previous.timestamp().getSecond());

            if (timeInterval == 0) {
                continue; // Avoid division by zero
            }

            double acceleration = (currentSpeed - previousSpeed) / timeInterval;
            totalAcceleration += acceleration;
            count++;
        }

        return count == 0 ? 0 : totalAcceleration / count;
    }
}
