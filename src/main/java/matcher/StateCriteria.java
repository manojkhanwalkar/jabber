package matcher;

import java.util.ArrayList;

public class StateCriteria {


    boolean any =false;

    ArrayList<Profile.State> states = new ArrayList<>();

    public static StateCriteria any()
    {
        return new StateCriteria(true);
    }

    public StateCriteria() {
    }

    public StateCriteria(boolean any)
    {

        this.any = any;

    }

    public StateCriteria(ArrayList<Profile.State> states)
    {
        this.states = states;

    }


    public boolean isAny() {
        return any;
    }

    public void setAny(boolean any) {
        this.any = any;
    }

    public ArrayList<Profile.State> getStates() {
        return states;
    }

    public void setStates(ArrayList<Profile.State> states) {
        this.states = states;
    }

    public boolean evaluate(Profile other)
    {

        if (any)
            return true;

        if (states.contains(other.getState()))
            return true;
        else
            return false;


    }
}
