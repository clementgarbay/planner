package fr.clementgarbay.planner.utils;

import org.junit.Test;

import fr.clementgarbay.planner.planning.Person;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class UtilsTest {

    @Test
    public void testFilesToPersons() throws Exception {
        List<Person> persons = Utils.filesToPersons("data/test/");

        assertEquals(persons.size(), 4);
    }
}