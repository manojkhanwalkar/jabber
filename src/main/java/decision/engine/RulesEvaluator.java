package decision.engine;

import decision.data.ServiceResponse;

import java.util.List;
import java.util.Random;

public class RulesEvaluator {

    public static boolean evaluate(RuleSet ruleSet, ServiceResponse serviceResponse)
    {

        boolean result = false;
        for (int i=0;i<ruleSet.rules.size();i++)
        {
            Rule rule = ruleSet.rules.get(i);

            result = rule.evaluate();
            System.out.println(rule + " evaluated to " + result);

            if (result) {
                serviceResponse.setServiceDecision("Approved");
                return result;
            }
        }

        serviceResponse.setServiceDecision("Rejected");
        return result;

    }



}
