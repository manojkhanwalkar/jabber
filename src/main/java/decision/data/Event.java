package decision.data;

import java.util.HashMap;
import java.util.Map;

public class Event {

    HashMap<String,String> attributes = new HashMap<>();

    public HashMap<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(HashMap<String, String> attributes) {
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
