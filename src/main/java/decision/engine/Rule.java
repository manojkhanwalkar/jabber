package decision.engine;

import decision.data.ServiceResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Rule {

    public static String lastStep = "terminate";
    public static String nextRule = "nextRule";

    String trueNext;
    String falseNext;



  //  String nextStep;


    List<Condition> conditions= new ArrayList<>();

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public void add(Condition condition)
    {
        conditions.add(condition);
    }

    public Rule(String trueNext , String falseNext)
    {
        this.trueNext=trueNext;
        this.falseNext=falseNext;
    }


    public String evaluate(ServiceResponse serviceResponse)
    {

       for (Condition condition :conditions)
       {
           boolean result = condition.evaluate(serviceResponse);
           if (!result)
               return falseNext;
       }

       return trueNext;

    }

    public static String getLastStep() {
        return lastStep;
    }

    public static void setLastStep(String lastStep) {
        Rule.lastStep = lastStep;
    }

    public static String getNextRule() {
        return nextRule;
    }

    public static void setNextRule(String nextRule) {
        Rule.nextRule = nextRule;
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
