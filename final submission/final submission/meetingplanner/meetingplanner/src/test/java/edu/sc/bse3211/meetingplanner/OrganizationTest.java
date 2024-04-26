package edu.sc.bse3211.meetingplanner;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class OrganizationTest {

    private Organization organization;

    @Before
    public void setUp() {
        organization = new Organization();
    }

    @Test
    public void testGetEmployees() {
        // Test getting employees list
        ArrayList<Person> employees = organization.getEmployees();
        assertNotNull(employees);
        assertEquals(5, employees.size());
    }

    @Test
    public void testGetRooms() {
        // Test getting rooms list
        ArrayList<Room> rooms = organization.getRooms();
        assertNotNull(rooms);
        assertEquals(5, rooms.size());
    }

    @Test
    public void testGetRoomForValidRoom() {
        try {
            // Test getting a valid room
            Room room = organization.getRoom("LLT6A");
            assertNotNull(room);
            assertEquals("LLT6A", room.getID());
        } catch (Exception e) {
            fail("Exception should not be thrown: " + e.getMessage());
        }
    }

    @Test
    public void testGetRoomForInvalidRoom() {
        // Test getting an invalid room
        try {
            organization.getRoom("InvalidRoom");
            fail("Exception should be thrown for invalid room");
        } catch (Exception e) {
            assertTrue(e instanceof Exception);
            assertEquals("Requested room does not exist", e.getMessage());
        }
    }

    @Test
    public void testGetEmployeeForValidEmployee() {
        try {
            // Test getting a valid employee
            Person employee = organization.getEmployee("Namugga Martha");
            assertNotNull(employee);
            assertEquals("Namugga Martha", employee.getName());
        } catch (Exception e) {
            fail("Exception should not be thrown: " + e.getMessage());
        }
    }

    @Test
    public void testGetEmployeeForInvalidEmployee() {

        // Test getting an invalid employee
        try {
            organization.getEmployee("InvalidEmployee");
            fail("Exception should be thrown for invalid employee");
        } catch (Exception e) {
            assertTrue(e instanceof Exception);
            assertEquals("Requested employee does not exist", e.getMessage());
        }
    }
}
