package decision.wf;

import decision.data.DecisionRequest;
import decision.data.DecisionResponse;
import decision.data.ServiceResponse;
import decision.engine.RuleSet;
import decision.engine.RulesEvaluator;
import decision.service.Service;
import decision.service.ServiceLocator;

import java.util.List;
import java.util.UUID;

import static decision.wf.ServiceRuleSetTuple.lastStep;
import static decision.wf.ServiceRuleSetTuple.nextRule;

public class WorkflowEvaluator {

    static DecisionResponse process(Workflow wf, DecisionRequest request)
    {
        List<ServiceRuleSetTuple> workflow = wf.serviceRuleSetTuples;

        DecisionResponse decisionResponse = new DecisionResponse();

        decisionResponse.setRequestId(request.getRequestId());
        decisionResponse.setResponseId(UUID.randomUUID().toString());

        int curr=0;
        while(curr<workflow.size())
        {
            String serviceName = workflow.get(curr).getServiceName();

            Service service = ServiceLocator.getInstance().get(serviceName).get();
            RuleSet ruleSet = workflow.get(curr).getRuleSet();

            System.out.println("Calling service " + service.getName());

            ServiceResponse serviceResponse=service.evaluate(request.getEvent());

            decisionResponse.addRawResponse(serviceResponse);

            String next;
            if (ruleSet==null)
            {
                curr = curr+1;
            }

            if (RulesEvaluator.evaluate(ruleSet,serviceResponse)) {

               next = workflow.get(curr).getTrueNext();

            }
            else
            {
                next = workflow.get(curr).getFalseNext();

            }

            if (next.equalsIgnoreCase(lastStep))
            {
                break;
            }

            if (next.equalsIgnoreCase(nextRule))
            {
                curr=curr+1;  // next rule

            }
            else
            {
                curr = getLocation(next,workflow);
            }


        }

        wf.OnTerminate(decisionResponse);
        return decisionResponse;




    }

    private static int getLocation(String name,List<ServiceRuleSetTuple> workflow )
    {
        for (int i=0;i<workflow.size();i++)
        {
            if (workflow.get(i).serviceName.equalsIgnoreCase(name))
                return i;
        }

        return -1;
    }
}
