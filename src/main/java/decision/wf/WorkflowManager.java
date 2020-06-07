package decision.wf;

import decision.data.DecisionRequest;
import decision.data.DecisionResponse;
import decision.engine.RuleSet;
import decision.service.PIIValidator;
import decision.service.S1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class WorkflowManager {

    Map<String, List<ServiceRuleSetTuple>> workflows = new HashMap<>();

    public DecisionResponse submit(DecisionRequest request) {

        String name = request.getWorkflowId();

        var workflow = workflows.get(name);

        return WorkflowEvaluator.process(workflow,request);




    }


    public void addWorkFlow(String name,List<ServiceRuleSetTuple> servicesAndRules)
    {

        workflows.put(name,servicesAndRules);
    }


    public void init()
    {

        RuleSet ruleSet = RuleSet.testSet1("Rule1");

        RuleSet ruleSet2 = RuleSet.testSet2("Rule2");

        ServiceRuleSetTuple serviceRuleSetTuple = new ServiceRuleSetTuple(new S1(),ruleSet);
        ServiceRuleSetTuple serviceRuleSetTuple2 = new ServiceRuleSetTuple(new PIIValidator(),ruleSet2);

        addWorkFlow("WF1",List.of(serviceRuleSetTuple,serviceRuleSetTuple2));
    }
}
