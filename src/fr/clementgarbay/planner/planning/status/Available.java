package fr.clementgarbay.planner.planning.status;

/**
 * Represents the availability of a person. <br>
 * It used to represent free time slot.
 * <p>
 * Note: It uses the singleton pattern.
 * </p>
 */
public class Available implements AvailabilityStatus {

    private static Available instance = new Available();

    private Available() {}

    public static Available getInstance() {
        return instance;
    }

    @Override
    public int getUnavailabilityFactor() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AvailabilityStatus)) return false;
        AvailabilityStatus availabilityStatus = (AvailabilityStatus) obj;
        return this.getUnavailabilityFactor() == availabilityStatus.getUnavailabilityFactor();
    }

    @Override
    public String toString() {
        return "Available";
    }
}
