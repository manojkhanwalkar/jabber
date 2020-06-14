package dynamodb2;

import java.util.ArrayList;
import java.util.List;

public class CacheProperties {
    String cacheName;

    List<String> uniqueKeys = new ArrayList<>();

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public List<String> getUniqueKeys() {
        return uniqueKeys;
    }

    public void setUniqueKeys(List<String> uniqueKeys) {
        this.uniqueKeys = uniqueKeys;
    }

    public void addUniqueKey(String uniqueKey)
    {
        uniqueKeys.add(uniqueKey);
    }
}
