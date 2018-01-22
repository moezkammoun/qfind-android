package qfind.com.qfindappandroid.categorycontaineractivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import qfind.com.qfindappandroid.AppConfig;
import qfind.com.qfindappandroid.BaseActivity;
import qfind.com.qfindappandroid.MainActivity;
import qfind.com.qfindappandroid.R;
import qfind.com.qfindappandroid.categoryfragment.CategoryFragment;
import qfind.com.qfindappandroid.historyPage.HistoryFragment;
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
