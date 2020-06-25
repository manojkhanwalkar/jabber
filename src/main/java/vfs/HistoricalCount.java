package vfs;

public class HistoricalCount {

    int lastDay;
    int last7Days;
    int last30Days;

    public HistoricalCount() {}

    public int getLastDay() {
        return lastDay;
    }

    public void setLastDay(int lastDay) {
        this.lastDay = lastDay;
    }

    public int getLast7Days() {
        return last7Days;
    }

    public void setLast7Days(int last7Days) {
        this.last7Days = last7Days;
    }

    public int getLast30Days() {
        return last30Days;
    }

    public void setLast30Days(int last30Days) {
        this.last30Days = last30Days;
    }

    @Override
    public String toString() {
        return "HistoricalCount{" +
                "lastDay=" + lastDay +
                ", last7Days=" + last7Days +
                ", last30Days=" + last30Days +
                '}';
    }
}
