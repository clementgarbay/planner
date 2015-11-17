package fr.clementgarbay.planner.parser;

/**
 * Represents a property of ICS format.
 */
public class ICSProperty {
    private String key;
    private String value;

    /**
     * Constructor
     * @param key   the key property
     * @param value the value property
     */
    public ICSProperty(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Constructor
     * @param property  the full property (with key:value)
     */
    public ICSProperty(String property) {
        this(property.split(":")[0], property.split(":")[1]);
    }

    /**
     * Get the key of the property.
     * @return  the key
     */
    public String getKey() {
        return this.key;
    }

    /**
     * Get the value of the property.
     * @return  the value
     */
    public String getValue() {
        return this.value;
    }
}
