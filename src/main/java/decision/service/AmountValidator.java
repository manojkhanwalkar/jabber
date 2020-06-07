package decision.service;

import decision.data.Event;
import decision.data.ServiceResponse;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class AmountValidator implements Service {

    public static final String serviceName="AmountValidator";


    public String getName()
    {
        return serviceName;
    }


    @Override
    public ServiceResponse evaluate(Event event) {



        HashMap<String,String> serviceDecision = new HashMap<>();
        serviceDecision.put("age",event.get("age"));
        serviceDecision.put("amount",event.get("amount"));

        ServiceResponse serviceResponse=new ServiceResponse();
        serviceResponse.setServiceName(serviceName);
        serviceResponse.setRawResponse("This is the raw response from service AmountValidator");
        serviceResponse.setResponseId(UUID.randomUUID().toString());



        serviceResponse.setServiceDecisionElements(serviceDecision);
        return serviceResponse;
    }
}
