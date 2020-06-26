package vfs;

public class VelocityStats {

    HistoricalCount historicalCount;
    CurrentCount currentCount;

    public HistoricalCount getHistoricalCount() {
        return historicalCount;
    }

    public void setHistoricalCount(HistoricalCount historicalCount) {
        this.historicalCount = historicalCount;
    }

    public CurrentCount getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(CurrentCount currentCount) {
        this.currentCount = currentCount;
    }

    @Override
    public String toString() {
        return "VelocityStats{" +
                "historicalCount=" + historicalCount +
                ", currentCount=" + currentCount +
                '}';
    }
}
