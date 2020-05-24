package client;

import com.amazonaws.services.batch.model.SubmitJobRequest;
import data.StatusRequest;
import data.StatusResponse;
import data.SubmitRequest;
import data.SubmitResponse;
import org.apache.commons.codec.binary.Base64;

public class JobSubmitter {

    public static void main(String[] args) {

    }




    public void test()
    {
        //SubmitRequest request = new SubmitRequest(, "client1");
    }


    private SubmitResponse submitJob(SubmitRequest request)
    {
        // send to scheduler a jar file and client name and get back a job id .

        return null;

    }


    private StatusResponse getStatus(StatusRequest statusRequest)
    {
        return null;
    }

}
