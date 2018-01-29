package qfind.com.qfindappandroid.categorycontaineractivity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;

import java.util.ArrayList;

import butterknife.ButterKnife;
import qfind.com.qfindappandroid.AppConfig;
import qfind.com.qfindappandroid.BaseActivity;
import qfind.com.qfindappandroid.R;
import qfind.com.qfindappandroid.Util;
import qfind.com.qfindappandroid.categoryfragment.CategoryFragment;
import qfind.com.qfindappandroid.categoryfragment.CategoryPageCurrentStatus;
import qfind.com.qfindappandroid.informationFragment.InformationFragment;
import qfind.com.qfindappandroid.retrofitinstance.ApiClient;
import qfind.com.qfindappandroid.retrofitinstance.ApiInterface;
import qfind.com.qfindappandroid.searchResultsFragment.SearchResultsFragment;
import qfind.com.qfindappandroid.settingspagefragment.SettingsFragment;
import qfind.com.qfindappandroid.termsandconditionfragment.TermsandConditionFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ContainerActivity extends BaseActivity implements ContainerActivityView {

    ContainerActivityPresenter containerActivityPresenter = new ContainerActivityPresenter();
    Fragment fragment;
    Intent intent;
    String fragmentToShow, searchText, accessToken;
    SharedPreferences qFindPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        ButterKnife.bind(this);
        qFindPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //containerActivityPresenter.loadFragmentOncreate(this, new CategoryFragment());
        getMainCategoryItemsList();
        loadFragmentWithoutBackStack(new CategoryFragment());
        intent = getIntent();
        fragmentToShow = intent.getStringExtra("SHOW_FRAGMENT");
        searchText = intent.getStringExtra("SEARCH_TEXT");
        if (fragmentToShow.equals(AppConfig.Fragments.SEARCH_RESULTS.toString())) {
            fragment = new SearchResultsFragment();
            loadFragmentWithoutBackStack(fragment);
        } else if (fragmentToShow.equals(AppConfig.Fragments.SETTINGS.toString())) {
            fragment = new SettingsFragment();
            loadFragmentWithoutBackStack(fragment);
        } else if (fragmentToShow.equals(AppConfig.Fragments.TERMS_AND_CONDITIONS.toString())) {
            fragment = new TermsandConditionFragment();
            loadFragmentWithoutBackStack(fragment);
        }
        if (searchText != null)
            autoCompleteTextView.setText(searchText);
        checkClickListenerForBackStack();

    }

    @Override
    public void onBackPressed() {
        if (fullView.isDrawerOpen(Gravity.END)) {
            fullView.closeDrawer(Gravity.END);
        }
        if (CategoryPageCurrentStatus.categoryPageStatus == 2) {
            fragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
            if ((fragment instanceof InformationFragment)) {
                super.onBackPressed();
                CategoryFragment fragment = (CategoryFragment) getSupportFragmentManager().findFragmentById(R.id.frame_container);
                fragment.setSubCategoryListOnInfoPageBackClick();

            } else if ((fragment instanceof CategoryFragment)) {
                CategoryFragment fragment = (CategoryFragment) getSupportFragmentManager().findFragmentById(R.id.frame_container);
                fragment.setSubCategoryBackButtonClickAction();
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

//    @Override
//    public void loadFragment(Fragment fragment) {
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.frame_container, fragment);
//        transaction.commit();
//
//    }

    public void loadFragmentWithoutBackStack(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }

    public void checkClickListenerForBackStack() {
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment f = getSupportFragmentManager().findFragmentById(R.id.frame_container);
                if ((f instanceof InformationFragment)) {
                    showInfoToolbar();
                }

            }
        });
    }

    public void getMainCategoryItemsList() {
        accessToken = qFindPreferences.getString("AccessToken", null);
        if (accessToken != null) {
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<MainCategory> call = apiService.getMainCategory(accessToken,qFindPreferences.getInt("AppLanguage", 1));
            call.enqueue(new Callback<MainCategory>() {
                @Override
                public void onResponse(Call<MainCategory> call, Response<MainCategory> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            MainCategory mainCategory = response.body();
                            if (mainCategory.getCode().equals("200")) {
                                ArrayList<MainCategoryItemList> mainCategoryItemList = mainCategory.getMainCategoryItemList();
                                Fragment f = getSupportFragmentManager().findFragmentById(R.id.frame_container);
                                if ((f instanceof CategoryFragment)) {
                                    CategoryFragment fragment = (CategoryFragment) getSupportFragmentManager().findFragmentById(R.id.frame_container);
                                    fragment.setRecyclerViewDatas(mainCategoryItemList);

                                }
                            } else {
                                Util.showToast(getResources().getString(R.string.something_went_wrong), getApplicationContext());
                            }
                        }

                    } else {
                        Util.showToast(getResources().getString(R.string.error_in_connecting), getApplicationContext());

                    }
                }

                @Override
                public void onFailure(Call<MainCategory> call, Throwable t) {
                    Util.showToast(getResources().getString(R.string.check_network), getApplicationContext());
                }
            });
        }
    }
}
