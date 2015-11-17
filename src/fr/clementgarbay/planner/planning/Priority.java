package fr.clementgarbay.planner.planning;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumerate the priority possibilities of an event.
 */
public enum Priority {
    P1 ("Obligatoire", 3),
    P2 ("Préférable", 2),
    P3 ("Informatif", 1);

    public String title;
    public int value;
    public static int MAX = 0;

    private static final Map<Integer,String> map;
    static {
        map = new HashMap<>();
        for (Priority priority : Priority.values()) {
            if (priority.value > MAX) MAX = priority.value;
            map.put(priority.value, priority.title);
        }
    }

    /**
     * Constructor
     * @param title the title of the priority
     * @param value the value of priority
     */
    Priority(String title, int value) {
        this.title = title;
        this.value = value;
    }

    /**
     * Get the title of a specific priority value.
     * @param value the concerned priority value
     * @return      the corresponding title
     */
    public static String getTitle(int value) {
        return map.get(value);
    }
}
