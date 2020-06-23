package matcher;

public class AgeCriteria {




    int min ;
    int max;

    public static AgeCriteria any()
    {
        return new AgeCriteria(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public AgeCriteria() {
    }

    public AgeCriteria(int min, int max)
    {
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }


    public boolean evaluate(Profile other)
    {
        if (min <= other.getAge() && other.getAge() <= max)
            return true;
        else
            return false;
    }
}
