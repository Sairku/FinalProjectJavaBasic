package com.booking.controller;

import com.booking.entities.*;
import com.booking.service.*;
import com.booking.utils.Input;
import com.booking.utils.Menu;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingControllerTest {
    @InjectMocks
    private final BookingController bookingController = new BookingController();

    @Mock
    private final Scanner scanner = mock(Scanner.class);

    @Mock
    private final UserService userService = new UserService();

    private final BookingService bookingService = new BookingService();
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    int flightId = (int) Instant.now().toEpochMilli();
    String cityFrom = "Kyiv";
    String cityTo = "Lviv";
    LocalDateTime dateFlight = LocalDateTime.now();
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    Flight flight;

    {
        FlightService flightService = new FlightService();

        flight = new Flight(flightId, cityTo, cityFrom, dateFlight.format(dateFormat), 250);

        flightService.save(flight);
    }

    @BeforeEach
    public void setUp() {
        // reassign the standard output stream to a new PrintStream with a ByteArrayOutputStream
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        // reassign the standard output back
        System.setOut(standardOut);
    }

    @Test
    public void testBookFlight() {
        int seatsCount = 1;

        User user = new User((int) Instant.now().toEpochMilli(), "Kate", "Smith");

        try (
                MockedStatic<Input> input = mockStatic(Input.class);
                MockedStatic<Menu> menu = mockStatic(Menu.class)
        ) {
            // mock some data input functions
            input.when(() -> Input.enterDate(anyString(), any())).thenReturn(dateFlight.toLocalDate());
            input.when(() -> Input.enterIntNumber(anyString(), anyInt())).thenReturn(seatsCount);

            // enter data
            when(scanner.nextLine())
                    .thenReturn(cityFrom)
                    .thenReturn(cityTo);

            // mock choosing flight
            menu.when(() -> Menu.chooseNumericMenu(any(), anyBoolean(), anyString())).thenReturn(1);

            // mock getting passenger
            when(userService.getUserByName(anyString(), anyString())).thenReturn(user);

            Booking booking = assertDoesNotThrow(() -> bookingController.bookFlight(user.getId()));
            assertNotNull(booking);
        }
    }

    @Test
    public void testDeleteBook() {
        long id = Instant.now().toEpochMilli();
        int userId = (int) Instant.now().toEpochMilli();
        int expected = bookingService.getAll().size();

        Booking booking = new Booking(id, flight.getId(), userId, new ArrayList<>());
        bookingService.save(booking);

        assertEquals(expected + 1, bookingService.getAll().size());

        try (MockedStatic<Input> input = mockStatic(Input.class)) {
            // mock some data input function
            input.when(() -> Input.enterLongNumber(anyString(), anyInt())).thenReturn(id);

            bookingController.deleteBooking();

            int actual = bookingService.getAll().size();
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testFindBooking() {
        long id = Instant.now().toEpochMilli();
        String name = "Tom";
        String surname = "Smith";
        User user = new User((int) Instant.now().toEpochMilli(), name, surname);

        // enter data
        when(scanner.nextLine())
                .thenReturn(name)
                .thenReturn(surname);

        // mock getting passenger
        when(userService.getUserByName(anyString(), anyString())).thenReturn(user);

        Booking booking = new Booking(id, flight.getId(), user.getId(), new ArrayList<>());
        bookingService.save(booking);

        String expectedPart = "your bookings";
        bookingController.findBookings();

        // verify that the outputStreamCaptor contains the content we were expecting
        assertTrue(outputStreamCaptor.toString().trim().toLowerCase().contains(expectedPart));

        // create and mock a new user in order not to find a booking
        user = new User((int) Instant.now().toEpochMilli(), name, surname);
        when(userService.getUserByName(anyString(), anyString())).thenReturn(user);
        expectedPart = "no bookings";

        bookingController.findBookings();
        assertTrue(outputStreamCaptor.toString().trim().toLowerCase().contains(expectedPart));

    }
}
