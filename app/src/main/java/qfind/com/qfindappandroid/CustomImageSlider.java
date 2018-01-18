package qfind.com.qfindappandroid;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;

/**
 * Created by MoongedePC on 17-Jan-18.
 */

public class CustomImageSlider extends BaseSliderView {
    protected CustomImageSlider(Context context) {
        super(context);
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(this.getContext()).inflate(R.layout.render_type_text, null);
        ImageView target = (ImageView) v.findViewById(R.id.daimajia_slider_image);
        LinearLayout frame = (LinearLayout) v.findViewById(R.id.description_layout);
        frame.setBackgroundColor(Color.TRANSPARENT);
        this.bindEventAndShow(v, target);
        return v;
    }
}
