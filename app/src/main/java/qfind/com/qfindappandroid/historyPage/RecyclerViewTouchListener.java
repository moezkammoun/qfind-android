package qfind.com.qfindappandroid.historyPage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import qfind.com.qfindappandroid.searchResultsFragment.SearchResultsClickListener;

/**
 * Created by MoongedePC on 07-Feb-18.
 */

public class RecyclerViewTouchListener implements RecyclerView.OnItemTouchListener {

    private GestureDetector gestureDetector;
    private HistoryClickListener clickListener;

    public RecyclerViewTouchListener(Context context, final RecyclerView recyclerView, final HistoryClickListener clickListener) {
        this.clickListener = clickListener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }


        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
            clickListener.onClick(child, rv.getChildPosition(child));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}

