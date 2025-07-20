package org.example.command_handler.helper;

import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Slf4j
public class DataHelper {
    public static boolean isIsoFormat(String time) {
        try {
            Instant.from(DateTimeFormatter.ISO_INSTANT.parse(time));
            return true;
        } catch (DateTimeParseException e) {
            log.info("Something happened trying to parse to iso: '{}'", time);
            e.printStackTrace();
        }
        return false;
    }
}
