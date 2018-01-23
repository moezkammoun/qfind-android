package qfind.com.qfindappandroid.categoryfragment;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import cn.lightsky.infiniteindicator.Page;

/**
 * Created by dilee on 07-01-2018.
 */

public class CategoryFragmentPresenterImpl {

    CategoryItemAdapter categoryItemAdapter;
    List<Categories> categories;
    CategoryFragmentView categoryFragmentView;
    CategoryFragmentModel categoryFragmentModel;
    ArrayList<Page> adsImagesList;
    RecyclerViewClickListener recyclerViewClickListener;

    public CategoryFragmentPresenterImpl(CategoryFragmentView categoryFragmentView,RecyclerViewClickListener recyclerViewClickListener) {
        this.categoryFragmentView = categoryFragmentView;
        this.recyclerViewClickListener = recyclerViewClickListener;

    }

    public void getImagesForAds() {
        categoryFragmentModel = new CategoryFragmentModel();
        adsImagesList = categoryFragmentModel.getAdsImages();
        categoryFragmentView.loadAds(adsImagesList);

    }

    public void getCategoryItemsDetails(Context context) {
        categories = new ArrayList<>();
        categoryItemAdapter = new CategoryItemAdapter(context, categories,recyclerViewClickListener);
        categoryFragmentView.setCategoryItemRecyclerView(categoryItemAdapter);
        categories.addAll(categoryFragmentModel.getCategoriesDetails());
        categoryItemAdapter.notifyDataSetChanged();
    }
    public void getSubCategoryItemsDetails(Context context) {
        categories = new ArrayList<>();
        categoryItemAdapter = new CategoryItemAdapter(context, categories,recyclerViewClickListener);
        categoryFragmentView.setCategoryItemRecyclerView(categoryItemAdapter);
        categories.addAll(categoryFragmentModel.getSubCategoriesDetails());
        categoryItemAdapter.notifyDataSetChanged();
    }

}
