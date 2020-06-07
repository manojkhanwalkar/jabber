package decision.wf;

import decision.data.DecisionRequest;
import decision.data.DecisionResponse;
import decision.data.ServiceResponse;
import decision.engine.RuleSet;
import decision.engine.RulesEvaluator;
import decision.service.Service;

import java.util.List;
import java.util.UUID;

public class WorkflowEvaluator {

    static DecisionResponse process(List<ServiceRuleSetTuple> workflow, DecisionRequest request)
    {

        DecisionResponse decisionResponse = new DecisionResponse();

        decisionResponse.setRequestId(request.getRequestId());
        decisionResponse.setResponseId(UUID.randomUUID().toString());

        for (int i=0;i<workflow.size();i++)
        {
            Service service = workflow.get(i).getService();
            RuleSet ruleSet = workflow.get(i).getRuleSet();

            ServiceResponse serviceResponse=service.evaluate(request.getEvent());

            if (RulesEvaluator.evaluate(ruleSet,serviceResponse)) {

                // todo - add decisions
                return decisionResponse;
            }


        }

        return decisionResponse;




    }
}
