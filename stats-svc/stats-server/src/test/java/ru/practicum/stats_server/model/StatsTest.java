package ru.practicum.stats_server.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class StatsTest {

    @Test
    void testStatsCreation() {
        LocalDateTime now = LocalDateTime.now();
        Stats stats = new Stats();
        stats.setId(1L);
        stats.setApp("TestApp");
        stats.setUri("/test/uri");
        stats.setIp("192.168.0.1");
        stats.setTimestamp(now);

        assertNotNull(stats);
        assertEquals(1L, stats.getId());
        assertEquals("TestApp", stats.getApp());
        assertEquals("/test/uri", stats.getUri());
        assertEquals("192.168.0.1", stats.getIp());
        assertEquals(now, stats.getTimestamp());
    }

    @Test
    void testStatsSettersAndGetters() {
        Stats stats = new Stats();
        stats.setId(2L);
        stats.setApp("AnotherApp");
        stats.setUri("/another/uri");
        stats.setIp("192.168.0.2");
        stats.setTimestamp(LocalDateTime.of(2023, 10, 1, 12, 0));

        assertEquals(2L, stats.getId());
        assertEquals("AnotherApp", stats.getApp());
        assertEquals("/another/uri", stats.getUri());
        assertEquals("192.168.0.2", stats.getIp());
        assertEquals(LocalDateTime.of(2023, 10, 1, 12, 0), stats.getTimestamp());
    }
}
