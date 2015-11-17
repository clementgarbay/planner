package fr.clementgarbay.planner.utils;

import fr.clementgarbay.planner.parser.ICSParser;
import fr.clementgarbay.planner.planning.Person;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {

    /**
     * Get a list of person objects from a directory of ICS files.
     * @param directoryPath the concerned directory
     * @return              a list of person objects (the name of the person is the name of the file)
     */
    public static List<Person> filesToPersons(String directoryPath) {

        List<Person> persons = new ArrayList<>();

        try (Stream<Path> pathStream = Files.walk(Paths.get(directoryPath))) {
            pathStream
                    .filter(Files::isRegularFile)
                    .filter(path -> !path.toFile().isDirectory())
                    .filter(path -> path.toFile().getAbsolutePath().endsWith("ics"))
                    .collect(Collectors.toList())
                    .forEach(path -> {
                        try {
                            persons.add(new Person(path.toFile().getName(), ICSParser.parseFile(path.toFile())));
                        } catch (IOException e) {
                            System.err.println(e.getMessage());
                        }
                    });
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return persons;
    }
}
