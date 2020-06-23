package matcher;

import java.util.ArrayList;
import java.util.List;

public class ReligionCriteria {


    boolean any =false;



    public static ReligionCriteria any()
    {
        return new ReligionCriteria(true);
    }

    public ReligionCriteria() {
    }

    public ReligionCriteria(boolean any)
    {

        this.any = any;

    }


    ArrayList<Profile.Religion> religions;


    public ReligionCriteria(ArrayList<Profile.Religion> religions)
    {

        this.religions = religions;

    }


    public boolean isAny() {
        return any;
    }

    public void setAny(boolean any) {
        this.any = any;
    }

    public ArrayList<Profile.Religion> getReligions() {
        return religions;
    }

    public void setReligions(ArrayList<Profile.Religion> religions) {
        this.religions = religions;
    }

    public boolean evaluate(Profile other)
    {

        if (any)
            return true;


        if (religions.contains(other.getReligion()))
            return true;

        return false;

    }
}
