package scheduler;

import data.StatusRequest;
import data.StatusResponse;
import data.SubmitRequest;
import data.SubmitResponse;

import java.util.UUID;

public class ClientRequestManager {


    public SubmitResponse submit(SubmitRequest submitRequest)
    {

        String jobId = UUID.randomUUID().toString();
        SubmitResponse response = new SubmitResponse(submitRequest.getClient(),jobId);

        return response;

    }

    public StatusResponse status(StatusRequest request) {

        return new StatusResponse();
    }
}
