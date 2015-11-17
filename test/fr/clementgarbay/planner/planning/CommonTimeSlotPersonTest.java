package fr.clementgarbay.planner.planning;

import org.junit.Before;
import org.junit.Test;

import fr.clementgarbay.planner.planning.status.AvailabilityStatus;
import fr.clementgarbay.planner.planning.status.Available;
import fr.clementgarbay.planner.planning.status.Unavailable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CommonTimeSlotPersonTest {


    CommonTimeSlotPerson person;

    @Before
    public void setUp() throws Exception {
        List<AvailabilityStatus> availabilityStatusList = new ArrayList<>();
        availabilityStatusList.add(Available.getInstance());
        availabilityStatusList.add(Available.getInstance());
        availabilityStatusList.add(new Unavailable(2));
        this.person = new CommonTimeSlotPerson("Alfred", true, availabilityStatusList);
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals(this.person.getName(), "Alfred");
    }

    @Test
    public void testIsCompulsory() throws Exception {
        assertEquals(this.person.isCompulsory(), true);
    }

    @Test
    public void testIsAvailable() throws Exception {
        assertEquals(this.person.isAvailable(), false);
    }

    @Test
    public void testGetTotalUnavailabilityFactor() throws Exception {
        assertEquals(this.person.getTotalUnavailabilityFactor(), 2);
    }
}