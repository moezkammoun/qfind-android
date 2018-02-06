package qfind.com.qfindappandroid.historyPage;

/**
 * Created by MoongedePC on 29-Jan-18.
 */

public class HistoryDateCount {
    String day;
    int count;

    public HistoryDateCount() {
    }

    public HistoryDateCount(String day, int count) {
        this.day = day;
        this.count = count;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
