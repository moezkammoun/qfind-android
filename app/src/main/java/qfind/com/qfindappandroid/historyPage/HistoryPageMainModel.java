package qfind.com.qfindappandroid.historyPage;

import java.util.ArrayList;

/**
 * Created by MoongedePC on 19-Jan-18.
 */

public class HistoryPageMainModel {

    private String day;
    private ArrayList<HistoryPageDataModel> historyPageDataModels;

    public HistoryPageMainModel(String day, ArrayList<HistoryPageDataModel> historyPageDataModels) {
        this.day = day;
        this.historyPageDataModels = historyPageDataModels;
    }

    public HistoryPageMainModel() {

    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public ArrayList<HistoryPageDataModel> getHistoryPageDataModels() {
        return historyPageDataModels;
    }

    public void setHistoryPageDataModels(ArrayList<HistoryPageDataModel> historyPageDataModels) {
        this.historyPageDataModels = historyPageDataModels;
    }
}
