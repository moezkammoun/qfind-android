package qfind.com.qfindappandroid.informationFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import qfind.com.qfindappandroid.R;

/**
 * Created by MoongedePC on 22-Mar-18.
 */

public class PopupAdapter extends BaseAdapter {
    Context context;
    ArrayList<PopupTimeItem> popupTimeItem;

    public PopupAdapter(Context context, ArrayList<PopupTimeItem> popupTimeItem) {
        this.context = context;
        this.popupTimeItem = popupTimeItem;
    }

    public PopupAdapter(Context context) {
    }

    @Override
    public int getCount() {
        return popupTimeItem.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.popup_layout, null);
        TextView day, openingTime, closingTime, openingTitle, closingTitle, closedText;
        LinearLayout open, closed;
        day = (TextView) v.findViewById(R.id.info_day);
        openingTime = (TextView) v.findViewById(R.id.opening_time);
        closingTime = (TextView) v.findViewById(R.id.closing_time);
        openingTitle = (TextView) v.findViewById(R.id.opening_title);
        closingTitle = (TextView) v.findViewById(R.id.closing_title);
        closedText = (TextView) v.findViewById(R.id.closed);
        open = (LinearLayout) v.findViewById(R.id.open_layout);
        closed = (LinearLayout) v.findViewById(R.id.closed_layout);

        if (popupTimeItem.get(i).getOpening_time().equals("Holiday")) {
            open.setVisibility(View.GONE);
            closed.setVisibility(View.VISIBLE);
            switch (popupTimeItem.get(i).getDay()) {
                case "Monday":
                    day.setText(R.string.Monday);
                    break;
                case "Tuesday":
                    day.setText(R.string.Tuesday);
                    break;
                case "Wednesday":
                    day.setText(R.string.Wednesday);
                    break;
                case "Thursday":
                    day.setText(R.string.Thursday);
                    break;
                case "Friday":
                    day.setText(R.string.Friday);
                    break;
                case "Saturday":
                    day.setText(R.string.Saturday);
                    break;
                case "Sunday":
                    day.setText(R.string.Sunday);
                    break;
                default:
                    break;
            }
            closedText.setText(R.string.Closed);

        } else {
            open.setVisibility(View.VISIBLE);
            closed.setVisibility(View.GONE);
            switch (popupTimeItem.get(i).getDay()) {
                case "Monday":
                    day.setText(R.string.Monday);
                    break;
                case "Tuesday":
                    day.setText(R.string.Tuesday);
                    break;
                case "Wednesday":
                    day.setText(R.string.Wednesday);
                    break;
                case "Thursday":
                    day.setText(R.string.Thursday);
                    break;
                case "Friday":
                    day.setText(R.string.Friday);
                    break;
                case "Saturday":
                    day.setText(R.string.Saturday);
                    break;
                case "Sunday":
                    day.setText(R.string.Sunday);
                    break;
                default:
                    break;
            }

            openingTime.setText(popupTimeItem.get(i).getOpening_time());
            closingTime.setText(popupTimeItem.get(i).getClosing_time());
            openingTitle.setText(popupTimeItem.get(i).getOpening_title());
            closingTitle.setText(popupTimeItem.get(i).getClosing_title());

        }

        return v;
    }
}
