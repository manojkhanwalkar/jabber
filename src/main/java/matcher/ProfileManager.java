package matcher;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ProfileManager {

    ConcurrentMap<Integer, Set<Integer>> matches = new ConcurrentHashMap<>();

    ConcurrentMap<Integer,Profile> profiles = new ConcurrentHashMap<>();

    public void add(Profile profile)
    {
        profiles.put(profile.getId(),profile);

    }


    //TODO - needs synch if updated by multiple threads.
    public synchronized void add(Profile curr, Profile other)
    {
        if (curr.equals(other))
            return;


        Set<Integer> ids = matches.computeIfAbsent(curr.getId(), k->new HashSet<>());
        ids.add(other.getId());


        ids = matches.computeIfAbsent(other.getId(), k->new HashSet<>());
        ids.add(curr.getId());
    }

}
