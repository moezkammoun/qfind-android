package qfind.com.qfindappandroid;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lightsky.infiniteindicator.IndicatorConfiguration;
import cn.lightsky.infiniteindicator.InfiniteIndicator;
import cn.lightsky.infiniteindicator.OnPageClickListener;
import cn.lightsky.infiniteindicator.Page;
import qfind.com.qfindappandroid.categorycontaineractivity.ContainerActivity;
import qfind.com.qfindappandroid.categoryfragment.CategoryFragmentModel;
import qfind.com.qfindappandroid.categoryfragment.PicassoLoader;
import qfind.com.qfindappandroid.searchResultsFragment.SearchResultsFragment;
import qfind.com.qfindappandroid.settingspagefragment.SettingsFragment;
import qfind.com.qfindappandroid.termsandconditionfragment.TermsandConditionFragment;

import static cn.lightsky.infiniteindicator.IndicatorConfiguration.LEFT;
import static cn.lightsky.infiniteindicator.IndicatorConfiguration.RIGHT;


public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, OnPageClickListener, NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    android.support.v7.widget.Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    @BindView(R.id.findByCategoryBtn)
    Button findByCategoryBtn;
    @BindView(R.id.hamburger_menu)
    ImageView hamburgerMenu;
    @BindView(R.id.side_menu_hamburger)
    ImageView sideMenuHamburger;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.side_menu_tittle_txt)
    TextView sideMenuTittleTxt;
    @BindView(R.id.side_menu_about_us_txt)
    TextView sideMenuAboutUsTxt;
    @BindView(R.id.side_menu_qfinder_txt)
    TextView sideMenuQfinderTxt;
    @BindView(R.id.side_menu_terms_condition_txt)
    TextView sideMenuTermAndConditionTxt;
    @BindView(R.id.side_menu_contact_us_txt)
    TextView sideMenuContactUsTxt;
    @BindView(R.id.side_menu_settings_txt)
    TextView sideMenuSettingsTxt;
    @BindView(R.id.about_us_layout)
    LinearLayout sideMenuAboutUslayout;
    @BindView(R.id.qfinder_layout)
    LinearLayout sideMenuQfinderlayout;
    @BindView(R.id.terms_and_condition_layout)
    LinearLayout sideMenuTermsandConditionlayout;
    @BindView(R.id.contact_us_layout)
    LinearLayout sideMenuContactUslayout;
    @BindView(R.id.settings_layout)
    LinearLayout sideMenuSettingslayout;
    @BindView(R.id.search_icon)
    ImageView searchButton;
    @BindView(R.id.autoCompleteEditText)
    AutoCompleteTextView autoCompleteTextView;
    Fragment fragment;
    Locale myLocale;
    private InfiniteIndicator mAnimCircleIndicator;
    Typeface mTypeFace;
    Intent navigationIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        mAnimCircleIndicator = (InfiniteIndicator) findViewById(R.id.indicator_default_circle);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.syncState();

        int width = getResources().getDisplayMetrics().widthPixels / 3;
        DrawerLayout.LayoutParams params = (android.support.v4.widget.DrawerLayout.LayoutParams) navigationView.getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels - width;
        navigationView.setLayoutParams(params);
        setupHamburgerClickListener();
        setupSideMenuItemClickListener();

        String[] FINDINGS = new String[]{
                "Hotel", "Hotel", "Hotel", "Hotel", "Bar", "Dentist", "Exterior Designer", "Restaurant"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, FINDINGS);
        autoCompleteTextView.setAdapter(adapter);

        hamburgerMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawer.isDrawerOpen(Gravity.END)) {
                    drawer.closeDrawer(Gravity.END);
                } else {
                    drawer.openDrawer(Gravity.END);
                }
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!autoCompleteTextView.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "Finding...", Toast.LENGTH_SHORT).show();
                    navigationIntent = new Intent(MainActivity.this, ContainerActivity.class);
                    navigationIntent.putExtra("SHOW_RESULTS", true);
                    startActivity(navigationIntent);
                }
            }
        });
        findByCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigationIntent = new Intent(MainActivity.this, ContainerActivity.class);
                navigationIntent.putExtra("SHOW_RESULTS", false);
                startActivity(navigationIntent);
            }
        });

        loadAdsToSlider();
        setFontTypeForText();

    }
    public void setFontTypeForText() {
        if (getResources().getConfiguration().locale.getLanguage().equals("en")) {
            mTypeFace = Typeface.createFromAsset(this.getAssets(),
                    "fonts/Lato-Regular.ttf");
        } else {
            mTypeFace = Typeface.createFromAsset(this.getAssets(),
                    "fonts/GE_SS_Unique_Light.otf");
        }

        autoCompleteTextView.setTypeface(mTypeFace);
    }

    public void drawerOpenCloseHandler() {
        if (drawer.isDrawerOpen(Gravity.END)) {
            drawer.closeDrawer(Gravity.END);
        } else {
            drawer.openDrawer(Gravity.END);
        }
    }

    public void setupHamburgerClickListener() {
        hamburgerMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerOpenCloseHandler();
            }
        });
        sideMenuHamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerOpenCloseHandler();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(Gravity.END)) {
            drawer.closeDrawer(Gravity.END);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }

    public void setupSideMenuItemClickListener() {
        sideMenuAboutUslayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragment = new SearchResultsFragment();
                loadFragment();
                drawer.closeDrawer(GravityCompat.END);
            }
        });
        sideMenuQfinderlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drawer.closeDrawer(GravityCompat.END);
            }
        });
        sideMenuTermsandConditionlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment f = getSupportFragmentManager().findFragmentById(R.id.frame_container);
                if (!(f instanceof TermsandConditionFragment)) {
                    fragment = new TermsandConditionFragment();
                    loadFragment();
                }
                drawer.closeDrawer(GravityCompat.END);
            }
        });
        sideMenuContactUslayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drawer.closeDrawer(GravityCompat.END);
            }
        });
        sideMenuSettingslayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment f = getSupportFragmentManager().findFragmentById(R.id.frame_container);
                if (!(f instanceof SettingsFragment)) {
                    fragment = new SettingsFragment();
                    loadFragment();
                }

                drawer.closeDrawer(GravityCompat.END);
            }
        });

    }

    public void loadFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void loadAdsToSlider() {
        ArrayList<Page> adsImages;
        CategoryFragmentModel categoryFragmentModel = new CategoryFragmentModel();
        adsImages = categoryFragmentModel.getAdsImages();
        if (getResources().getConfiguration().locale.getLanguage().equals("en")) {
            IndicatorConfiguration configuration = new IndicatorConfiguration.Builder()
                    .imageLoader(new PicassoLoader())
                    .isStopWhileTouch(true)
                    .onPageChangeListener(this)
                    .scrollDurationFactor(6)
                    .internal(3000)
                    .isLoop(true)
                    .isAutoScroll(true)
                    .onPageClickListener(this)
                    .direction(LEFT)
                    .position(IndicatorConfiguration.IndicatorPosition.Center_Bottom)
                    .build();
            mAnimCircleIndicator.init(configuration);
            mAnimCircleIndicator.notifyDataChange(adsImages);
        } else {
            IndicatorConfiguration configuration = new IndicatorConfiguration.Builder()
                    .imageLoader(new PicassoLoader())
                    .isStopWhileTouch(true)
                    .onPageChangeListener(this)
                    .internal(3000)
                    .scrollDurationFactor(6)
                    .isLoop(true)
                    .isAutoScroll(true)
                    .onPageClickListener(this)
                    .direction(RIGHT)
                    .position(IndicatorConfiguration.IndicatorPosition.Center_Bottom)
                    .build();
            mAnimCircleIndicator.init(configuration);
            mAnimCircleIndicator.notifyDataChange(adsImages);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPageClick(int position, Page page) {

    }
}
