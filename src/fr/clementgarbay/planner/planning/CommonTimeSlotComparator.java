package fr.clementgarbay.planner.planning;

import java.util.Comparator;

/**
 * A comparator of CommonTimeSlot objects.
 * The CommonTimeSlot objects are compared by their impossibility status.
 */
public class CommonTimeSlotComparator implements Comparator<CommonTimeSlot> {
    @Override
    public int compare(CommonTimeSlot p1, CommonTimeSlot p2) {
        return p1.getImpossibilityFactor() - p2.getImpossibilityFactor();
    }
}