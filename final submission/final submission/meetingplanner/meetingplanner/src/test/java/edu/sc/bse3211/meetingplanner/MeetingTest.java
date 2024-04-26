package edu.sc.bse3211.meetingplanner;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;

public class MeetingTest {
	// Add test methods here. 
    // You are not required to write tests for all classes.
    public Meeting meeting;
    public Person person;
    public Room room;




    public void setUp() {
        // Create a sample meeting object for testing
        ArrayList<Person> attendees = new ArrayList<>();
        attendees.add(new Person("John"));
        attendees.add(new Person("Alice"));
        room = new Room("Meeting Room 1");
        meeting = new Meeting(3, 20, 9, 11, attendees, room, "Team Meeting");
    }

    @Test
    public void testMeetingAndRoom_InitializedCorrectly(){
        setUp();

        assertNotNull(room);
        assertNotNull("Meeting initialized correctly", meeting);
    }

    @Test
    public void testAddAttendee() {
        setUp();

        Person newAttendee = new Person("Bob");
        meeting.addAttendee(newAttendee);
        assertTrue(meeting.getAttendees().contains(newAttendee));
    }

    @Test
    public void testRemoveAttendee() {
        setUp();

        Person attendeeToRemove = meeting.getAttendees().get(1);
        meeting.removeAttendee(attendeeToRemove);
        assertFalse(meeting.getAttendees().contains(attendeeToRemove));
    }

    @Test
    public void testToString() {
        setUp();

        String expectedString = "3/20, 9 - 11,Meeting Room 1: Team Meeting\nAttending: John,Alice";
        assertEquals(expectedString, meeting.toString().trim());
    }

    @Test
    public void testGettersAndSetters() {
        setUp();

        assertEquals(3, meeting.getMonth());
        assertEquals(20, meeting.getDay());
        assertEquals(9, meeting.getStartTime());
        assertEquals(11, meeting.getEndTime());
        assertEquals("Team Meeting", meeting.getDescription());
        assertEquals("Meeting Room 1", meeting.getRoom().getID());

        meeting.setMonth(4);
        meeting.setDay(15);
        meeting.setStartTime(10);
        meeting.setEndTime(12);
        meeting.setDescription("Project Review");
        Room newRoom = new Room("Conference Room");
        meeting.setRoom(newRoom);

        assertEquals(4, meeting.getMonth());
        assertEquals(15, meeting.getDay());
        assertEquals(10, meeting.getStartTime());
        assertEquals(12, meeting.getEndTime());
        assertEquals("Project Review", meeting.getDescription());
        assertEquals("Conference Room", meeting.getRoom().getID());
    }


}
