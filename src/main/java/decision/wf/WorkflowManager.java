package decision.wf;

import decision.data.DecisionRequest;
import decision.data.DecisionResponse;
import decision.engine.RuleSet;
import decision.service.PIIValidator;
import decision.service.S1;
import decision.service.ServiceLocator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static decision.wf.ServiceRuleSetTuple.lastStep;

public class WorkflowManager {

    Map<String, Workflow> workflows = new HashMap<>();

    public DecisionResponse submit(DecisionRequest request) {

        String name = request.getWorkflowId();

        var workflow = workflows.get(name);

        return WorkflowEvaluator.process(workflow,request);




    }


    public void addWorkFlow(String name,Workflow workflow)
    {

        workflows.put(name,workflow);
    }


    public void init()
    {

        ServiceLocator.getInstance().put("S1", new S1());
        ServiceLocator.getInstance().put("PIIValidator", new PIIValidator());

        RuleSet ruleSet = RuleSet.testSet1("Rule1");

        RuleSet ruleSet2 = RuleSet.testSet2("Rule2");

        ServiceRuleSetTuple serviceRuleSetTuple = new ServiceRuleSetTuple("S1",ruleSet,"PIIValidator","PIIValidator");
        ServiceRuleSetTuple serviceRuleSetTuple2 = new ServiceRuleSetTuple("PIIValidator",ruleSet2, lastStep, lastStep);

        Workflow workflow = new Workflow("WF1");
        workflow.add(serviceRuleSetTuple);
        workflow.add(serviceRuleSetTuple2);

        addWorkFlow("WF1",workflow);
    }
}
