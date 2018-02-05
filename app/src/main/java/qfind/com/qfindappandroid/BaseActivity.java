package qfind.com.qfindappandroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import qfind.com.qfindappandroid.categoryfragment.CategoryFragment;
import qfind.com.qfindappandroid.favoritePage.FavoriteFragment;
import qfind.com.qfindappandroid.historyPage.HistoryFragment;
import qfind.com.qfindappandroid.informationFragment.InformationFragment;
import qfind.com.qfindappandroid.searchResultsFragment.SearchResultsFragment;
import qfind.com.qfindappandroid.settingspagefragment.SettingsFragment;
import qfind.com.qfindappandroid.termsandconditionfragment.TermsandConditionFragment;


public class BaseActivity extends AppCompatActivity {


    protected LinearLayout sideMenuAboutUsLayout, sideMenuQFinderLayout, sideMenuTermsAndConditionLayout,
            sideMenuContactUsLayout, sideMenuSettingsLayout;

    TextView sideMenuTittleTxt;

    TextView sideMenuAboutUsTxt;
    TextView sideMenuQfinderTxt;

    TextView sideMenuTermAndConditionTxt;

    TextView sideMenuContactUsTxt;

    TextView sideMenuSettingsTxt;
    Toolbar toolbar;
    Fragment fragment;
    protected DrawerLayout fullView;
    ImageView sideMenuHamburger, hamburger, infoHamburger, infoBackButton;
    ArrayAdapter<String> adapter;
    ImageView searchButton;
    protected AutoCompleteTextView autoCompleteTextView;
    View keyboard;
    InputMethodManager imm;
    BottomNavigationView bottomNavigationView;
    FrameLayout activityContainer;
    ActionBarDrawerToggle toggle;
    Typeface mTypeFace;
    LinearLayout infoToolbar, normalToolbar;
    TextView infoToolBarMainTittleTxtView;
    String infoToolBarTittle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        activityContainer = (FrameLayout) fullView.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(fullView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (useToolbar()) {
            setSupportActionBar(toolbar);
        } else {
            toolbar.setVisibility(View.GONE);
        }

        toggle = new ActionBarDrawerToggle(
                this, fullView, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        fullView.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.syncState();
        sideMenuAboutUsLayout = (LinearLayout) fullView.findViewById(R.id.about_us_layout);
        sideMenuQFinderLayout = (LinearLayout) fullView.findViewById(R.id.qfinder_layout);
        sideMenuTermsAndConditionLayout = (LinearLayout) fullView.findViewById(R.id.terms_and_condition_layout);
        sideMenuContactUsLayout = (LinearLayout) fullView.findViewById(R.id.contact_us_layout);
        sideMenuSettingsLayout = (LinearLayout) fullView.findViewById(R.id.settings_layout);
        sideMenuHamburger = (ImageView) fullView.findViewById(R.id.side_menu_hamburger);
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteEditText);
        searchButton = (ImageView) findViewById(R.id.search_icon);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setItemIconTintList(null);
        hamburger = (ImageView) findViewById(R.id.sideMenu);
        sideMenuTittleTxt = (TextView) findViewById(R.id.side_menu_tittle_txt);
        sideMenuAboutUsTxt = (TextView) findViewById(R.id.side_menu_about_us_txt);
        sideMenuQfinderTxt = (TextView) findViewById(R.id.side_menu_qfinder_txt);
        sideMenuTermAndConditionTxt = (TextView) findViewById(R.id.side_menu_terms_condition_txt);
        sideMenuContactUsTxt = (TextView) findViewById(R.id.side_menu_contact_us_txt);
        sideMenuSettingsTxt = (TextView) findViewById(R.id.side_menu_settings_txt);
        infoHamburger = (ImageView) findViewById(R.id.hamburger_info);
        infoToolbar = (LinearLayout) findViewById(R.id.info_toolbar);
        infoToolBarMainTittleTxtView = (TextView) findViewById(R.id.main_title);
        normalToolbar = (LinearLayout) findViewById(R.id.normal_toolbar);
        infoBackButton = (ImageView) findViewById(R.id.back_button_info);

        setupSideMenuItemClickListener();
        setFontTypeForText();

        sideMenuHamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerOpenCloseHandler();
            }
        });

        hamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerOpenCloseHandler();
            }
        });
        infoHamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerOpenCloseHandler();
            }
        });
        infoBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        String[] FINDINGS = new String[]{
                "Hotel", "Hotel", "Hotel", "Hotel", "Bar", "Dentist", "Exterior Designer",
                "Restaurant", "الفندق", "الفندق", "الفندق"
        };
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, FINDINGS);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setDropDownBackgroundResource(R.color.color_white);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!autoCompleteTextView.getText().toString().equals("")) {
                    keyboard = getCurrentFocus();
                    if (keyboard != null) {
                        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(keyboard.getWindowToken(), 0);
                    }
                    fragment = new SearchResultsFragment();
                    loadFragment(fragment);
                } else {
                    Toast.makeText(BaseActivity.this, R.string.please_type, Toast.LENGTH_SHORT).show();
                }
            }
        });
        if (useBottomBar()) {
            bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        } else {
            bottomNavigationView.setVisibility(View.GONE);
        }
    }

    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            fragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
            switch (item.getItemId()) {
                case R.id.favorite_categories_bottom_menu:
                    if (!(fragment instanceof FavoriteFragment)) {
                        item.setCheckable(true);
                        fragment = new FavoriteFragment();
                    } else
                        fragment = null;
                    break;
                case R.id.qfind_us_menu:
                    item.setCheckable(true);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    fragment = null;
                    break;
                case R.id.category_history_menu:
                    if (!(fragment instanceof HistoryFragment)) {
                        item.setCheckable(true);
                        fragment = new HistoryFragment();
                    } else
                        fragment = null;
                    break;
            }
            if (fragment != null) {

                loadFragment(fragment);
                showNormalToolbar();
            }
            return true;
        }

    };


    @Override
    public void onBackPressed() {
        if (fullView.isDrawerOpen(Gravity.END)) {
            fullView.closeDrawer(Gravity.END);
        } else {
            if (autoCompleteTextView.getText() != null) {
                autoCompleteTextView.setText(null);
                autoCompleteTextView.clearFocus();
            }
            showNormalToolbar();
            super.onBackPressed();
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected())
            return true;
        else {
            Util.showToast(getResources().getString(R.string.check_network), getApplicationContext());
            return false;
        }

    }

    protected boolean useToolbar() {
        return true;
    }

    protected boolean useBottomBar() {
        return true;
    }

    public void drawerOpenCloseHandler() {
        if (fullView.isDrawerOpen(Gravity.END)) {
            fullView.closeDrawer(Gravity.END);
        } else {
            fullView.openDrawer(Gravity.END);
        }
    }

    public void setupSideMenuItemClickListener() {
        sideMenuAboutUsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullView.closeDrawer(GravityCompat.END);
            }
        });
        sideMenuQFinderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullView.closeDrawer(GravityCompat.END);
            }
        });
        sideMenuTermsAndConditionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
                if (!(fragment instanceof TermsandConditionFragment)) {
                    BaseActivity.this.fragment = new TermsandConditionFragment();
                    loadFragment(BaseActivity.this.fragment);
                }
                fullView.closeDrawer(GravityCompat.END);
                showNormalToolbar();
            }
        });
        sideMenuContactUsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullView.closeDrawer(GravityCompat.END);
            }
        });
        sideMenuSettingsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
                if (!(fragment instanceof SettingsFragment)) {
                    BaseActivity.this.fragment = new SettingsFragment();
                    loadFragment(BaseActivity.this.fragment);
                }
                fullView.closeDrawer(GravityCompat.END);
                showNormalToolbar();
            }
        });

    }

    public void showInfoToolbar(String tittle) {

        infoToolBarTittle = tittle;
        normalToolbar.setVisibility(View.GONE);
        infoToolbar.setVisibility(View.VISIBLE);
        infoToolBarMainTittleTxtView.setText(infoToolBarTittle);

    }

    public void showNormalToolbar() {
        normalToolbar.setVisibility(View.VISIBLE);
        infoToolbar.setVisibility(View.GONE);
    }


    public void loadFragment(Fragment fragment) {
        if (isNetworkAvailable()) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_container, fragment, Integer.toString(getFragmentCount()));
            transaction.addToBackStack(null);
            transaction.commit();
            if (!(fragment instanceof SearchResultsFragment) && autoCompleteTextView.getText() != null) {
                autoCompleteTextView.setText(null);
                autoCompleteTextView.clearFocus();
            }
        }
    }

    public void loadFragmentWithoutBackStack(Fragment fragment) {
        if (isNetworkAvailable()) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_container, fragment);
            transaction.commit();
            if (!(fragment instanceof SearchResultsFragment) && autoCompleteTextView.getText() != null) {
                autoCompleteTextView.setText(null);
                autoCompleteTextView.clearFocus();
            }
        }
    }

    protected int getFragmentCount() {
        return getSupportFragmentManager().getBackStackEntryCount();
    }

    private Fragment getFragmentAt(int index) {
        return getFragmentCount() > 0 ? getSupportFragmentManager().findFragmentByTag(Integer.toString(index)) : null;
    }

    protected Fragment getCurrentFragment() {
        return getFragmentAt(getFragmentCount() - 2);
    }

    public void setFontTypeForText() {
        if (getResources().getConfiguration().locale.getLanguage().equals("en")) {
            mTypeFace = Typeface.createFromAsset(getAssets(),
                    "fonts/Lato-Bold.ttf");
            sideMenuTittleTxt.setTypeface(mTypeFace);
            mTypeFace = Typeface.createFromAsset(getAssets(),
                    "fonts/Lato-Light.ttf");
        } else {
            mTypeFace = Typeface.createFromAsset(getAssets(),
                    "fonts/GE_SS_Unique_Light.otf");
        }
        sideMenuAboutUsTxt.setTypeface(mTypeFace);
        sideMenuQfinderTxt.setTypeface(mTypeFace);
        sideMenuTermAndConditionTxt.setTypeface(mTypeFace);
        sideMenuContactUsTxt.setTypeface(mTypeFace);
        sideMenuSettingsTxt.setTypeface(mTypeFace);
    }

    public void setupBottomNavigationBar() {
        fragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
        bottomNavigationView.setSelected(false);
        if ((fragment instanceof HistoryFragment)) {
            bottomNavigationView.getMenu().findItem(R.id.category_history_menu).setCheckable(true);
            bottomNavigationView.getMenu().findItem(R.id.favorite_categories_bottom_menu).setCheckable(false);
        } else if ((fragment instanceof FavoriteFragment)) {
            bottomNavigationView.getMenu().findItem(R.id.favorite_categories_bottom_menu).setCheckable(true);
            bottomNavigationView.getMenu().findItem(R.id.category_history_menu).setCheckable(false);
        } else {
            Menu menu = bottomNavigationView.getMenu();
            for (int i = 0, size = menu.size(); i < size; i++) {
                MenuItem item = menu.getItem(i);
                item.setCheckable(false);
            }
        }

    }

}
