package decision.wf;

import decision.data.DecisionResponse;
import decision.engine.RuleSet;

import java.util.ArrayList;
import java.util.List;

public class Workflow {

    public static String firstRuleSet = "InputRuleSet";
    public static String firstService = "FirstService";
    String name ;

    public Workflow(String name)
    {
        this.name = name;
    }

    public Workflow()
    {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ServiceRuleSetTuple> getServiceRuleSetTuples() {
        return serviceRuleSetTuples;
    }

    public void setServiceRuleSetTuples(ArrayList<ServiceRuleSetTuple> serviceRuleSetTuples) {
        this.serviceRuleSetTuples = serviceRuleSetTuples;
    }

    ArrayList<ServiceRuleSetTuple> serviceRuleSetTuples = new ArrayList<>();

    public void add(String serviceName , RuleSet ruleSet )
    {
            ServiceRuleSetTuple serviceRuleSetTuple = new ServiceRuleSetTuple(serviceName,ruleSet);
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
           if ( sr.getServiceDecision()!=null && sr.getServiceDecision().equalsIgnoreCase("rejected"))
           {
               decisionResponse.setFinalDecision("Rejected");
               return;
           }
        });
    }

    ServiceRuleSetTuple first ;


    public ServiceRuleSetTuple getFirst() {
        return first;
    }

    public void setFirst(ServiceRuleSetTuple first) {
        this.first = first;
    }

    public void addFirst(ServiceRuleSetTuple serviceRuleSetTuple) {

        this.first = serviceRuleSetTuple;
    }
}
