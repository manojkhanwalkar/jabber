package client;

import com.amazonaws.services.batch.model.SubmitJobRequest;
import data.StatusRequest;
import data.StatusResponse;
import data.SubmitRequest;
import data.SubmitResponse;
import org.apache.commons.codec.binary.Base64;
import util.Connection;
import util.JSONUtil;
import util.JarUtils;

public class JobSubmitter {

    public static void main(String[] args) {

        JobSubmitter submitter = new JobSubmitter();
        submitter.test();

    }




    public void test()
    {
        String jar = JarUtils.getBase64EncodedStringFromJar("/home/manoj/data/jabber/epi.jar");
        SubmitRequest request = new SubmitRequest(jar, "client1","tmp.Test");

        SubmitResponse response = submitJob(request);

        System.out.println(response);
    }


    private SubmitResponse submitJob(SubmitRequest request)
    {
        // send to scheduler a jar file and client name and get back a job id .

        Connection app = new Connection("https://localhost:8480/");

        String respStr =  app.sendSimple(JSONUtil.toJSON(request),"submit");

        SubmitResponse response = (SubmitResponse)JSONUtil.fromJSON(respStr,SubmitResponse.class);

        return response;

        //return null;

    }


    private StatusResponse getStatus(StatusRequest statusRequest)
    {
        return null;
    }

}
