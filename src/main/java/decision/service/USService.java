package decision.service;

import decision.data.Event;
import decision.data.ServiceResponse;

import java.util.UUID;

public class USService implements Service {

    public static final String serviceName="USService";


    public String getName()
    {
        return serviceName;
    }


    @Override
    public ServiceResponse evaluate(Event event) {

        ServiceResponse serviceResponse=new ServiceResponse();
        serviceResponse.setServiceName(serviceName);
        serviceResponse.setRawResponse("This is the raw response from service USService");
        serviceResponse.setResponseId(UUID.randomUUID().toString());

        serviceResponse.setServiceDecision("Approved");
        return serviceResponse;
    }
}
