package fr.clementgarbay.planner.planning;

import fr.clementgarbay.planner.planning.status.AvailabilityStatus;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents a common time slot between multiple persons with a impossibility factor.
 * More this factor is higher, the less people are available at the same time (or have already an event with a high priority level). So, if the status is equal to 0, then all of people are available.
 */
public class CommonTimeSlot extends TimeSlot implements Comparable {

    private int impossibilityFactor;
    private List<CommonTimeSlotPerson> persons;

    /**
     * Constructor
     * @param timeStart                         the start time of the time slot
     * @param timeEnd                           the end time of the time slot
     * @param availabilityStatusListWithPersons the persons with their associated availability status list
     * @param compulsoryPersons                 the compulsory persons for the time slot
     */
    public CommonTimeSlot(LocalTime timeStart, LocalTime timeEnd, Map<Person, List<AvailabilityStatus>> availabilityStatusListWithPersons, List<Person> compulsoryPersons) {
        super(timeStart, timeEnd);

        this.impossibilityFactor = 0;
        this.persons = new ArrayList<>();

        // For each person in the common time slot, create a corresponding CommonTimeSlotPerson object
        for (Map.Entry<Person, List<AvailabilityStatus>> availabilityStatusListWithPerson : availabilityStatusListWithPersons.entrySet()) {

            Person currentPerson = availabilityStatusListWithPerson.getKey();
            List<AvailabilityStatus> currentAvailabilityStatusList = availabilityStatusListWithPerson.getValue();
            boolean personIsCompulsory = (compulsoryPersons != null) && compulsoryPersons.contains(availabilityStatusListWithPerson.getKey());

            // Create a new common time slot person object
            CommonTimeSlotPerson commonTimeSlotPerson = new CommonTimeSlotPerson(currentPerson.getName(), personIsCompulsory, currentAvailabilityStatusList);
            // Add it in the persons collection of this common time slots
            this.persons.add(commonTimeSlotPerson);
        }

        int penaltyWeight = (int)super.getDuration().toMinutes() / Planner.TIME_INTERVAL * Priority.MAX * this.persons.size() * 2;

        // Compute the impossibility factor by summing the total unavailability factor of each person of the time slot.
        // Is the person is compulsory multiply his total unavailability factor by a penalty weight to move down the common time slot in the results
        this.impossibilityFactor = this.persons.stream().mapToInt(p -> p.getTotalUnavailabilityFactor() * (p.isCompulsory() ? penaltyWeight : 1)).sum();
    }

    /**
     * Compute the impossibility factor (using an other method more complex).
     */
    @Deprecated
    private void computeImpossibilityFactor() {
        // If the common time slot contains a compulsory person and his total unavailability factor is equals to 0 then make a "simple" calculation
        boolean simple = this.persons.stream().filter(p -> p.getTotalUnavailabilityFactor() == 0 && p.isCompulsory()).count() > 0;

        // Increment the global impossibility factor with the total unavailability factor of each persons, weighted with the obligation of the persons
        for (CommonTimeSlotPerson commonTimeSlotPerson: this.persons) {
            if (simple) {
                // To trace back the common time slot in the results
                this.impossibilityFactor += commonTimeSlotPerson.getTotalUnavailabilityFactor() / 2;
            } else {
                // If the current person is compulsory then multiply his total unavailability factor by the total number of persons present in this common time slots +1 and if the person is not compulsory multiply his total unavailability factor by the total number of persons present in this common time slots.
                this.impossibilityFactor += commonTimeSlotPerson.getTotalUnavailabilityFactor() * ((commonTimeSlotPerson.isCompulsory()) ? this.persons.size()+1 : this.persons.size());
            }
        }
    }

    /**
     * Get a list of the names of all the persons concerned by this time slot.
     * @return  a list of names
     */
    public List<String> getPersonsNames() {
        return this.persons.stream().map(CommonTimeSlotPerson::getName).collect(Collectors.toList());
    }

    /**
     * Get a list of the persons concerned by this time slot.
     * @return  a list of CommonTimeSlotPerson objects
     */
    public List<CommonTimeSlotPerson> getPersons() {
        return this.persons;
    }

    /**
     * Get the global impossibility factor of this common time slot.
     * @return  the impossibility global factor
     */
    public int getImpossibilityFactor() {
        return this.impossibilityFactor;
    }

    /**
     * Check if there are conflicts between persons in this common time slots.
     * @return  true if two or more persons are in conflict, false otherwise
     */
    public boolean hasConflicts() {
        return this.getImpossibilityFactor() > 0;
    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof TimeSlot)) throw new IllegalArgumentException();
        CommonTimeSlot ts = (CommonTimeSlot)o;
        return this.getImpossibilityFactor() - ts.getImpossibilityFactor();
    }

    @Override
    public String toString() {
        return  "[start: " + this.getTimeStart() +
                " - end: " + this.getTimeEnd() +
                " , conflicts : " + this.hasConflicts() +
                " , details : " + this.getPersons() +
                "] \n";
    }
}
