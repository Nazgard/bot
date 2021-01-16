package dev.makarov.bot.telegram.datecalc;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DateCalculatorTest {

    private static final ZoneId ZONE_ID = ZoneId.of("Europe/Moscow");
    private static final ZonedDateTime NOW = ZonedDateTime.of(
            LocalDate.of(2020, Month.DECEMBER, 4),
            LocalTime.of(17, 0, 0),
            ZONE_ID);

    @Test
    public void testApril5() {
        DateCalculator calculator = new DateCalculator("", NOW);
        assertFalse(calculator.getOutput().isEmpty());
        assertEquals("608 дней, 52 608 600 секунд", calculator.getOutput().get());
    }

    @Test
    public void testDifferentDate() {
        DateCalculator calculator = new DateCalculator("2012-05-20", NOW);
        assertFalse(calculator.getOutput().isEmpty());
        assertEquals("3 120 дней, 269 632 800 секунд", calculator.getOutput().get());
    }

    @Test
    public void testDifferentDates() {
        DateCalculator calculator = new DateCalculator("2012-05-20 2015-12-10", NOW);
        assertFalse(calculator.getOutput().isEmpty());
        assertEquals("1 299 дней, 112 237 200 секунд", calculator.getOutput().get());
    }

    @Test
    public void testDifferentDatesWithDoubleSpace() {
        DateCalculator calculator = new DateCalculator("2012-05-20  2015-12-10", NOW);
        assertFalse(calculator.getOutput().isEmpty());
        assertEquals("1 299 дней, 112 237 200 секунд", calculator.getOutput().get());
    }

    @Test
    public void testDifferentDatesWithFirstSpace() {
        DateCalculator calculator = new DateCalculator(" 2012-05-20 2015-12-10", NOW);
        assertFalse(calculator.getOutput().isEmpty());
        assertEquals("1 299 дней, 112 237 200 секунд", calculator.getOutput().get());
    }

    @Test
    public void testIncorrectInput() {
        DateCalculator calculator = new DateCalculator("12312312", NOW);
        assertTrue(calculator.getOutput().isEmpty());
    }

    @Test
    public void testThreeDates() {
        DateCalculator calculator = new DateCalculator("2012-05-20 2015-12-10 2010-01-10", NOW);
        assertTrue(calculator.getOutput().isEmpty());
    }

    @Test
    public void testConstructorWithoutDateNow() {
        DateCalculator calculator = new DateCalculator("2012-05-20 2015-12-10");
        assertFalse(calculator.getOutput().isEmpty());
    }

}