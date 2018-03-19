package qfind.com.qfindappandroid.categoryfragment;

import java.util.ArrayList;

import cn.lightsky.infiniteindicator.Page;

/**
 * Created by dilee on 07-01-2018.
 */

public interface CategoryFragmentView {
    public void loadAds(ArrayList<Page> adsImages);

    public void setCategoryItemRecyclerView(CategoryItemAdapter categoryItemAdapter);
}
