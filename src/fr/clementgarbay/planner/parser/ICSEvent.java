package fr.clementgarbay.planner.parser;

import fr.clementgarbay.planner.planning.Priority;
import fr.clementgarbay.planner.planning.TimeSlot;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents a event of ICS format.
 */
public class ICSEvent extends TimeSlot {
    private LocalDate date;
    private Priority priority;

    /**
     * Constructor
     * @param date      the date of the event
     * @param timeStart the start time of the event
     * @param timeEnd   the end time of the event
     * @param priority  the priority of the event
     */
    public ICSEvent(LocalDate date, LocalTime timeStart, LocalTime timeEnd, Priority priority) {
        super(timeStart, timeEnd);
        this.date = date;
        this.priority = priority;
    }

    /**
     * Get the date of the event.
     * @return  the priority
     */
    public LocalDate getDate() {
        return this.date;
    }

    /**
     * Get the priority of the event.
     * @return  the priority
     */
    public Priority getPriority() {
        return this.priority;
    }
}
