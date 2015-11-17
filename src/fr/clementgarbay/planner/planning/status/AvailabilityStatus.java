package fr.clementgarbay.planner.planning.status;

/**
 * Defines compulsory method of availability status objects.
 */
public interface AvailabilityStatus {

    /**
     * Get the unavailability factor of this availability status.
     * @return  a integer which represents the unavailability factor. Ie. positive if it is a unavailable time slot, zero if it is a free time slot.
     */
    int getUnavailabilityFactor();

}
