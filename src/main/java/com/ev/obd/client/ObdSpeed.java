package com.ev.obd.client;

import java.time.LocalDate;
import java.util.List;

public interface ObdSpeed {
   List<Speed> speeds(LocalDate localDate);
}
