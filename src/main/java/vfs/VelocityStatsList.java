package vfs;

import java.util.HashMap;

public class VelocityStatsList {

   HashMap<String,VelocityStats> stats = new HashMap<>();

    public HashMap<String, VelocityStats> getStats() {
        return stats;
    }

    public void setStats(HashMap<String, VelocityStats> stats) {
        this.stats = stats;
    }

    public void addStats(String name, VelocityStats vs)
    {
        stats.put(name,vs);
    }

    @Override
    public String toString() {
        return "VelocityStatsList{" +
                "stats=" + stats +
                '}';
    }
}
