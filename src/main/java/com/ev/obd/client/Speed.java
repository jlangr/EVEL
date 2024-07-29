package com.ev.obd.client;

import java.time.LocalDateTime;

public record Speed(LocalDateTime timestamp, int kmPerHour) {
}
