package decision.service;

import decision.data.Event;
import decision.data.ServiceResponse;

public interface Service {

    public ServiceResponse evaluate(Event event);

}
