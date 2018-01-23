package qfind.com.qfindappandroid.categorycontaineractivity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;

import butterknife.ButterKnife;
import qfind.com.qfindappandroid.AppConfig;
import qfind.com.qfindappandroid.BaseActivity;
import qfind.com.qfindappandroid.R;
import qfind.com.qfindappandroid.categoryfragment.CategoryFragment;
import qfind.com.qfindappandroid.searchResultsFragment.SearchResultsFragment;
import qfind.com.qfindappandroid.settingspagefragment.SettingsFragment;
import qfind.com.qfindappandroid.termsandconditionfragment.TermsandConditionFragment;


public class ContainerActivity extends BaseActivity implements ContainerActivityView {

    ContainerActivityPresenter containerActivityPresenter = new ContainerActivityPresenter();
    Fragment fragment;
    Intent intent;
    String fragmentToShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        ButterKnife.bind(this);
        containerActivityPresenter.loadFragmentOncreate(this, new CategoryFragment());

        intent = getIntent();
        fragmentToShow = intent.getStringExtra("SHOW_FRAGMENT");
        if (fragmentToShow.equals(AppConfig.Fragments.SEARCH_RESULTS.toString())) {
            fragment = new SearchResultsFragment();
            containerActivityPresenter.loadFragmentOnButtonClick(fragment);

        } else if (fragmentToShow.equals(AppConfig.Fragments.SETTINGS.toString())) {
            fragment = new SettingsFragment();
            containerActivityPresenter.loadFragmentOnButtonClick(fragment);
        } else if (fragmentToShow.equals(AppConfig.Fragments.TERMS_AND_CONDITIONS.toString())) {
            fragment = new TermsandConditionFragment();
            containerActivityPresenter.loadFragmentOnButtonClick(fragment);
        } else if (fragmentToShow.equals(AppConfig.Fragments.CATEGORIES.toString())) {

        }
    }

    @Override
    public void onBackPressed() {
        if (fullView.isDrawerOpen(Gravity.END)) {
            fullView.closeDrawer(Gravity.END);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}
