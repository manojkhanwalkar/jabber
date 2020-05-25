package scheduler;

import com.amazonaws.services.dynamodbv2.xspec.S;
import data.Result;
import data.Status;
import data.StatusTuple;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

public class JobCache {


    Map<String, Set<StatusTuple>>  map = new HashMap<>();

    public synchronized  void add(String clientId , String jobId, Status status)
    {

        Set<StatusTuple> list = map.computeIfAbsent(clientId,(k)->{

            return new HashSet<>();

        });

        list.add(new StatusTuple(jobId,status));
    }

    public synchronized  void update(String clientId , String jobId, Status status)
    {
        Set<StatusTuple> list = map.get(clientId);

        if (list!=null)
        {
            list.remove(new StatusTuple(jobId));
            list.add(new StatusTuple(jobId,status));
        }
        else
        {
            System.out.println("Not found "+ clientId + " " + jobId + " " + status);
        }
    }

    public synchronized  void update(String clientId , String jobId, Status status, Result result)
    {
        Set<StatusTuple> list = map.get(clientId);

        if (list!=null)
        {
            list.remove(new StatusTuple(jobId));
            list.add(new StatusTuple(jobId,status, result));
        }
        else
        {
            System.out.println("Not found "+ clientId + " " + jobId + " " + status);
        }

        System.out.println(map);
    }




    public synchronized StatusTuple get(String clientId, String jobId)
    {
        Set<StatusTuple> tuples = map.get(clientId);

        if (tuples!=null) {
            Optional<StatusTuple>tuple= tuples.stream().filter(t -> t.getId().equals(jobId)).findFirst();

            if (tuple.isPresent())
                return tuple.get();
        }

        return new StatusTuple(jobId,Status.NotFound);
    }


}
