package qfind.com.qfindappandroid.categorycontaineractivity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.Gravity;

import java.util.ArrayList;

import butterknife.ButterKnife;
import qfind.com.qfindappandroid.AppConfig;
import qfind.com.qfindappandroid.BaseActivity;
import qfind.com.qfindappandroid.R;
import qfind.com.qfindappandroid.Util;
import qfind.com.qfindappandroid.categoryfragment.CategoryFragment;
import qfind.com.qfindappandroid.retrofitinstance.ApiClient;
import qfind.com.qfindappandroid.retrofitinstance.ApiInterface;
import qfind.com.qfindappandroid.searchResultsFragment.SearchResultsFragment;
import qfind.com.qfindappandroid.settingspagefragment.SettingsFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ContainerActivity extends BaseActivity implements ContainerActivityView {

    Fragment fragment;
    Intent intent;
    String fragmentToShow, searchText, accessToken;
    SharedPreferences qFindPreferences;
    private Integer searchType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        ButterKnife.bind(this);
        qFindPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        loadFragmentWithoutBackStack(new CategoryFragment());
        //getMainCategoryItemsList();
        intent = getIntent();
        fragmentToShow = intent.getStringExtra("SHOW_FRAGMENT");
        searchText = intent.getStringExtra("SEARCH_TEXT");
        searchType = intent.getIntExtra("SEARCH_TYPE", 0);
        if (fragmentToShow.equals(AppConfig.Fragments.SEARCH_RESULTS.toString())) {
            fragment = new SearchResultsFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("searchType", searchType);
            bundle.putString("searchKey", searchText);
            fragment.setArguments(bundle);
            loadFragmentWithoutBackStack(fragment);
        } else if (fragmentToShow.equals(AppConfig.Fragments.SETTINGS.toString())) {
            fragment = new SettingsFragment();
            loadFragmentWithoutBackStack(fragment);
        }
        if (searchText != null)
            autoCompleteTextView.setText(searchText);


    }

    @Override
    public void onBackPressed() {
        if (fullView.isDrawerOpen(Gravity.END)) {
            fullView.closeDrawer(Gravity.END);
        } else if (Util.categoryPageStatus == 2 || Util.categoryPageStatus == 3) {
            fragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
            if ((fragment instanceof CategoryFragment)) {
                CategoryFragment fragment = (CategoryFragment) getSupportFragmentManager().findFragmentById(R.id.frame_container);
                fragment.setSubCategoryBackButtonClickAction();
                fragment.hideLoader();
            } else {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        setupBottomNavigationBar();
    }

    public void getMainCategoryItemsList() {
        accessToken = qFindPreferences.getString("AccessToken", null);
        if (accessToken != null) {
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<MainCategory> call = apiService.getMainCategory(accessToken, qFindPreferences.getInt("AppLanguage", 1));
            call.enqueue(new Callback<MainCategory>() {
                @Override
                public void onResponse(Call<MainCategory> call, Response<MainCategory> response) {
                    Fragment f = getSupportFragmentManager().findFragmentById(R.id.frame_container);
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            MainCategory mainCategory = response.body();
                            if (mainCategory.getCode().equals("200")) {
                                ArrayList<MainCategoryItemList> mainCategoryItemList = mainCategory.getMainCategoryItemList();
                                if ((f instanceof CategoryFragment)) {
                                    CategoryFragment fragment = (CategoryFragment) getSupportFragmentManager().findFragmentById(R.id.frame_container);
                                    fragment.setRecyclerViewDatas(mainCategoryItemList);
                                    fragment.hideLoader();

                                }
                            } else if (mainCategory.getCode().equals("404")) {
                                if ((f instanceof CategoryFragment)) {
                                    CategoryFragment fragment = (CategoryFragment) getSupportFragmentManager().findFragmentById(R.id.frame_container);
                                    fragment.hideLoader();
                                    fragment.showNoDataText();
                                }

                            } else {
                                if ((f instanceof CategoryFragment)) {
                                    CategoryFragment fragment = (CategoryFragment) getSupportFragmentManager().findFragmentById(R.id.frame_container);
                                    fragment.hideLoader();
                                    fragment.showNoDataText();
                                    //Util.showToast(getResources().getString(R.string.something_went_wrong), getApplicationContext());
                                }
                            }
                        }

                    } else {
                        if ((f instanceof CategoryFragment)) {
                            CategoryFragment fragment = (CategoryFragment) getSupportFragmentManager().findFragmentById(R.id.frame_container);
                            fragment.hideLoader();
                            Util.showToast(getResources().getString(R.string.error_in_connecting), getApplicationContext());
                        }


                    }
                }

                @Override
                public void onFailure(Call<MainCategory> call, Throwable t) {
                    Fragment f = getSupportFragmentManager().findFragmentById(R.id.frame_container);
                    if ((f instanceof CategoryFragment)) {
                        CategoryFragment fragment = (CategoryFragment) getSupportFragmentManager().findFragmentById(R.id.frame_container);
                        fragment.hideLoader();
                        Util.showToast(getResources().getString(R.string.check_network), getApplicationContext());
                    }

                }
            });
        }
    }

}
