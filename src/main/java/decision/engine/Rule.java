package decision.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Rule {

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

    public boolean evaluate()
    {

        System.out.println("Evaluating " + conditions);
        Random random = new Random();
        int toss = random.nextInt(2);
        return  (toss==0);

    }


    /*

     private static boolean evaluate (Condition rule , ServiceResponse serviceResponse)
    {


    }
     */
    //TODO - action might need to be associated at this level.
}
