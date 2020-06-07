package decision.wf;



import com.codahale.metrics.annotation.Timed;
import data.*;
import decision.data.DecisionRequest;
import decision.data.DecisionResponse;
import scheduler.ClientRequestManager;
import scheduler.WorkerLoadBalancer;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class WFResource {
    private final String template;
    private final String defaultName;

   // ClientRequestManager clientRequestManager ;


    public WFResource(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
     /*   clientRequestManager = new ClientRequestManager();

        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            public void run()
            {
                clientRequestManager.stop();
            }
        }); */


    }


   @POST
    @Timed
    @Path("/submit")
    @Produces(MediaType.APPLICATION_JSON)
    public DecisionResponse submit(DecisionRequest request) {


        return new DecisionResponse();
//        return clientRequestManager.submit(request);


    }









}
