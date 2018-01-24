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
import qfind.com.qfindappandroid.categoryfragment.CategoryPageCurrentStatus;
import qfind.com.qfindappandroid.searchResultsFragment.SearchResultsFragment;
import qfind.com.qfindappandroid.settingspagefragment.SettingsFragment;
import qfind.com.qfindappandroid.termsandconditionfragment.TermsandConditionFragment;


public class ContainerActivity extends BaseActivity implements ContainerActivityView {

    ContainerActivityPresenter containerActivityPresenter = new ContainerActivityPresenter();
    Fragment fragment;
    Intent intent;
    String fragmentToShow, searchText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        ButterKnife.bind(this);

        //containerActivityPresenter.loadFragmentOncreate(this, new CategoryFragment());
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
    }

    @Override
    public void onBackPressed() {
        if (fullView.isDrawerOpen(Gravity.END)) {
            fullView.closeDrawer(Gravity.END);
        } else if(CategoryPageCurrentStatus.categoryPageStatus==2){
          //  ((CategoryFragment) fragment).setSubCategoryBackButtonClickAction();
            CategoryFragment fragment = (CategoryFragment) getSupportFragmentManager().findFragmentById(R.id.frame_container);
            fragment.setSubCategoryBackButtonClickAction();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

//    @Override
//    public void loadFragment(Fragment fragment) {
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.frame_container, fragment);
//        transaction.commit();
//
//    }

    public void loadFragmentWithoutBackStack(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }

}
