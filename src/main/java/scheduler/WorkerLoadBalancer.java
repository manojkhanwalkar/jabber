package scheduler;

import util.Connection;

import java.util.ArrayDeque;
import java.util.Queue;

public class WorkerLoadBalancer {

    static class Holder
    {

        static WorkerLoadBalancer balancer = new WorkerLoadBalancer();
    }


    public static WorkerLoadBalancer getInstance()
    {
        return Holder.balancer;
    }

    private WorkerLoadBalancer()
    {

    }

    Queue<String> workers = new ArrayDeque<>();

    public synchronized void register(String url)
    {

        //     Connection app = new Connection("https://localhost:8380/");

        workers.add(url);

    }

    public synchronized void deregister(String url)
    {

        //     Connection app = new Connection("https://localhost:8380/");

        workers.remove(url);

    }

    public synchronized Connection getNextWorker()
    {
        if (!workers.isEmpty()) {
            String url = workers.remove();
            workers.add(url);
            Connection worker = new Connection(url);

            return worker;
        }
        else
        {
            return null;
        }

    }



}
