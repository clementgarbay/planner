package fr.clementgarbay.planner.parser;

import fr.clementgarbay.planner.planning.Priority;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ICSParser {

    /**
     * Parse a ISC file for getting events included.
     * @param filepath      the path of the file to parse
     * @return              a list of ICSEvent objects of events parsed in the file
     * @throws IOException  an IO exception
     */
    public static List<ICSEvent> parseFile(String filepath) throws IOException {
        List<ICSEvent> icsEvents = new ArrayList<>();
        String content = new String(Files.readAllBytes(Paths.get(filepath)));

        String[] events = content.split("BEGIN:VEVENT");

        // Starting at 1 for ignoring the informations of the calendar
        for (int i = 1; i < events.length; i++) {
            String tmp = events[i];

            ICSProperty dtStart = new ICSProperty(ICSParser.getPropertyLine(tmp, "DTSTART"));
            ICSProperty dtEnd = new ICSProperty(ICSParser.getPropertyLine(tmp, "DTEND"));

            // Get the priority in the summary (PRIORITY-[summary text] ie. "P1-The summary" or "P2-Lorem ipsum")
            Priority priority;
            try {
                ICSProperty summary = new ICSProperty(ICSParser.getPropertyLine(tmp, "SUMMARY"));
                String priorityStr = summary.getValue().split("-")[0];
                priority = Priority.valueOf(priorityStr);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Unknown priority in summary property.");
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss");
            icsEvents.add(new ICSEvent(LocalDate.parse(dtStart.getValue(), formatter), LocalTime.parse(dtStart.getValue(), formatter), LocalTime.parse(dtEnd.getValue(), formatter), priority));
        }

        return icsEvents;
    }

    /**
     * Parse a ISC file for getting events included.
     * @param file          the file object to parse
     * @return              a list of ICSEvent objects of events parsed in the file
     * @throws IOException  an IO exception
     */
    public static List<ICSEvent> parseFile(File file) throws IOException {
        return ICSParser.parseFile(file.getAbsolutePath());
    }

    /**
     * Get a specific property line (string line corresponding) in icsSubContent which matches the key
     * @param icsSubContent the ICS content where find the property
     * @param key           the key of the property to find
     * @return              the first line corresponding to the property, or null if none property matches the key
     */
    private static String getPropertyLine(String icsSubContent, String key) {
        Matcher m = Pattern.compile("(?m)" + key + "(.*):(.*)").matcher(icsSubContent);
        if (m.find()) {
            return m.group();
        }
        return null;
    }
}