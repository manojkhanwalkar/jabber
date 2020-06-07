package decision.wf;

import decision.engine.RuleSet;
import decision.service.Service;

public class ServiceRuleSetTuple {

    Service service;
    RuleSet ruleSet;


    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public RuleSet getRuleSet() {
        return ruleSet;
    }

    public void setRuleSet(RuleSet ruleSet) {
        this.ruleSet = ruleSet;
    }

    public ServiceRuleSetTuple(Service service, RuleSet ruleSet) {
        this.service = service;
        this.ruleSet = ruleSet;
    }
}
