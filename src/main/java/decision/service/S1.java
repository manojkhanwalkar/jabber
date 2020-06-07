package decision.service;

import decision.data.Event;
import decision.data.ServiceResponse;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class S1 implements Service {

    public static final String serviceName="S1";

    public String getName()
    {
        return serviceName;
    }


    /*
      String serviceName;
    String serviceDecision;
    String rawResponse;
    String responseId;
     */
    @Override
    public ServiceResponse evaluate(Event event) {

        Random random = new Random();

        ServiceResponse serviceResponse=new ServiceResponse();
        serviceResponse.setServiceName(serviceName);
        serviceResponse.setRawResponse("This is the raw response from service S1");
        serviceResponse.setResponseId(UUID.randomUUID().toString());

        HashMap<String,String> serviceDecision = new HashMap<>();
        serviceDecision.put("score",String.valueOf(random.nextInt(101)));


        serviceResponse.setServiceDecisionElements(serviceDecision);
        return serviceResponse;
    }
}
