package dev.makarov.bot.telegram.datecalc;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Optional;

class DateCalculator {

    private static final Locale LOCALE = Locale.forLanguageTag("ru-RU");
    private static final ZoneId MOSCOW_ZONE_ID = ZoneId.of("Europe/Moscow");
    private static final ZonedDateTime APRIL_5 = ZonedDateTime.of(
            LocalDate.of(2019, Month.APRIL, 5),
            LocalTime.of(19, 30),
            MOSCOW_ZONE_ID);

    private final String msg;
    private final ZonedDateTime now;

    DateCalculator(String msg) {
        this(msg, ZonedDateTime.now(MOSCOW_ZONE_ID));
    }

    DateCalculator(String msg, ZonedDateTime now) {
        this.msg = msg.replaceAll("\\s+", " ").trim();
        this.now = now;
    }

    public Optional<String> getOutput() {
        try {
            if (msg.isEmpty()) {
                return Optional.of(april5());
            }
            String[] split = msg.split(" ");
            if (split.length == 1) {
                return Optional.of(betweenNow(split[0]));
            }
            if (split.length == 2) {
                return Optional.of(betweenDates(split[0], split[1]));
            }
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
        return Optional.empty();
    }

    private String april5() {
        return formatOutput(Duration.between(APRIL_5, now).toSeconds());
    }

    private String betweenNow(String rawDate) {
        ZonedDateTime time = ZonedDateTime.of(LocalDate.parse(rawDate), LocalTime.MIN, MOSCOW_ZONE_ID);
        return formatOutput(Duration.between(time, now).toSeconds());
    }

    private String betweenDates(String rawDate1, String rawDate2) {
        ZonedDateTime time1 = ZonedDateTime.of(LocalDate.parse(rawDate1), LocalTime.MIN, MOSCOW_ZONE_ID);
        ZonedDateTime time2 = ZonedDateTime.of(LocalDate.parse(rawDate2), LocalTime.MIN, MOSCOW_ZONE_ID);
        return formatOutput(Duration.between(time1, time2).toSeconds());
    }

    private String formatOutput(long seconds) {
        long days = seconds / (60 * 60 * 24);
        return String.format(LOCALE, "%,d дней, %,d секунд", days, seconds);
    }

}
