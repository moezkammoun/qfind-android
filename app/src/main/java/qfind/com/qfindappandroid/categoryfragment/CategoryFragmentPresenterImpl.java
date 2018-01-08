package qfind.com.qfindappandroid.categoryfragment;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dilee on 07-01-2018.
 */

public class CategoryFragmentPresenterImpl {

    CategoryItemAdapter categoryItemAdapter;
    List<Categories> categories;
    CategoryFragmentView categoryFragmentView;
    CategoryFragmentModel categoryFragmentModel;
    ArrayList<String> adsImagesList;

    public CategoryFragmentPresenterImpl(CategoryFragmentView categoryFragmentView) {
        this.categoryFragmentView = categoryFragmentView;

    }

    public void getImagesForAds() {
        categoryFragmentModel = new CategoryFragmentModel();
        adsImagesList = categoryFragmentModel.getAdsImages();
        categoryFragmentView.loadAds(adsImagesList);

    }

    public void getCategoryItemsDetails(Context context) {
        categories = new ArrayList<>();
        categoryItemAdapter = new CategoryItemAdapter(context, categories);
        categoryFragmentView.setCategoryItemRecyclerView(categoryItemAdapter);
        categories.addAll(categoryFragmentModel.getCategoriesDetails());
        categoryItemAdapter.notifyDataSetChanged();
    }

}
