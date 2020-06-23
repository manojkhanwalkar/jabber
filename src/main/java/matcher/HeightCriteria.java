package matcher;

public class HeightCriteria {




    int min ;
    int max;

    public static HeightCriteria any()
    {
        return new HeightCriteria(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public HeightCriteria() {
    }

    public HeightCriteria(int min, int max)
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
