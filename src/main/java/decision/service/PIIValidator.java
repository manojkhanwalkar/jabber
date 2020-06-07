package decision.service;

import decision.data.Event;
import decision.data.ServiceResponse;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class PIIValidator implements Service {

    public static final String serviceName="PIIValidator";


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

        HashMap<String,String> serviceDecision = new HashMap<>();
        serviceDecision.put("age",event.get("age"));
        serviceDecision.put("country",event.get("country"));

        ServiceResponse serviceResponse=new ServiceResponse();
        serviceResponse.setServiceName(serviceName);
        serviceResponse.setRawResponse("This is the raw response from service PIIValidator");
        serviceResponse.setResponseId(UUID.randomUUID().toString());



        serviceResponse.setServiceDecisionElements(serviceDecision);
        return serviceResponse;
    }
}
