package fr.clementgarbay.planner.planning;

import org.junit.Before;
import org.junit.Test;

import fr.clementgarbay.planner.parser.ICSParser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PlannerTest {

    private List<Person> persons;

    @Before
    public void setUp() throws Exception {
        Person person1 = new Person("test1", ICSParser.parseFile("data/test/test11.ics"));
        Person person2 = new Person("test2", ICSParser.parseFile("data/test/test22.ics"));

        this.persons = new ArrayList<>();
        persons.add(person1);
        persons.add(person2);
    }

    @Test
    public void testGetCommunTimeSlot() throws Exception {
        Planner planner = new Planner(LocalDate.of(2015, 11, 5), LocalTime.of(9, 0), LocalTime.of(20, 0), this.persons);

        assertEquals(planner.getCommonTimeSlots(60).size(), 41);
    }

    @Test
    public void testGetCommunTimeSlot2() throws Exception {
        Planner planner = new Planner(LocalDate.of(2010, 11, 5), LocalTime.of(12, 0), LocalTime.of(20, 0), this.persons);

        assertEquals(planner.getCommonTimeSlots(60).size(), 29);
    }

    @Test
    public void testGetCommunTimeSlot3() throws Exception {
        Planner planner = new Planner(LocalDate.of(2015, 11, 5), LocalTime.of(9, 0), LocalTime.of(20, 0), this.persons);

        assertEquals(planner.getCommonTimeSlots(45).size(), 42);
    }

    @Test
    public void testGetCommunTimeSlot4() throws Exception {
        Planner planner = new Planner(LocalDate.of(2015, 11, 5), LocalTime.of(9, 0), LocalTime.of(20, 0), this.persons);

        assertEquals(planner.getCommonTimeSlots(45, 10).size(), 10);
    }

    @Test
    public void testGetCommunTimeSlot5() throws Exception {
        Planner planner = new Planner(LocalDate.of(2015, 11, 5), LocalTime.of(9, 0), LocalTime.of(20, 0), this.persons);

        List<CommonTimeSlot> commonTimeSlots = planner.getCommonTimeSlots(45, persons.subList(0, 1));
        Object[] commonTimeSlotsWithImpossibilityFactorEqualsTo9 = commonTimeSlots.stream().filter(i -> i.getImpossibilityFactor() == 9).toArray();

        assertEquals(commonTimeSlotsWithImpossibilityFactorEqualsTo9.length, 6);
    }
}