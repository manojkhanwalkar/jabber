package scheduler;

import data.*;
import jdk.jshell.Snippet;
import scheduler.persistence.FilePersistenceManager;
import scheduler.persistence.Record;
import util.JSONUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static util.JarUtils.getJarFromBase64EncodedString;
import static util.JarUtils.loadClassFromJar;

public class ClientRequestManager {

    JobCache jobcache ;

    JobManager jobManager ;

    FilePersistenceManager  filePerisistenceManager = new FilePersistenceManager("/home/manoj/data/jabber/cache/");


    public ClientRequestManager()
    {
        jobcache = new JobCache(filePerisistenceManager);
        jobManager = new JobManager(jobcache);

        restore();
    }

    private void restore()
    {
        Iterator<Record>  iterator = filePerisistenceManager.restore();

        Map<String,SubmitRequest> tmpCache = new HashMap<>();

        while(iterator.hasNext())
        {
            Record record = iterator.next();

            if (record!=null) {
             //   System.out.println(record.getAction() + " " + record.getClientId() + " " + record.getJobId());
                if (record.getAction()== Record.Action.Add)
                {
                    SubmitRequest submitRequest = (SubmitRequest) JSONUtil.fromJSON(record.getData(),SubmitRequest.class);
                    jobcache.add(submitRequest.getClient(), record.getJobId(), Status.Submitted );
                    tmpCache.put(record.getJobId(),submitRequest);

                }
                else {
                    Result result = (Result) JSONUtil.fromJSON(record.getData(),Result.class);
                    jobcache.update(record.getClientId(),record.getJobId(), Status.Complete,result);

                }

            }

        }

     /*   jobcache.map.entrySet().stream().forEach(entry->{

            entry.getValue().stream().filter(st->st.getResult()==null).forEach(st-> {
                jobManager.submit(tmpCache.get((st.getId())),st.getId());

                System.out.println("Resubmitting job on recovery " + st.getId());
            });
        });
        */


    /*    List<String> jobIds = new ArrayList<>();

        jobcache.map.entrySet().stream().forEach(entry->{

            entry.getValue().stream().filter(st->st.getResult()==null).forEach(st-> {

                jobIds.add(st.getId());
                System.out.println("Resubmitting job on recovery " + st.getId());
            });
        }); */



        List<StatusTuple> list = jobcache.map.entrySet().stream().flatMap(entry->entry.getValue().stream()).filter(st->st.getResult()==null).collect(Collectors.toList());

        list.stream().map(st->st.getId()).forEach(id-> jobManager.submit(tmpCache.get(id),id));






        filePerisistenceManager.init();
    }

    public SubmitResponse submit(SubmitRequest submitRequest)
    {

        String jobId = UUID.randomUUID().toString();
        SubmitResponse response = new SubmitResponse(submitRequest.getClient(),jobId);

        filePerisistenceManager.persist(Record.makeRecord(submitRequest,jobId));

        jobcache.add(submitRequest.getClient(), jobId, Status.Submitted );


        jobManager.submit(submitRequest,jobId);

        return response;

    }




    public StatusResponse status(StatusRequest request) {

        StatusResponse response = new StatusResponse();

        request.getJobIds().forEach(id->{

            StatusTuple tuple = jobcache.get(request.getClient(),id );

            response.addStatus(tuple);

        });

        return response;
    }


    public void stop()
    {
        System.out.println(jobcache);
        filePerisistenceManager.stop();


    }

    public String response(WorkerResponse response) {

        jobManager.processResponse(response);
        return "Received";
    }
}
