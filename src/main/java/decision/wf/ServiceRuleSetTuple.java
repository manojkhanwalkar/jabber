package decision.wf;

import decision.engine.RuleSet;
import decision.service.Service;

public class ServiceRuleSetTuple {


    String serviceName;
    RuleSet ruleSet;

    public ServiceRuleSetTuple() {
    }

    public ServiceRuleSetTuple(String serviceName, RuleSet ruleSet) {
        this.serviceName = serviceName;
        this.ruleSet = ruleSet;

    }

    public RuleSet getRuleSet() {
        return ruleSet;
    }

    public void setRuleSet(RuleSet ruleSet) {
        this.ruleSet = ruleSet;
    }




    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }


}
