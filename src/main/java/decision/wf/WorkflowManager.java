package decision.wf;

import decision.data.DecisionRequest;
import decision.data.DecisionResponse;
import decision.engine.RuleSet;
import decision.service.*;
import logger.LogManager;
import logger.Logger;
import util.JSONUtil;

import java.util.HashMap;
import java.util.Map;

import static decision.wf.Workflow.firstRuleSet;
import static decision.wf.Workflow.firstService;

public class WorkflowManager {

    HashMap<String, Workflow> workflows = new HashMap<>();

    public HashMap<String, Workflow> getWorkflows() {
        return workflows;
    }

    public void setWorkflows(HashMap<String, Workflow> workflows) {
        this.workflows = workflows;
    }

    public DecisionResponse submit(DecisionRequest request) {

        String name = request.getWorkflowId();

        var workflow = workflows.get(name);

        return WorkflowEvaluator.process(workflow,request);




    }


    public void addWorkFlow(String name,Workflow workflow)
    {

        workflows.put(name,workflow);
    }


    public static WorkflowManager init()
    {

        WorkflowManager workflowManager = new WorkflowManager();

        ServiceLocator.getInstance().put("ScoreCreator", new ScoreCreator());
        ServiceLocator.getInstance().put("PIIValidator", new PIIValidator());
        ServiceLocator.getInstance().put("AmountValidator", new AmountValidator());
        ServiceLocator.getInstance().put("BigLoanGiver", new BigLoanGiver());
        ServiceLocator.getInstance().put("MiscLoanGiver", new MiscLoanGiver());
        ServiceLocator.getInstance().put("MiscLoanGiver", new MiscLoanGiver());
        ServiceLocator.getInstance().put("USService", new USService());
        ServiceLocator.getInstance().put("InternationalService", new InternationalService());
        ServiceLocator.getInstance().put(firstService, new FirstService());

//    Rule rule1 = new Rule(,"");
//
        {

            RuleSet inputRuleSet = RuleSet.inputRuleSet(firstRuleSet);

            ServiceRuleSetTuple serviceRuleSetTuple = new ServiceRuleSetTuple(firstService,inputRuleSet);


            //  RuleSet ruleSet = RuleSet.testSet1("Rule1");

            //RuleSet ruleSet2 = RuleSet.testSet2("Rule2");

            //ServiceRuleSetTuple serviceRuleSetTuple = new ServiceRuleSetTuple("ScoreCreator", ruleSet, "PIIValidator", nextRule);
            //ServiceRuleSetTuple serviceRuleSetTuple2 = new ServiceRuleSetTuple("PIIValidator", ruleSet2, lastStep, lastStep);

            Workflow workflow = new Workflow("WF1");

            workflow.addFirst(serviceRuleSetTuple);


            //workflow.add(serviceRuleSetTuple);
            //workflow.add(serviceRuleSetTuple2);

            workflowManager.addWorkFlow("WF1", workflow);

        }



        String str = JSONUtil.toJSON(workflowManager);

        LogManager.getInstance().log(str, Logger.LogLevels.INFO);

        //System.out.println(str);

        return workflowManager;

//TODO - read wf rules from config file .



    }
}
