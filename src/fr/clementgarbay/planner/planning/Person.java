package fr.clementgarbay.planner.planning;

import fr.clementgarbay.planner.parser.ICSEvent;
import fr.clementgarbay.planner.planning.status.AvailabilityStatus;
import fr.clementgarbay.planner.planning.status.Available;
import fr.clementgarbay.planner.planning.status.Unavailable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Represents a person.
 */
public class Person {
    private String name;
    private List<ICSEvent> events;
    private Map<LocalDate, Map<LocalTime, AvailabilityStatus>> planning;

    /**
     * Constructor
     * @param name      the name of the person
     * @param events    a list of ICSEvent objects corresponding to the events of the person
     */
    public Person(String name, List<ICSEvent> events) {
        this.name = name;
        this.events = events;
        this.planning = new TreeMap<>();

        this.computePlanningFromEvents();
    }

    /**
     * Compute the unavailability planning of the person depending to these scheduled events.
     * In fact, this method constructs a dictionary that associates each day occupied to another dictionary that stores hours occupied and the unavailability related status.
     * <p>
     * Note: This private method is called by the constructor.
     * </p>
     */
    private void computePlanningFromEvents() {
        for (ICSEvent event : this.events) {
            long minutes = TimeSlot.getMinutesBetween(event.getTimeStart(), event.getTimeEnd());

            for (int i = 0; i < minutes; i += Planner.TIME_INTERVAL) {
                LocalTime timeSlotStart = event.getTimeStart().plusMinutes(i);
                LocalDate slotDate = LocalDate.of(event.getDate().getYear(), event.getDate().getMonth(), event.getDate().getDayOfMonth());
                LocalTime slotTime = LocalTime.of(timeSlotStart.getHour(), timeSlotStart.getMinute());

                Map<LocalTime, AvailabilityStatus> planningOfTheDate = this.planning.get(slotDate);
                if (planningOfTheDate == null) planningOfTheDate = new TreeMap<>();

                planningOfTheDate.put(slotTime, new Unavailable(event.getPriority().value));
                this.planning.put(slotDate, planningOfTheDate);
            }
        }
    }

    /**
     * Get the name of the person.
     * @return  the name of the person
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the list to the scheduled events of the person.
     * @return  a list of ICSEvent objects
     */
    public List<ICSEvent> getEvents() {
        return this.events;
    }

    /**
     * Get the availability status of the person for the slot (of the duration determined by the time interval of the planner) starting at a determinate datetime.
     * @param dateTime  the start datetime of slot
     * @return          the unavailability status
     */
    public AvailabilityStatus getAvailabilityStatus(LocalDateTime dateTime) {
        LocalDate slotDate = LocalDate.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth());
        LocalTime slotTime = LocalTime.of(dateTime.getHour(), dateTime.getMinute());

        return (this.planning.get(slotDate) != null && this.planning.get(slotDate).get(slotTime) != null) ? this.planning.get(slotDate).get(slotTime) : Available.getInstance();
    }

    /**
     * Get a list of availability status of the person for each slots (of the duration determined by the time interval of the planner) from a time to another time for a specific date.
     * @param date      the concerned date
     * @param timeStart the start time
     * @param timeEnd   the end time
     * @return          the list of availability status for each slots of the duration determined by the time interval of the planner, sorted chronologically
     */
    public List<AvailabilityStatus> getAvailabilityStatusList(LocalDate date, LocalTime timeStart, LocalTime timeEnd) {
        long minutes = TimeSlot.getMinutesBetween(timeStart, timeEnd);
        List<AvailabilityStatus> availabilityStatusList = new ArrayList<>();

        for (int i = 0; i < minutes; i += Planner.TIME_INTERVAL) {
            LocalTime time = timeStart.plusMinutes(i);
            LocalDateTime dateTime = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), time.getHour(), time.getMinute());
            availabilityStatusList.add(this.getAvailabilityStatus(dateTime));
        }

        return availabilityStatusList;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
