package decision.wf;

import decision.data.DecisionRequest;
import decision.data.DecisionResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class WorkflowManager {

    Map<String, List<ServiceRuleSetTuple>> workflows = new HashMap<>();

    public DecisionResponse submit(DecisionRequest request) {

        String name = request.getWorkflowId();

        var workflow = workflows.get(name);



        DecisionResponse decisionResponse = new DecisionResponse();

        decisionResponse.setRequestId(request.getRequestId());
        decisionResponse.setResponseId(UUID.randomUUID().toString());

        return decisionResponse;


    }


    public void addWorkFlow(String name,List<ServiceRuleSetTuple> servicesAndRules)
    {

        workflows.put(name,servicesAndRules);
    }
}
