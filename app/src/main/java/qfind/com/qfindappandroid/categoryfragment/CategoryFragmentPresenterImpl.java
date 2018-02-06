package qfind.com.qfindappandroid.categoryfragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

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
    ArrayList<Page> adsImagesList;
    RecyclerViewClickListener recyclerViewClickListener;
    ArrayList<MainCategoryItemList> mainCategoryItemList;
    ArrayList<SubCategoryItemList> subCategoryItemList;
    ArrayList<ServiceProviderListDetails> serviceProviderListDetails;
    Context context;
    SharedPreferences qFindPreferences;
    ArrayList<Page> ads;

    public CategoryFragmentPresenterImpl(Context context, CategoryFragmentView categoryFragmentView, RecyclerViewClickListener recyclerViewClickListener) {
        this.categoryFragmentView = categoryFragmentView;
        this.recyclerViewClickListener = recyclerViewClickListener;
        this.context = context;
        categoryItemAdapter = new CategoryItemAdapter(context, mainCategoryItemList, recyclerViewClickListener);
        categoryFragmentView.setCategoryItemRecyclerView(categoryItemAdapter);

    }

    public void getImagesForAds() {
        qFindPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        ads = new ArrayList<>();
        for (int i = 0; i < qFindPreferences.getInt("COUNT", 0); i++) {
            ads.add(new Page("", qFindPreferences.getString("AD" + (i + 1), null)));
        }
        categoryFragmentView.loadAds(ads);
    }

    public void getCategoryItemsDetails(ArrayList<MainCategoryItemList> mainCategoryItemList) {
        this.mainCategoryItemList = mainCategoryItemList;
        byte a = 1;
        categoryItemAdapter.addCategoryListValues(mainCategoryItemList, a);
        categoryItemAdapter.notifyDataSetChanged();
    }

    public void getSubCategoryItemsDetails(ArrayList<SubCategoryItemList> subCategoryItemList) {
        this.subCategoryItemList = subCategoryItemList;
        byte a = 2;
        categoryItemAdapter.addSubCategoryListValues(subCategoryItemList, a);
        categoryItemAdapter.notifyDataSetChanged();
    }

    public void getServieProvidersList(ArrayList<ServiceProviderListDetails> serviceProviderListDetails) {
        this.serviceProviderListDetails = serviceProviderListDetails;
        byte a = 3;
        categoryItemAdapter.addServiceProviderList(serviceProviderListDetails, a);
        categoryItemAdapter.notifyDataSetChanged();
    }

}
