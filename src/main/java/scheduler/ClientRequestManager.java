package scheduler;

import data.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static util.JarUtils.getJarFromBase64EncodedString;
import static util.JarUtils.loadClassFromJar;

public class ClientRequestManager {

    JobCache jobcache = new JobCache();

    JobManager jobManager = new JobManager();

    public SubmitResponse submit(SubmitRequest submitRequest)
    {

        String jobId = UUID.randomUUID().toString();
        SubmitResponse response = new SubmitResponse(submitRequest.getClient(),jobId);

        jobcache.add(submitRequest.getClient(), jobId, Status.Submitted );


        jobManager.submit(submitRequest,jobId,jobcache);

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
}
