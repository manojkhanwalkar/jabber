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

    List<List<Rule>> rules= new ArrayList();

    public List<List<Rule>> getRules() {
        return rules;
    }

    public void setRules(List<List<Rule>> rules) {
        this.rules = rules;
    }

    public RuleSet()
    {

    }

    protected RuleSet(String name)
    {
        this.name = name;
    }


    public static RuleSet testSet1(String name)
    {
        RuleSet ruleSet = new RuleSet(name);

        ruleSet.rules.add(new ArrayList<>());
        ruleSet.rules.add(new ArrayList<>());

        ruleSet.rules.get(0).add(new Rule("score" , Rule.Operator.gt , "100", Rule.OperandType.integer));
        ruleSet.rules.get(1).add(new Rule("approved" , Rule.Operator.eq , "yes", Rule.OperandType.string));

        return ruleSet;
    }

    public static RuleSet testSet2(String name)
    {
        RuleSet ruleSet = new RuleSet(name);

        ruleSet.rules.add(new ArrayList<>());
        ruleSet.rules.add(new ArrayList<>());

        ruleSet.rules.get(0).add(new Rule("age" , Rule.Operator.gt , "18", Rule.OperandType.integer));
        ruleSet.rules.get(0).add(new Rule("country" , Rule.Operator.eq , "US", Rule.OperandType.integer));

        return ruleSet;
    }





}
