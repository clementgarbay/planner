package fr.clementgarbay.planner.planning;

import java.time.Duration;
import java.time.LocalTime;

/**
 * Represents a time slot.
 */
public class TimeSlot {
    private LocalTime timeStart;
    private LocalTime timeEnd;

    /**
     * Constructor
     * @param timeStart the start time of the time slot
     * @param timeEnd   the end time of the time slot
     */
    public TimeSlot(LocalTime timeStart, LocalTime timeEnd) {
        if (timeStart.compareTo(timeEnd) >= 0)
            throw new IllegalArgumentException("The start time should be lower than the end time.");

        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    /**
     * Get the start time of the time slot.
     * @return  the start LocalTime object
     */
    public LocalTime getTimeStart() {
        return this.timeStart;
    }

    /**
     * Get the end time of the time slot.
     * @return  the end LocalTime object
     */
    public LocalTime getTimeEnd() {
        return this.timeEnd;
    }

    /**
     * Get the duration of the time slot.
     * @return  the corresponding Duration object
     */
    public Duration getDuration() {
        return Duration.between(this.timeStart, this.timeEnd);
    }

    /**
     * Compute the number of minutes between two LocalTime.
     * @param start     the start time
     * @param end       the end time
     * @return          the number of minutes
     */
    public static long getMinutesBetween(LocalTime start, LocalTime end) {
        Duration duration = Duration.between(start, end);
        return duration.toMinutes();
    }
}
