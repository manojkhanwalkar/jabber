package decision.wf;

import decision.data.DecisionResponse;
import decision.engine.RuleSet;

import java.util.ArrayList;
import java.util.List;

public class Workflow {
    String name ;

    public Workflow(String name)
    {
        this.name = name;
    }

    List<ServiceRuleSetTuple> serviceRuleSetTuples = new ArrayList<>();

    public void add(String serviceName , RuleSet ruleSet , String trueNext, String falseNext)
    {
            ServiceRuleSetTuple serviceRuleSetTuple = new ServiceRuleSetTuple(serviceName,ruleSet,trueNext,falseNext);
            serviceRuleSetTuples.add(serviceRuleSetTuple);
    }

    public void add( ServiceRuleSetTuple serviceRuleSetTuple)
    {
        serviceRuleSetTuples.add(serviceRuleSetTuple);
    }

    public void OnTerminate(DecisionResponse decisionResponse)
    {
        //TODO - add a ruleset to go with this later

        decisionResponse.setFinalDecision("Approved");
        // for now all services that have been called need to approve to get final decision as approved

        decisionResponse.getRawResponses().stream().forEach(sr->{
           if ( sr.getServiceDecision().equalsIgnoreCase("rejected"))
           {
               decisionResponse.setFinalDecision("Rejected");
               return;
           }
        });
    }
}
