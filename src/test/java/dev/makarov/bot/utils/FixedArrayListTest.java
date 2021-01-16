package dev.makarov.bot.utils;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FixedArrayListTest {

    @Test
    void add() {
        List<String> testList = new FixedArrayList<>(5);
        testList.add("1");
        testList.add("2");
        testList.add("3");
        testList.add("4");
        testList.add("5");
        testList.add("6");

        assertEquals(5, testList.size());
        assertEquals("2", testList.get(0));
        assertEquals("6", testList.get(4));
    }

}