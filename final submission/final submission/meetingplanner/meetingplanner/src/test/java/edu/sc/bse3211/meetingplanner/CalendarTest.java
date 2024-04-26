package edu.sc.bse3211.meetingplanner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Test;

public class CalendarTest {
	// Add test methods here. 
	// You are not required to write tests for all classes.

	@Test
	public void testAddMeeting_holiday() {
		// Create Janan Luwum holiday
		Calendar calendar = new Calendar();
		// Add to calendar object.
		try {
			Meeting janan = new Meeting(2, 16, "Janan Luwum");
			calendar.addMeeting(janan);
			// Verify that it was added.
			Boolean added = calendar.isBusy(2, 16, 0, 23);
			assertTrue("Janan Luwum Day should be marked as busy on the calendar",added);
		} catch(TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}

	// Testing default constructor
	@Test
	public void testCalendarInitializedCorrectly(){
		Calendar calendar = new Calendar();
		assertNotNull("Calendar object is not null", calendar);
	}

	@Test
	public void testCalendarInitializedWithDaysThatDonnotExist(){
		Calendar calendar = new Calendar();
		String description1 = calendar.getMeeting(2, 29, 0).getDescription();
		String description2 = calendar.getMeeting(2, 30, 0).getDescription();
		String description3 = calendar.getMeeting(2, 31, 0).getDescription();
		String description4 = calendar.getMeeting(4, 31, 0).getDescription();
		String description5 = calendar.getMeeting(6, 31, 0).getDescription();
		String description6 = calendar.getMeeting(9, 31, 0).getDescription();
		String description7 = calendar.getMeeting(11, 31, 0).getDescription();
		String description8 = calendar.getMeeting(11, 31, 0).getDescription();

		assertEquals("Day does not exist", description1);
		assertEquals("Day does not exist", description2);
		assertEquals("Day does not exist", description3);
		assertEquals("Day does not exist", description4);
		assertEquals("Day does not exist", description5);
		assertEquals("Day does not exist", description6);
		assertEquals("Day does not exist", description7);
		assertEquals("Day does not exist", description8);
	}

	
	// Testing isBusy function
	@Test
	public void testIsBusyForNonBusyTimeFrame(){
		Calendar calendar = new Calendar();
		try{
			assertFalse("Time frame is not busy", calendar.isBusy(1, 1, 8, 10));
		}catch(TimeConflictException e){
			fail("Unexpected TimeConflictException: " + e.getMessage());
		}

	}
	
	@Test
	public void testIsBusyForOverlappingTimeFrame(){
		Calendar calendar = new Calendar();
		try{
			Meeting meeting = new Meeting(1, 1, 9, 11);
			calendar.addMeeting(meeting);
		}catch(TimeConflictException e){
			fail("Unexpected TimeConflictException: " + e.getMessage());
		}

		try{
			assertTrue("Time frame overlaps with existing meeting", calendar.isBusy(1, 1, 9, 11));
		}catch(TimeConflictException e){
			fail("Unexpected TimeConflictException: " + e.getMessage());
		}

	}

	@Test(expected = TimeConflictException.class)
	public void testIsBusyForInvalidMonth() throws TimeConflictException{
		Calendar calendar = new Calendar();
		calendar.isBusy(13, 1, 8, 10);
	}

	@Test(expected = TimeConflictException.class)
	public void testIsBusyForInvalidDay() throws TimeConflictException{
		Calendar calendar = new Calendar();
		calendar.isBusy(1, 32, 8, 10);
	}

	@Test(expected = TimeConflictException.class)
	public void testIsBusyForInvalidStartTime() throws TimeConflictException{
		Calendar calendar = new Calendar();
		calendar.isBusy(1, 1, -1, 10);
	}

	@Test(expected = TimeConflictException.class)
	public void testIsBusyForInvalidEndTime() throws TimeConflictException{
		Calendar calendar = new Calendar();
		calendar.isBusy(1, 1, 8, 24);
	}

	@Test(expected = TimeConflictException.class)
	public void testIsBusyForEndTimeBeforeStartTime() throws TimeConflictException{
		Calendar calendar = new Calendar();
		calendar.isBusy(1, 1, 10, 8);
	}

	@Test(expected = TimeConflictException.class)
	public void testIsBusyForStartTimeEqualsEndTime() throws TimeConflictException{
		Calendar calendar = new Calendar();
		calendar.isBusy(1, 1, 8, 8);
	}

	// Test checkTimes method
	@Test
	public void testCheckTimesForValidInputs(){
		try{
			// Test for valid month, day, start time and end time
			Calendar.checkTimes(1, 1, 8, 10);
		} catch(TimeConflictException e){
			fail("Unexpected TimeConflictException: " + e.getMessage());
		}
	}

	@Test(expected = TimeConflictException.class)
	public void testCheckTimesForInvalidMonth() throws TimeConflictException{
		// Test for invalid month
		Calendar.checkTimes(13, 1, 8, 10);
	}

	@Test(expected = TimeConflictException.class)
	public void testCheckTimesForInvalidDay() throws TimeConflictException{
		// Test for invalid day
		Calendar.checkTimes(1, 32, 8, 10);
	}

	@Test(expected = TimeConflictException.class)
	public void testCheckTimesForInvalidStartTime() throws TimeConflictException{
		// Test for invalid start time
		Calendar.checkTimes(1, 1, -1, 10);
	}

	@Test(expected = TimeConflictException.class)
	public void testCheckTimesForInvalidEndTime() throws TimeConflictException{
		// Test for invalid end time
		Calendar.checkTimes(1, 1, 8, 24);
	}

	@Test(expected = TimeConflictException.class)
	public void testCheckTimesForEndTimeBeforeStartTime() throws TimeConflictException{
		// Test for end time before start time
		Calendar.checkTimes(1, 1, 10, 8);
	}

	@Test(expected = TimeConflictException.class)
	public void testCheckTimesForStartTimeEqualsEndTime() throws TimeConflictException{
		// Test for start time equals end time
		Calendar.checkTimes(1, 1, 8, 8);
	}

	// Test addMeeting function
	@Test
	public void testAddMeetingOnNonBusyDay(){
		Calendar calendar = new Calendar();
		try{
			Meeting meeting = new Meeting(1, 1, 8, 10);
			calendar.addMeeting(meeting);
			assertTrue("Meeting added successfully", calendar.isBusy(1, 1, 8, 10));
		} catch (TimeConflictException e){
			fail("Unexpected TimeConflictException: " + e.getMessage());
		}
	}

	@Test
	public void testAddMeetingWithBusyTimeFrame(){
		Calendar calendar = new Calendar();
		try{
			// Add initial meeting
			Meeting initialMeeting = new Meeting(3, 29, "Good Friday");
			calendar.addMeeting(initialMeeting);

			// Try adding a meeting that overlaps with the initial meeting
			Meeting overlappingMeeting = new Meeting(3, 29, "Teacher's Retreat");
			calendar.addMeeting(overlappingMeeting);

			// Expect TimeConflictException to be thrown
			fail("Expected TimeConflictExpection, but no exception was thrown");
		} catch (TimeConflictException e){
			assertTrue("TimeConflictException thrown successfully", true);
		} catch (Exception e){
			fail("Unexpected exception thrown: " + e.getMessage());
		}
	}

	@Test(expected = TimeConflictException.class)
	public void testAddMeetingWithInvalidMonth() throws TimeConflictException {
		Calendar calendar = new Calendar();
		Meeting invalidMonthMeeting = new Meeting(35, 2, 8, 10);
		calendar.addMeeting(invalidMonthMeeting);
	}

	@Test(expected = TimeConflictException.class)
	public void testAddMeetingWithInvalidDay() throws TimeConflictException {
		Calendar calendar = new Calendar();
		Meeting invalidDayMeeting = new Meeting(1, 32, 8, 10);
		calendar.addMeeting(invalidDayMeeting);
	}

	@Test(expected = TimeConflictException.class)
	public void testAddMeetingWithInvalidStartTime() throws TimeConflictException {
		Calendar calendar = new Calendar();
		Meeting invalidStartMeeting = new Meeting(1, 1, -1, 10);
		calendar.addMeeting(invalidStartMeeting);
	}

	@Test(expected = TimeConflictException.class)
	public void testAddMeetingWithInvalidEndTime() throws TimeConflictException {
		Calendar calendar = new Calendar();
		Meeting invalidEndMeeting = new Meeting(1, 1, 8, 24);
		calendar.addMeeting(invalidEndMeeting);
	}

	@Test(expected = TimeConflictException.class)
	public void testAddMeetingWithEndTimeBeforeStartTime() throws TimeConflictException {
		Calendar calendar = new Calendar();
		Meeting invalidOrderMeeting = new Meeting(1, 1, 12, 10);
		calendar.addMeeting(invalidOrderMeeting);
	}

	@Test(expected = TimeConflictException.class)
	public void testAddMeetingWithStartTimeEqualsEndTime() throws TimeConflictException {
		Calendar calendar = new Calendar();
		Meeting startEqualsEndTimeMeeting = new Meeting(1, 1, 8, 8);
		calendar.addMeeting(startEqualsEndTimeMeeting);
	}

	// test clearSchedule function
	@Test
	public void testClearScheduleForValidMonthAndDay(){
		Calendar calendar = new Calendar();
		try{
			Meeting meeting = new Meeting(1, 1, "Test Meeting");
			calendar.addMeeting(meeting);
		} catch(TimeConflictException e){
			fail("Unexpected TimeConflictException: " + e.getMessage());
		}

		// Clear the schedule for a valid month and day
		calendar.clearSchedule(1, 1);

		try{
			// Verify that the schedule is cleared for the specified month and day
			calendar.getMeeting(1, 1, 0);
		} catch (IndexOutOfBoundsException e){
			assertTrue("Expected IndexOutOfBoundsException: ",true);
		}
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testClearScheduleForInvalidMonthAndDay(){
		Calendar calendar = new Calendar();
		calendar.clearSchedule(13, 32);
	}	

	// test printAgenda

	@Test
	public void testPrintAgendaForValidMonth(){
		// Create a Calendar object
		Calendar calendar = new Calendar();

		try{
			// Create a Room object
			Room room = new Room("Test Room");

			// Create a list of attendees
			ArrayList<Person> attendees = new ArrayList<>();
			attendees.add(new Person("John Doe"));
			attendees.add(new Person("Jane Snow"));

			// Create Meeting objects using the existing constructor
			Meeting meeting1 = new Meeting(1, 1, 8, 10, attendees, room, "Test Meeting 1");
			calendar.addMeeting(meeting1);

			Meeting meeting2 = new Meeting(1, 2, 8, 10, attendees, room, "Test Meeting 2");
			calendar.addMeeting(meeting2);

		} catch (TimeConflictException e){
			fail("Unexpected TimeConflictException: " + e.getMessage());
		}

		String agenda = calendar.printAgenda(1);

		assertTrue("Agenda contains Test Meeting 1", agenda.contains("Test Meeting 1"));
		assertTrue("Agenda contains Test Meeting 2", agenda.contains("Test Meeting 2"));
	}

	@Test
	public void testPrintAgendaForValidMonthAndDay(){
		// Create a Calendar object
		Calendar calendar = new Calendar();

		try{
			// Create a Room object
			Room room = new Room("Test Room");

			// Create a list of attendees
			ArrayList<Person> attendees = new ArrayList<>();
			attendees.add(new Person("John Doe"));
			attendees.add(new Person("Jane Snow"));

			// Create Meeting objects using the existing constructor
			Meeting meeting1 = new Meeting(1, 1, 8, 10, attendees, room, "Test Meeting 1");
			calendar.addMeeting(meeting1);

			Meeting meeting2 = new Meeting(1, 1, 12, 14, attendees, room, "Test Meeting 2");
			calendar.addMeeting(meeting2);

		} catch (TimeConflictException e){
			fail("Unexpected TimeConflictException: " + e.getMessage());
		}

		String agenda = calendar.printAgenda(1, 1);

		assertTrue("Agenda contains Test Meeting 1", agenda.contains("Test Meeting 1"));
		assertTrue("Agenda contains Test Meeting 2", agenda.contains("Test Meeting 2"));
	}


	@Test
	public void testPrintAgendaForFreeMonth(){
		Calendar calendar = new Calendar();
		String agenda = calendar.printAgenda(3);

		assertEquals("Agenda is empty for invalid month", "Agenda for 3:\n", agenda);
	}

	@Test
	public void testPrintAgendaForFreeMonthAndDay(){
		Calendar calendar = new Calendar();
		String agenda = calendar.printAgenda(1, 2);
		assertEquals("Agenda is empty for invalid month", "Agenda for 1/2:\n", agenda);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testPrintAgendaForInvalidMonth(){
		Calendar calendar = new Calendar();
		calendar.printAgenda(14);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testPrintAgendaForInvalidMonthAndValidDay(){
		Calendar calendar = new Calendar();
		calendar.printAgenda(14, 1);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testPrintAgendaForValidMonthAndInvalidDay(){
		Calendar calendar = new Calendar();
		calendar.printAgenda(1, 32);
	}

	// test getMeeting function
	@Test
	public void testGetMeetingForOccupiedMonthAndDay(){
		Calendar calendar = new Calendar();
		try{
			Meeting meeting1 = new Meeting(1, 1, "Meeting 1");
			calendar.addMeeting(meeting1);

		} catch(TimeConflictException e){
			fail("Unexpected TimeConflictException: " + e.getMessage());
		}

		// Retrieve the meeting for an occupied month and day
		Meeting retrivedMeeting = calendar.getMeeting(1, 1, 0);

		assertEquals("Retrieved meeting matches expected meeting", "Meeting 1", retrivedMeeting.getDescription());
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetMeetingForFreeMonthAndDay(){
		Calendar calendar = new Calendar();
		calendar.getMeeting(1, 1, 0);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetMeetingInvalidMonthAndValidDay(){
		Calendar calendar = new Calendar();
		calendar.getMeeting(14, 1, 0);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetMeetingValidMonthAndInvalidDay(){
		Calendar calendar = new Calendar();
		calendar.getMeeting(1, 32, 0);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetMeetingForOccupiedMonthAndDayAndInvalidIndex(){
		Calendar calendar = new Calendar();
		try{
			Meeting meeting1 = new Meeting(1, 1, "Meeting 1");
			calendar.addMeeting(meeting1);

		} catch(TimeConflictException e){
			fail("Unexpected TimeConflictException: " + e.getMessage());
		}

		calendar.getMeeting(1, 1, 1);
	}

	// test removeMeeting
	@Test(expected = IndexOutOfBoundsException.class)
	public void testRemoveMeetingForOccupiedMonthAndDay(){
		Calendar calendar = new Calendar();
		try{
			Meeting meeting1 = new Meeting(1, 1, "Meeting 1");
			calendar.addMeeting(meeting1);

		} catch(TimeConflictException e){
			fail("Unexpected TimeConflictException: " + e.getMessage());
		}

		calendar.removeMeeting(1, 1, 0);

		calendar.getMeeting(1, 1, 0).getDescription();
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testRemoveMeetingForFreeMonthAndDay(){
		Calendar calendar = new Calendar();
		calendar.removeMeeting(1, 1, 0);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testRemoveMeetingForInvalidMonthAndValidDay(){
		Calendar calendar = new Calendar();
		calendar.removeMeeting(13, 1, 0);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testRemoveMeetingForValidMonthAndInvalidDay(){
		Calendar calendar = new Calendar();
		calendar.removeMeeting(1, 32, 0);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testRemoveMeetingForOccupiedMonthAndDayAndInvalidIndex(){
		Calendar calendar = new Calendar();
		try{
			Meeting meeting1 = new Meeting(1, 1, "Meeting 1");
			calendar.addMeeting(meeting1);

		} catch(TimeConflictException e){
			fail("Unexpected TimeConflictException: " + e.getMessage());
		}

		calendar.removeMeeting(1, 1, 1);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testRemoveMeetingForFreeMonthAndDayAndInvalidIndex(){
		Calendar calendar = new Calendar();
		calendar.removeMeeting(1, 1, -1);
	}

}
