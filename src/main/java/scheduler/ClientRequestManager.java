package scheduler;

import data.*;

import java.util.UUID;

public class ClientRequestManager {

    JobCache jobcache = new JobCache();

    public SubmitResponse submit(SubmitRequest submitRequest)
    {

        String jobId = UUID.randomUUID().toString();
        SubmitResponse response = new SubmitResponse(submitRequest.getClient(),jobId);

        jobcache.add(submitRequest.getClient(), jobId, Status.Submitted );

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
