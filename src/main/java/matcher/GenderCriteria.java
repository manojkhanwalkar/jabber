package matcher;

public class GenderCriteria {


    boolean any =false;

    boolean opposite = true;

    Profile.Gender gender;

    public static GenderCriteria any()
    {
        return new GenderCriteria(true);
    }

    public GenderCriteria() {
    }

    public GenderCriteria(boolean any)
    {

        this.any = any;

    }

    public GenderCriteria(Profile.Gender gender, boolean opposite)
    {

        this.gender = gender;
        this.opposite=opposite;

    }





    public boolean evaluate(Profile other)
    {

        if (any)
            return true;

        if (opposite && gender!=other.gender)
            return true;

        if (!opposite && gender==other.gender)
            return true;

        return false;

    }
}
