package qfind.com.qfindappandroid.categoryfragment;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;


import java.io.File;

import cn.lightsky.infiniteindicator.ImageLoader;
import qfind.com.qfindappandroid.R;

/**
 * Created by dilee on 17-01-2018.
 */

public class PicassoLoader implements ImageLoader {

    public PicassoLoader getImageLoader(Context context) {
        return new PicassoLoader();
    }

    @Override
    public void load(Context context, ImageView targetView, Object res) {
        if (res == null) {
            return;
        }

        Picasso picasso = Picasso.with(context);
        RequestCreator requestCreator = null;

        if (res instanceof String) {
            requestCreator = picasso.load((String) res).placeholder(R.drawable.banner);
        } else if (res instanceof File) {
            requestCreator = picasso.load((File) res).placeholder(R.drawable.banner);
        } else if (res instanceof Integer) {
            requestCreator = picasso.load((Integer) res).placeholder(R.drawable.banner);
        }

        requestCreator
                .resize(600,600)
               .centerInside()
//                .fit()
                .into(targetView);


    }

}
