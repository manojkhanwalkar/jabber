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
        any=false;

    }

    public boolean isAny() {
        return any;
    }

    public void setAny(boolean any) {
        this.any = any;
    }

    public boolean isOpposite() {
        return opposite;
    }

    public void setOpposite(boolean opposite) {
        this.opposite = opposite;
    }

    public Profile.Gender getGender() {
        return gender;
    }

    public void setGender(Profile.Gender gender) {
        this.gender = gender;
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
