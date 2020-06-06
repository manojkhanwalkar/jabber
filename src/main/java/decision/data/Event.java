package decision.data;

import java.util.HashMap;
import java.util.Map;

public class Event {

    Map<String,String> attributes = new HashMap<>();

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public String get(String key)
    {
        return attributes.get(key);
    }

    public void put(String key, String value)
    {
        attributes.put(key,value);
    }

    public Event() {
    }
}
