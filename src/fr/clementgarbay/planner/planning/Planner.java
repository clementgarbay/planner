package fr.clementgarbay.planner.planning;

import fr.clementgarbay.planner.planning.status.AvailabilityStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Planner tool to compute common time slots between persons.
 */
public class Planner extends TimeSlot {

    /**
     * The time interval is the quarter hour
     */
    public static final int TIME_INTERVAL = 15;

    private LocalDate date;
    private List<Person> persons;

    /**
     * Constructor
     * @param date      the date of the planning
     * @param timeStart the start time of the planning
     * @param timeEnd   the end time of the planning
     * @param persons   the list of persons
     */
    public Planner(LocalDate date, LocalTime timeStart, LocalTime timeEnd, List<Person> persons) {
        super(timeStart, timeEnd);
        this.date = date;
        this.persons = persons;
    }

    /**
     * Get a list of time slots (of a determinate duration) common to all of persons of the planning.
     * These slots are ordered by relevance, ie. by order of the highest number of free persons and/or have already scheduled events with a small priority.
     * @param timeSlotDuration  the desired duration for the search of common time slots
     * @param limit             the maximum number of CommonTimeSlot objects returned or null for no limit
     * @param compulsoryPersons the list of compulsory persons or null
     * @return                  a list of CommonTimeSlot objects
     */
    public List<CommonTimeSlot> getCommonTimeSlots(Integer timeSlotDuration, Integer limit, List<Person> compulsoryPersons) {
        if (limit != null && limit < 1)
            throw new IllegalArgumentException("The limit argument must be greater than 0 or null.");
        if (timeSlotDuration < TIME_INTERVAL || (timeSlotDuration % TIME_INTERVAL) != 0)
            throw new IllegalArgumentException("The time slot duration argument must be a multiple and greater than the time interval (" + TIME_INTERVAL + ").");

        long minutes = TimeSlot.getMinutesBetween(super.getTimeStart(), (super.getTimeEnd().minusMinutes(timeSlotDuration).plusMinutes(TIME_INTERVAL)));

        List<CommonTimeSlot> possibilities = new ArrayList<>();

        for (int i = 0; i < minutes; i += TIME_INTERVAL) {
            LocalTime timeSlotStart = super.getTimeStart().plusMinutes(i);
            LocalTime timeSlotEnd = timeSlotStart.plusMinutes(timeSlotDuration);

            Map<Person, List<AvailabilityStatus>> availabilityStatusListWithPersons = new HashMap<>();

            // For each person in the planner
            for (Person person : this.persons) {
                // Get the availability status list
                List<AvailabilityStatus> statusList = person.getAvailabilityStatusList(this.date, timeSlotStart, timeSlotEnd);
                availabilityStatusListWithPersons.put(person, statusList);
            }

            possibilities.add(new CommonTimeSlot(timeSlotStart, timeSlotEnd, availabilityStatusListWithPersons, compulsoryPersons));
        }

        // Sort possibilities by impossibility factor
        possibilities.sort(new CommonTimeSlotComparator());

        if (limit != null) {
            // Fixing limit when there are less possibilities than the limit
            limit = (possibilities.size() < limit) ? possibilities.size() : limit;
            return possibilities.subList(0, limit);
        }

        return possibilities;
    }

    /**
     * @see #getCommonTimeSlots(Integer, Integer, List<Person>)
     */
    public List<CommonTimeSlot> getCommonTimeSlots(Integer timeSlotDuration, Integer limit) {
        return this.getCommonTimeSlots(timeSlotDuration, limit, null);
    }

    /**
     * @see #getCommonTimeSlots(Integer, Integer, List<Person>)
     */
    public List<CommonTimeSlot> getCommonTimeSlots(Integer timeSlotDuration, List<Person> compulsoryPersons) {
        return this.getCommonTimeSlots(timeSlotDuration, null, compulsoryPersons);
    }

    /**
     * @see #getCommonTimeSlots(Integer, Integer)
     */
    public List<CommonTimeSlot> getCommonTimeSlots(Integer timeSlotDuration) {
        return this.getCommonTimeSlots(timeSlotDuration, null, null);
    }
}
