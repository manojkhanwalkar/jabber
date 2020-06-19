package decision.service;

import decision.data.Event;
import decision.data.ServiceResponse;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class FirstService implements Service {

    public static final String serviceName="FirstService";


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


        HashMap<String,String> serviceDecision = new HashMap<>();

        serviceDecision.putAll(event.getAttributes());


        ServiceResponse serviceResponse=new ServiceResponse();
        serviceResponse.setServiceName(serviceName);
        serviceResponse.setRawResponse("This is the raw response from service FirstService");
        serviceResponse.setResponseId(UUID.randomUUID().toString());



        serviceResponse.setServiceDecisionElements(serviceDecision);
        return serviceResponse;
    }
}
