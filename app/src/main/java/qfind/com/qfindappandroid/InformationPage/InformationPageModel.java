package qfind.com.qfindappandroid.InformationPage;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by MoongedePC on 08-Jan-18.
 */

public class InformationPageModel {
    int info_icon;
    int info_point;
    String info_content;
    int info_back_button;

    public InformationPageModel(int info_icon, int info_point,
                                String info_content, int info_back_button) {
        this.info_icon = info_icon;
        this.info_point = info_point;
        this.info_content = info_content;
        this.info_back_button = info_back_button;
    }

    public int getInfo_icon() {
        return info_icon;
    }

    public void setInfo_icon(int info_icon) {
        this.info_icon = info_icon;
    }

    public int getInfo_point() {
        return info_point;
    }

    public void setInfo_point(int info_point) {
        this.info_point = info_point;
    }

    public String getInfo_content() {
        return info_content;
    }

    public void setInfo_content(String info_content) {
        this.info_content = info_content;
    }

    public int getInfo_back_button() {
        return info_back_button;
    }

    public void setInfo_back_button(int info_back_button) {
        this.info_back_button = info_back_button;
    }
}
