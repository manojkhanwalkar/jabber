package decision.engine;

import decision.data.ServiceResponse;

import java.util.List;
import java.util.Random;

public class RulesEvaluator {

    public static String evaluate(RuleSet ruleSet, ServiceResponse serviceResponse)
    {

        String result ;
        for (int i=0;i<ruleSet.rules.size();i++)
        {
            Rule rule = ruleSet.rules.get(i);

            result = rule.evaluate(serviceResponse);
            System.out.println(rule + " evaluated to " + result);

            if (result.equals(Rule.nextRule))
                continue;
            else
            {
                return result;
            }

        }

        return Rule.lastStep;   // none of the rules indicated an action , terminate the workflow


    }



}
