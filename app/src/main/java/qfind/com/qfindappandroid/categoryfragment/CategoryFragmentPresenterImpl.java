package qfind.com.qfindappandroid.categoryfragment;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import cn.lightsky.infiniteindicator.Page;
import qfind.com.qfindappandroid.categorycontaineractivity.MainCategoryItemList;

/**
 * Created by dilee on 07-01-2018.
 */

public class CategoryFragmentPresenterImpl {

    CategoryItemAdapter categoryItemAdapter;
    CategoryFragmentView categoryFragmentView;
    CategoryFragmentModel categoryFragmentModel;
    ArrayList<Page> adsImagesList;
    RecyclerViewClickListener recyclerViewClickListener;
    ArrayList<MainCategoryItemList> mainCategoryItemList;
    ArrayList<SubCategoryItemList> subCategoryItemList;
    Context context;

    public CategoryFragmentPresenterImpl(Context context,CategoryFragmentView categoryFragmentView,RecyclerViewClickListener recyclerViewClickListener) {
        this.categoryFragmentView = categoryFragmentView;
        this.recyclerViewClickListener = recyclerViewClickListener;
        this.context = context;
        categoryItemAdapter = new CategoryItemAdapter(context, mainCategoryItemList,recyclerViewClickListener);
        categoryFragmentView.setCategoryItemRecyclerView(categoryItemAdapter);

    }

    public void getImagesForAds() {
        categoryFragmentModel = new CategoryFragmentModel();
        adsImagesList = categoryFragmentModel.getAdsImages();
        categoryFragmentView.loadAds(adsImagesList);

    }

    public void getCategoryItemsDetails(ArrayList<MainCategoryItemList> mainCategoryItemList) {
        this.mainCategoryItemList = mainCategoryItemList;
        byte a=1;
        categoryItemAdapter.addCategoryListValues(mainCategoryItemList,a);
        //categoryFragmentView.setCategoryItemRecyclerView(categoryItemAdapter);
        categoryItemAdapter.notifyDataSetChanged();
    }
    public void getSubCategoryItemsDetails(ArrayList<SubCategoryItemList> subCategoryItemList) {
        this.subCategoryItemList = subCategoryItemList;
        byte a=2;
        categoryItemAdapter.addSubCategoryListValues(subCategoryItemList,a);
        //categoryFragmentView.setCategoryItemRecyclerView(categoryItemAdapter);
        categoryItemAdapter.notifyDataSetChanged();
    }

}
