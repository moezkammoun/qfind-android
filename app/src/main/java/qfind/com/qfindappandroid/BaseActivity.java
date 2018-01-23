package qfind.com.qfindappandroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.Typeface;
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

import qfind.com.qfindappandroid.InformationPage.InformationPage;
import qfind.com.qfindappandroid.categoryfragment.CategoryFragment;
import qfind.com.qfindappandroid.favoritePage.FavoriteFragment;
import qfind.com.qfindappandroid.historyPage.HistoryFragment;
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
    ImageView sideMenuHamburger, hamburger;
    ArrayAdapter<String> adapter;
    ImageView searchButton;
    AutoCompleteTextView autoCompleteTextView;
    View keyboard;
    InputMethodManager imm;
    BottomNavigationView bottomNavigationView;
    FrameLayout activityContainer;
    ActionBarDrawerToggle toggle;
    Typeface mTypeFace;
    //NavigationView navigationView;

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
        //navigationView = (NavigationView) fullView.findViewById(R.id.nav_view);
        //setNavigationViewSize();
        sideMenuAboutUsLayout = (LinearLayout) fullView.findViewById(R.id.about_us_layout);
        sideMenuQFinderLayout = (LinearLayout) fullView.findViewById(R.id.qfinder_layout);
        sideMenuTermsAndConditionLayout = (LinearLayout) fullView.findViewById(R.id.terms_and_condition_layout);
        sideMenuContactUsLayout = (LinearLayout) fullView.findViewById(R.id.contact_us_layout);
        sideMenuSettingsLayout = (LinearLayout) fullView.findViewById(R.id.settings_layout);
        sideMenuHamburger = (ImageView) fullView.findViewById(R.id.side_menu_hamburger);
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteEditText);
        searchButton = (ImageView) findViewById(R.id.search_icon);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        hamburger = (ImageView) findViewById(R.id.sideMenu);
        sideMenuTittleTxt = (TextView) findViewById(R.id.side_menu_tittle_txt);
        sideMenuAboutUsTxt = (TextView) findViewById(R.id.side_menu_about_us_txt);
        sideMenuQfinderTxt = (TextView) findViewById(R.id.side_menu_qfinder_txt);
        sideMenuTermAndConditionTxt = (TextView) findViewById(R.id.side_menu_terms_condition_txt);
        sideMenuContactUsTxt = (TextView) findViewById(R.id.side_menu_contact_us_txt);
        sideMenuSettingsTxt = (TextView) findViewById(R.id.side_menu_settings_txt);

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
                    Toast.makeText(BaseActivity.this, "Finding...", Toast.LENGTH_SHORT).show();
                    keyboard = getCurrentFocus();
                    if (keyboard != null) {
                        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(keyboard.getWindowToken(), 0);
                    }
                    fragment = new SearchResultsFragment();
                    loadFragment(fragment);
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
            switch (item.getItemId()) {
                case R.id.favorite_categories_bottom_menu:
                    fragment = new FavoriteFragment();
                    break;
                case R.id.qfind_us_menu:
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.category_history_menu:
                    fragment = new HistoryFragment();
                    break;
            }
            if (fragment != null) {
                loadFragment(fragment);
            }
            return true;
        }

    };

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
            }
        });

    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
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
    public void setNavigationViewSize(){
//        Rect rectangle = new Rect();
//        Window window = getWindow();
//        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
//        int statusBarHeight = rectangle.top;
////        int contentViewTop =
////                window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
////        int titleBarHeight= contentViewTop - statusBarHeight;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//            params.setMargins(0, statusBarHeight, 0, 0);
//            navigationView.setLayoutParams(params);
//        }
    }

}
