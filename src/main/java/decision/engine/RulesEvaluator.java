package decision.engine;

import decision.data.Event;
import decision.data.ServiceResponse;
import decision.engine.RuleSet;

import java.util.List;
import java.util.Random;

public class RulesEvaluator {

    public static boolean evaluate(RuleSet ruleSet, ServiceResponse serviceResponse)
    {

        boolean result = false;
        for (int i=0;i<ruleSet.rules.size();i++)
        {
            List<Rule> list = ruleSet.rules.get(i);

            for (int j=0;j<list.size();j++)
            {
                Rule rule = list.get(j);
                result = evaluate(rule,serviceResponse);

                System.out.println(rule + " evaluated to " + result);
                if (!result)
                    break;

            }
            if (result) {
                serviceResponse.setServiceDecision("Approved");
                return result;
            }
        }

        serviceResponse.setServiceDecision("Rejected");
        return result;

    }


    private static boolean evaluate (Rule rule , ServiceResponse serviceResponse)
    {

        Random random = new Random();
        int toss = random.nextInt(2);
        return  (toss==0);

    }
}
