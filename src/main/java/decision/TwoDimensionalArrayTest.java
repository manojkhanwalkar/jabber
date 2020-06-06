package decision;

import util.JSONUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public class TwoDimensionalArrayTest {



    static class Container
    {

        List<List<Rule>> rules= new ArrayList();

        public List<List<Rule>> getRules() {
            return rules;
        }

        public void setRules(List<List<Rule>> rules) {
            this.rules = rules;
        }

        public void create()
        {
            rules.add(new ArrayList<>());
            rules.add(new ArrayList<>());

            rules.get(0).add(new Rule("score" , "gt" , "100"));
            rules.get(0).add(new Rule("score1" , "eq" , "10"));
            rules.get(1).add(new Rule("approved" , "eq" , "yes"));
        }


    }


  /*  static class Container
    {
        Rule[][]  rules = new Rule[2][2];

        public Rule[][] getRules() {
            return rules;
        }

        public void setRules(Rule[][] rules) {
            this.rules = rules;
        }

        public void create()
        {

            rules[0][0] = new Rule("score" , "gt" , "100");
            rules[0][1] = new Rule("score1" , "eq" , "10");
            rules[1][0] = new Rule("approved" , "eq" , "yes");
        }


    }*/



    static class Rule
    {
        String field;
        String operator ;
        String operand ;

        public Rule(String field, String operator, String operand) {
            this.field = field;
            this.operator = operator;
            this.operand = operand;
        }

        public Rule()
        {

        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public String getOperand() {
            return operand;
        }

        public void setOperand(String operand) {
            this.operand = operand;
        }
    }

    public static void main(String[] args) {

        Container container = new Container();

        container.create();

        String str = JSONUtil.toJSON(container);

        System.out.println(str);


    }

}
