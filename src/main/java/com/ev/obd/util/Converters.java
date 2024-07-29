package com.ev.obd.util;

public class Converters {
    public static double kmPerHourToMetersPerSecond(double kmPerHour) {
        return kmPerHour * 1000 / 3600;
    }
}
