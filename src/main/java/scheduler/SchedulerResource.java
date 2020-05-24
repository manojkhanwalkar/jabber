package scheduler;



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


    public SchedulerResource(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
    }


  /*  @POST
    @Timed
    @Path("/keyexchange")
    @Produces(MediaType.APPLICATION_JSON)
    public KeyExchange exchange(KeyExchange request) {


        return keyExchangeManager.processExchange(request);


    }


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
