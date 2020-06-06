package decision.engine;

import java.util.HashMap;
import java.util.Map;

public class RulesContainer {

    Map<String,RuleSet> map = new HashMap<>();

    public void addRuleSet(String name, RuleSet ruleSet)
    {
        map.put(name,ruleSet);
    }


    public RuleSet getRuleSet(String name)
    {
        return map.get(name);
    }

    public Map<String, RuleSet> getMap() {
        return map;
    }

    public void setMap(Map<String, RuleSet> map) {
        this.map = map;
    }
}
