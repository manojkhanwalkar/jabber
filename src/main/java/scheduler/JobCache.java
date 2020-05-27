package scheduler;

import com.amazonaws.services.dynamodbv2.xspec.S;
import data.Result;
import data.Status;
import data.StatusTuple;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

public class JobCache {

    CompletedJobEvictor completedJobEvictor ;

    public JobCache()
    {
        completedJobEvictor = new CompletedJobEvictor(this);
    }


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

            StatusTuple st = new StatusTuple(jobId,status, result);
            list.add(st);
            completedJobEvictor.add(st,clientId);
        }
        else
        {
            System.out.println("Not found "+ clientId + " " + jobId + " " + status);
        }

    //    System.out.println(map);
    }


    public synchronized void remove(StatusTuple statusTuple,String clientId)
    {
        Set<StatusTuple> list = map.get(clientId);
        list.remove(statusTuple);

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

    @Override
    public String toString() {
        return "JobCache{" +
                "map=" + map +
                '}';
    }
}
