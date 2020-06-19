package decision.engine;

import java.util.ArrayList;
import java.util.List;

public class RuleSet
{
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    List<Rule> rules= new ArrayList();

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public RuleSet()
    {

    }

    protected RuleSet(String name)
    {
        this.name = name;
    }


 /*   public static RuleSet testSet1(String name)
    {
        RuleSet ruleSet = new RuleSet(name);

        Rule rule1 = new Rule();
        rule1.add(new Condition("score" , Condition.Operator.gt , "100", Condition.OperandType.integer));

        Rule rule2 = new Rule();
        rule2.add(new Condition("approved" , Condition.Operator.eq , "yes", Condition.OperandType.string));

        ruleSet.rules.add(rule1);
        ruleSet.rules.add(rule2);


        return ruleSet;
    }*/

    public static RuleSet inputRuleSet(String name)
    {
        RuleSet ruleSet = new RuleSet(name);

        Rule rule1 = new Rule("USService","InternationalService");
        rule1.add(new Condition("country" , Condition.Operator.eq , "US", Condition.OperandType.string));


        ruleSet.rules.add(rule1);


        return ruleSet;
    }


   /* public static RuleSet testSet2(String name)
    {
        RuleSet ruleSet = new RuleSet(name);

        Rule rule1 = new Rule();
        rule1.add(new Condition("age" , Condition.Operator.gt , "18", Condition.OperandType.integer));
        rule1.add(new Condition("country" , Condition.Operator.eq , "US", Condition.OperandType.integer));

        return ruleSet;
    }*/





}
