package worker;



import com.codahale.metrics.annotation.Timed;
import data.RegisterWorker;
import data.SubmitResponse;
import data.WorkerRequest;
import data.WorkerResponse;
import util.Connection;
import util.JSONUtil;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;



@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class WorkerResource {
    private final String template;
    private final String defaultName;



    public WorkerResource(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;


        registerWorker();
    }

    private void registerWorker()
    {

        CompletableFuture.runAsync(()->{

            String respStr=null;
            while(respStr==null) {
                Connection app = new Connection("https://localhost:8480/");

                String url = "https://localhost:8380/";

                RegisterWorker registerWorker = new RegisterWorker(url);

                respStr = app.sendSimple(JSONUtil.toJSON(registerWorker), "register");

                if (respStr==null)
                {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        });




    }


    WorkManager workManager = new WorkManager();

    @POST
    @Timed
    @Path("/work")
    @Produces(MediaType.APPLICATION_JSON)
    public WorkerResponse work(WorkerRequest request) {


        return workManager.process(request);


    }










}
