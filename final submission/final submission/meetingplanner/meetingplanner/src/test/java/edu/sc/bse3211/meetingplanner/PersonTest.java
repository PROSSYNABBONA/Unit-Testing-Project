package edu.sc.bse3211.meetingplanner;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class PersonTest {

    private Person person;

    @Before
    public void setUp() {
        person = new Person();
    }

    @Test
    public void testAddMeeting_Success() {
        // Test Case 1: Add a meeting to an empty calendar
        Meeting meeting = new Meeting(3, 22);
        try {
            person.addMeeting(meeting);
            assertTrue("Meeting should be added to the calendar", person.isBusy(3, 22, 10, 12));
        } catch (TimeConflictException e) {         
            fail("Unexpected TimeConflictException: " + e.getMessage());
        }
    }

    @Test
    public void testPrintAgenda_Month() {
        // Test Case 2: Print agenda for a specific month
        String agenda = person.printAgenda(3);

        assertNotNull("Agenda should not be null", agenda);
    }

    @Test
    public void testPrintAgenda_Day() {
        // Test Case 3: Print agenda for a specific day
        String agenda = person.printAgenda(3, 22);

        assertNotNull("Agenda should not be null", agenda);
    }

    @Test
    public void testIsBusy_TimeSlotWhenNotBusy() {
        // Test Case 4: Check availability for a specific time slot
        try {
            assertFalse("Person should not be busy at this time slot", person.isBusy(3, 22, 10, 12));
        } catch (TimeConflictException e) {
            fail("Unexpected TimeConflictException: " + e.getMessage());
        }
    }

    @Test
    public void testIsBusy_TimeSlotWhenBusy() {
        Meeting meeting = new Meeting(5, 22);
        try {
            person.addMeeting(meeting);
            assertTrue("Person should not be busy at this time slot", person.isBusy(5, 22, 10, 12));
        } catch (TimeConflictException e) {
            fail("Unexpected TimeConflictException: " + e.getMessage());
        }
    }

    @Test
    public void testGetMeeting_Success() {
        // Test Case 5: Get a meeting for a specific date and index
        Meeting meeting = new Meeting(4, 22);
        try {
            person.addMeeting(meeting);
            Meeting retrievedMeeting = person.getMeeting(4, 22, 0);

            assertNotNull("Retrieved meeting should not be null", retrievedMeeting);
            assertEquals("Retrieved meeting should match the added meeting", meeting, retrievedMeeting);
        } catch (TimeConflictException e) {
            fail("Unexpected TimeConflictException: " + e.getMessage());
        }
    }

    @Test
    public void testRemoveMeeting_Success() {
        // Test Case 6: Remove a meeting from the calendar
        Meeting meeting = new Meeting(6, 22);
        try {
            person.addMeeting(meeting);
            person.removeMeeting(6, 22, 0);

            assertFalse("Meeting should be removed from the calendar", person.isBusy(3, 22, 10, 12));
        } catch (TimeConflictException e) {
            fail("Unexpected TimeConflictException: " + e.getMessage());
        }
    }
}
