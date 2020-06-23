package matcher;

import java.util.ArrayList;

public class InterestCriteria {


    boolean any =false;


    public static InterestCriteria any()
    {
        return new InterestCriteria(true);
    }

    public InterestCriteria() {
    }



    public InterestCriteria(boolean any)
    {

        this.any = any;

    }


    ArrayList<Profile.Interests> interests;


    int count = 0;
    public InterestCriteria(ArrayList<Profile.Interests> interests)
    {

        this.interests = interests;

        count=interests.size();

    }

    public InterestCriteria(ArrayList<Profile.Interests> interests, int match)
    {

        this.interests = interests;

        count=match;

    }

    public boolean isAny() {
        return any;
    }

    public void setAny(boolean any) {
        this.any = any;
    }

    public ArrayList<Profile.Interests> getInterests() {
        return interests;
    }

    public void setInterests(ArrayList<Profile.Interests> interests) {
        this.interests = interests;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean evaluate(Profile other)
    {

        if (any)
            return true;


            int x =0;
            for (Profile.Interests interest : interests)
            {
                if (other.getInterests().contains(interest))
                    x++;
            }

            if (count==x)
                return true;
            else
                return false;

    }
}
