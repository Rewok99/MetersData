package service;

import model.MeterReading;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MeterReadingServiceTest {

    @Test
    void testAuthenticateUser() {
        MeterReadingService.registerUser("testUser", "password", false);

        assertTrue(MeterReadingService.authenticateUser("testUser", "password"));
        assertFalse(MeterReadingService.authenticateUser("testUser", "wrongPassword"));
        assertFalse(MeterReadingService.authenticateUser("nonexistentUser", "password"));
    }

    @Test
    void testSubmitMeterReading() {
        MeterReadingService.registerUser("testUser", "password", false);

        MeterReading reading = new MeterReading(1, 2024, 100, 50, 30);
        MeterReadingService.submitMeterReading("testUser", reading);

        assertEquals(reading, MeterReadingService.getLatestMeterReading("testUser"));
    }

    @Test
    void testGetMeterReadingHistory() {
        MeterReadingService.registerUser("testUser", "password", false);

        MeterReading reading1 = new MeterReading(1, 2024, 100, 50, 30);
        MeterReading reading2 = new MeterReading(2, 2024, 120, 55, 35);

        MeterReadingService.submitMeterReading("testUser", reading1);
        MeterReadingService.submitMeterReading("testUser", reading2);

        assertEquals(List.of(reading1, reading2), MeterReadingService.getMeterReadingHistory("testUser"));
    }

    @Test
    void testGetLatestMeterReading() {
        MeterReadingService.registerUser("testUser", "password", false);

        MeterReading reading1 = new MeterReading(1, 2024, 100, 50, 30);
        MeterReading reading2 = new MeterReading(2, 2024, 120, 55, 35);

        MeterReadingService.submitMeterReading("testUser", reading1);
        MeterReadingService.submitMeterReading("testUser", reading2);

        assertEquals(reading2, MeterReadingService.getLatestMeterReading("testUser"));
    }


}
