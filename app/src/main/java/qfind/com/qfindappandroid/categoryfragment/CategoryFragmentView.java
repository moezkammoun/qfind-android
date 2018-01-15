package qfind.com.qfindappandroid.categoryfragment;

import java.util.ArrayList;

/**
 * Created by dilee on 07-01-2018.
 */

public interface CategoryFragmentView {
    public void loadAds(ArrayList<String> adsImages);
    public void setCategoryItemRecyclerView(CategoryItemAdapter categoryItemAdapter);
}
