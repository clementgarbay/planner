package fr.clementgarbay.planner.planning.status;

import fr.clementgarbay.planner.planning.Priority;

/**
 * Represents the unavailability of a person. <br>
 * It used to represent busy time slot.
 */
public class Unavailable implements AvailabilityStatus {

    private int unavailabilityFactor;

    /**
     * Constructor
     *
     * @param unavailabilityFactor  the unavailability factor number
     */
    public Unavailable(Integer unavailabilityFactor) {
        this.unavailabilityFactor = unavailabilityFactor;
    }

    @Override
    public int getUnavailabilityFactor() {
        return this.unavailabilityFactor;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AvailabilityStatus)) return false;
        AvailabilityStatus availabilityStatus = (AvailabilityStatus) obj;
        return this.getUnavailabilityFactor() == availabilityStatus.getUnavailabilityFactor();
    }

    @Override
    public String toString() {
        return "Unavailable(" + Priority.getTitle(this.getUnavailabilityFactor()) + ")";
    }
}
