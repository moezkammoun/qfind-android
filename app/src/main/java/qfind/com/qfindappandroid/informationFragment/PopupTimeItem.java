package qfind.com.qfindappandroid.informationFragment;

/**
 * Created by MoongedePC on 22-Mar-18.
 */

public class PopupTimeItem {
    String day;
    String opening_time;
    String opening_title;
    String closing_time;
    String closing_title;

    public PopupTimeItem() {
    }

    public PopupTimeItem(String day, String opening_time, String opening_title, String closing_time, String closing_title) {
        this.day = day;
        this.opening_time = opening_time;
        this.opening_title = opening_title;
        this.closing_time = closing_time;
        this.closing_title = closing_title;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getOpening_time() {
        return opening_time;
    }

    public void setOpening_time(String opening_time) {
        this.opening_time = opening_time;
    }

    public String getOpening_title() {
        return opening_title;
    }

    public void setOpening_title(String opening_title) {
        this.opening_title = opening_title;
    }

    public String getClosing_time() {
        return closing_time;
    }

    public void setClosing_time(String closing_time) {
        this.closing_time = closing_time;
    }

    public String getClosing_title() {
        return closing_title;
    }

    public void setClosing_title(String closing_title) {
        this.closing_title = closing_title;
    }
}
