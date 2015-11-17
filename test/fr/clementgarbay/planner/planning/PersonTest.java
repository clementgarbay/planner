package fr.clementgarbay.planner.planning;

import org.junit.Before;
import org.junit.Test;

import fr.clementgarbay.planner.parser.ICSParser;
import fr.clementgarbay.planner.planning.status.AvailabilityStatus;
import fr.clementgarbay.planner.planning.status.Available;
import fr.clementgarbay.planner.planning.status.Unavailable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PersonTest {

    Person person;

    @Before
    public void setUp() throws Exception {
        this.person = new Person("test1", ICSParser.parseFile("data/test/test11.ics"));
    }

    @Test
    public void testGetAvailabilityStatus() throws Exception {
        assertEquals(this.person.getAvailabilityStatus(LocalDateTime.of(2015, 11, 5, 12, 0)), new Unavailable(2));
    }

    @Test
    public void testGetAvailabilityStatus1() throws Exception {
        assertEquals(this.person.getAvailabilityStatus(LocalDateTime.of(2015, 11, 5, 17, 0)), new Unavailable(3));
    }

    @Test
    public void testGetAvailabilityStatus2() throws Exception {
        assertEquals(this.person.getAvailabilityStatus(LocalDateTime.of(2015, 10, 5, 9, 0)), Available.getInstance());
    }

    @Test
    public void testGetAvailabilityStatusList() throws Exception {
        List<AvailabilityStatus> availabilityStatusList = this.person.getAvailabilityStatusList(LocalDate.of(2015, 11, 5), LocalTime.of(11, 30), LocalTime.of(12, 15));
        List<AvailabilityStatus> res = new ArrayList<>();
        res.add(Available.getInstance());
        res.add(Available.getInstance());
        res.add(new Unavailable(2));

        assertEquals(availabilityStatusList, res);
    }
}