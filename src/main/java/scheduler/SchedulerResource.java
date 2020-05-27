package scheduler;



import com.codahale.metrics.annotation.Timed;
import data.*;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.concurrent.atomic.AtomicLong;



@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class SchedulerResource {
    private final String template;
    private final String defaultName;

    ClientRequestManager clientRequestManager ;


    public SchedulerResource(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
        clientRequestManager = new ClientRequestManager();

        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            public void run()
            {
                clientRequestManager.stop();
            }
        });
    }


   @POST
    @Timed
    @Path("/submit")
    @Produces(MediaType.APPLICATION_JSON)
    public SubmitResponse submit(SubmitRequest request) {


        return clientRequestManager.submit(request);


    }


    @POST
    @Timed
    @Path("/response")
    @Produces(MediaType.APPLICATION_JSON)
    public String response(WorkerResponse response) {


        return clientRequestManager.response(response);


    }

    @POST
    @Timed
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    public String register(RegisterWorker registerWorker) {


        System.out.println("Registered " + registerWorker.getUrl());
        WorkerLoadBalancer.getInstance().register(registerWorker.getUrl());
        return "success";


    }

    @POST
    @Timed
    @Path("/deregister")
    @Produces(MediaType.APPLICATION_JSON)
    public String deregister(DeRegisterWorker registerWorker) {


        System.out.println("DeRegistered " + registerWorker.getUrl());
        WorkerLoadBalancer.getInstance().deregister(registerWorker.getUrl());
        return "success";


    }



    @POST
    @Timed
    @Path("/status")
    @Produces(MediaType.APPLICATION_JSON)
    public StatusResponse status(StatusRequest request) {


        return clientRequestManager.status(request);


    }
/*

/*

    @GET
    @Timed
    @Path("/claimkey")
    @Produces(MediaType.APPLICATION_JSON)
    public String exchange() {


        return rsaKeyHolder.getPublicKeyStr();

    }




    @POST
    @Timed
    @Path("/report")
    @Produces(MediaType.APPLICATION_JSON)
    public EncryptedMessage verify(EncryptedMessage request) {

        keyExchangeManager.verify(request);


        String requestStr = keyExchangeManager.decryptRequest(request);


               // Encrypted Verified claim to be processed here .
        EncryptedVerifiedClaim encryptedVerifiedClaim = (EncryptedVerifiedClaim) JSONUtil.fromJSON(requestStr,EncryptedVerifiedClaim.class);

        VerifiedClaim claim = convert(encryptedVerifiedClaim,rsaKeyHolder.getPrivateKey());

        isValidClaim(claim);

        System.out.println(claim);

        CombinedCreditScore combinedCreditScore = new CombinedCreditScore();


        CreditManagerFactory creditManagerFactory = CreditManagerFactory.getInstance();

        creditManagerFactory.get().parallelStream().forEach(creditManager -> {

            CreditScore creditScore = creditManager.getCreditScore();
            creditScore.setBureau(creditManager.bureau);
            combinedCreditScore.addScore(creditScore);

        });



        String responseStr = JSONUtil.toJSON(combinedCreditScore);
        EncryptedMessage response =  keyExchangeManager.encryptResponse(responseStr,request);
        keyExchangeManager.sign(response);
        return response;


    }


*/






}
