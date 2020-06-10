package decision.engine;

import decision.data.ServiceResponse;

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

    public boolean evaluate(ServiceResponse serviceResponse)
    {

       for (Condition condition :conditions)
       {
           boolean result = condition.evaluate(serviceResponse);
           if (!result)
               return false;
       }

       return true;

    }




    /*

     private static boolean evaluate (Condition rule , ServiceResponse serviceResponse)
    {


    }
     */
    //TODO - action might need to be associated at this level.
}
