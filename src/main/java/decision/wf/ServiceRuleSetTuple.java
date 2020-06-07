package decision.wf;

import decision.engine.RuleSet;
import decision.service.Service;

public class ServiceRuleSetTuple {

    public static String lastStep = "terminate";

    String serviceName;
    RuleSet ruleSet;

    String trueNext;
    String falseNext;


    public ServiceRuleSetTuple() {}




    public ServiceRuleSetTuple(String serviceName, RuleSet ruleSet, String trueNext, String falseNext) {
        this.serviceName = serviceName;
        this.ruleSet = ruleSet;
        this.trueNext = trueNext;
        this.falseNext = falseNext;
    }

    public RuleSet getRuleSet() {
        return ruleSet;
    }

    public void setRuleSet(RuleSet ruleSet) {
        this.ruleSet = ruleSet;
    }


    public static String getLastStep() {
        return lastStep;
    }

    public static void setLastStep(String lastStep) {
        ServiceRuleSetTuple.lastStep = lastStep;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getTrueNext() {
        return trueNext;
    }

    public void setTrueNext(String trueNext) {
        this.trueNext = trueNext;
    }

    public String getFalseNext() {
        return falseNext;
    }

    public void setFalseNext(String falseNext) {
        this.falseNext = falseNext;
    }
}
