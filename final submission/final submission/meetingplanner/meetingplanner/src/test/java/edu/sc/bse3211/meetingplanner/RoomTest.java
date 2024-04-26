package edu.sc.bse3211.meetingplanner;

import static org.junit.Assert.*;
import org.junit.Test;

public class RoomTest {
    //Testing the default constructor of the room class
    @Test
    public void testDefaultConstructor() {
        Room room = new Room();//Creating a new room object
        assertNotNull(room);//asserting that the room object is not null
        assertEquals("", room.getID());//makes sure that he ID of the room is an empty string
    }
    //testing the parameterised constuctor of the room
    @Test
    public void testParameterizedConstructor() {
        String roomID = "Room1";//initialization
        Room room = new Room(roomID);//creting a new room object
        assertNotNull(room);
        assertEquals(roomID, room.getID());//making sure that the ID  is equal to the roomID
    }
    
      //Testing adding a meeting
    @Test
    public void testAddMeeting() {
        Room room = new Room("Room1");
        Meeting meeting = new Meeting(4, 27, 9, 10);
        //attemping to add a meeting
        try {
            room.addMeeting(meeting);
            // making sure the meeting was added successfully
            assertTrue(room.isBusy(4, 27, 9, 10));//checking if room is marked as busy
        } catch (TimeConflictException e) {//handling the exception
            fail("Unexpected TimeConflictException: " + e.getMessage());
        }
    }


    //Testing the Printing of the agenda for a month.
    @Test
    public void testPrintAgendaByMonth() {
        Room room = new Room();
        assertNotNull(room.printAgenda(3));//printing the room for specific monrh
    }


    //Testing the Printing of the agenda for a month with day.
    @Test
    public void testPrintAgendaByMonthAndDay() {
        Room room = new Room();
        assertNotNull(room.printAgenda(3, 27));
    }


    //Testing if a meeting is scheduled during a timeframe..
    @Test
    public void testIsBusyWhenBusy() {
        Room room = new Room("Room1");
        Meeting meeting = new Meeting(3, 27, 9, 10);
        try {
            room.addMeeting(meeting);
            assertTrue(room.isBusy(3, 27, 9, 10));
            // assertFalse(room.isBusy(3, 27, 10, 11));
        } catch (TimeConflictException e) {// If the room is indeed busy during that time frame, the test passes.
            fail("Unexpected TimeConflictException: " + e.getMessage());
        }
    }
//expects the room not to be busy at that time,if room is not busy at that time,test passes
    @Test
    public void testIsBusyWhenNotBusy() {
        Room room = new Room("Room1");
        try {
            assertFalse(room.isBusy(3, 27, 10, 11));
        } catch (TimeConflictException e) {
            fail("Unexpected TimeConflictException: " + e.getMessage());
        }
    }


    //Test to Gets a particular meeting.
    @Test
    public void testGetMeeting() {
        Room room = new Room("Room1");
        Meeting meeting = new Meeting(3, 27);
        try {//retrieved meeting should be the same as added to room
            room.addMeeting(meeting);//meeting is added to the room
            assertEquals(meeting, room.getMeeting(3, 27, 0));
            
        }//if retrieved matches the new meeting,the test passes
         catch (TimeConflictException e) {
            fail("Unexpected TimeConflictException: " + e.getMessage());
        }
    }


    //Test to remove Meeting
    @Test
    public void testRemoveMeeting() {
        Room room = new Room("Room1");//creating a room object
        Meeting meeting = new Meeting(3, 27);//creating the meeting
        try {
            room.addMeeting(meeting);//adding a meeting
            room.removeMeeting(3, 27, 0);//removes
            assertFalse(room.isBusy(3, 27, 9, 10));//checks room is not busy during time the previously occupied meeting 
        }//expects room to be free now 
        catch (TimeConflictException e) {
            fail("Unexpected TimeConflictException: " + e.getMessage());
        }
    }
    
}