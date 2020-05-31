package chatter.server;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class UserManager {


    ConcurrentMap<String,Sender> users = new ConcurrentHashMap<>();

    public void add(String user, Sender sender)
    {

        users.put(user,sender);
    }

    public void remove(String user)
    {
        users.remove(user);
    }


    public Sender get(String user)
    {
        return users.get(user);
    }

}
