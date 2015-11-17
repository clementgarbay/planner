package fr.clementgarbay.planner;

import fr.clementgarbay.planner.utils.Utils;
import fr.clementgarbay.planner.planning.CommonTimeSlot;
import fr.clementgarbay.planner.planning.Person;
import fr.clementgarbay.planner.planning.Planner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        ///----- DEMO ----///

        // Get the concerned persons from test folder (which contains ICS files)
        List<Person> persons = Utils.filesToPersons("data/test/");
        // Create a planner for the 05/11/2015 from 9:00 to 7:00 p.m. with this persons
        Planner planner = new Planner(LocalDate.of(2015, 11, 5), LocalTime.of(9, 0), LocalTime.of(19, 0), persons);
        // Get the 15 first common time slots with a duration of 1 hour
        List<CommonTimeSlot> commonTimeSlots = planner.getCommonTimeSlots(60, 20, persons.subList(1,3));

        System.out.println(commonTimeSlots);
    }
}
