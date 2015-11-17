package fr.clementgarbay.planner.planning;

import fr.clementgarbay.planner.planning.status.AvailabilityStatus;
import fr.clementgarbay.planner.planning.status.Available;

import java.util.List;

/**
 * Represents a person in a common time slot. <br>
 * <p>
 * Note: This object contains a list of availability status which represents the availability of the person for each sub slots (of the duration determined by the time interval of the planner) of the concerned common time slot.
 * </p>
 */
public class CommonTimeSlotPerson {

    private String name;
    private boolean isCompulsory;
    private List<AvailabilityStatus> availabilityStatusList;
    private int totalUnavailabilityFactor;

    /**
     * Constructor
     * @param name                      the name of the person
     * @param isCompulsory              indicate if this person is compulsory or not for the concerned time slot
     * @param availabilityStatusList    the availability status list for the person in the concerned time slot
     */
    public CommonTimeSlotPerson(String name, boolean isCompulsory, List<AvailabilityStatus> availabilityStatusList) {
        this.name = name;
        this.isCompulsory = isCompulsory;
        this.availabilityStatusList = availabilityStatusList;

        // Compute the total unavailability factor of the person for the concerned time slot by summing the unavailability factor of each items in availability status list
        this.totalUnavailabilityFactor = availabilityStatusList.stream().mapToInt(AvailabilityStatus::getUnavailabilityFactor).sum();
    }

    /**
     * Get the name of the common time slot person.
     * @return  the name of the person
     */
    public String getName() {
        return this.name;
    }

    /**
     * Check if the common time slot person is compulsory.
     * @return  true if the person is compulsory for the concerned time slot, false otherwise
     */
    public boolean isCompulsory() {
        return this.isCompulsory;
    }

    /**
     * Check if the person is available in the concerned common time slot.
     * @return  true if the person is available, false otherwise
     */
    public boolean isAvailable() {
        return this.availabilityStatusList.stream().filter(s -> !s.equals(Available.getInstance())).toArray().length == 0;
    }

    /**
     * Get a list of associate availability status for the person in the concerned time slot.
     * For example, if the list is [Available,Available,Unavailable(Préférable)] then this person will be available the first two slots of this common time slot, but will have a "Préférable" priority event the last slot.
     * @return  a list
     */
    public List<AvailabilityStatus> getAvailabilityStatusList() {
        return availabilityStatusList;
    }

    /**
     * Get the total unavailability factor for the person for the concerned time slot.
     * @return  the total unavailability factor
     */
    public int getTotalUnavailabilityFactor() {
        return totalUnavailabilityFactor;
    }

    @Override
    public String toString() {
        return "{" + this.getName() + (this.isCompulsory() ? " (Compulsory)" : "") + " : " + this.getAvailabilityStatusList() + "}";
    }
}
