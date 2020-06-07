package decision;

import decision.data.Event;
import decision.engine.RuleSet;
//import decision.engine.RulesContainer;
import decision.engine.RulesEvaluator;
import util.JSONUtil;

public class RulesTester {




  /*  public static void main(String[] args) {

        RuleSet ruleSet = RuleSet.testSet("Rule1");

        RulesContainer container = new RulesContainer();
        container.addRuleSet("Rule1",ruleSet);



        String str = JSONUtil.toJSON(container);

        System.out.println(str);

        Event event1 = new Event();
        event1.put("score","50");
        event1.put("score1","50");

        RulesEvaluator rulesEvaluator = new RulesEvaluator();

       boolean result =  rulesEvaluator.evaluate(container.getRuleSet("Rule1"), event1);

       System.out.println(result);


    }*/

}
