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
import qfind.com.qfindappandroid.InformationPage.InformationPage;
import qfind.com.qfindappandroid.MainActivity;
import qfind.com.qfindappandroid.R;
import qfind.com.qfindappandroid.categoryfragment.CategoryFragment;
import qfind.com.qfindappandroid.historyPage.HistoryFragment;
import qfind.com.qfindappandroid.searchResultsFragment.SearchResultsFragment;
import qfind.com.qfindappandroid.settingspagefragment.SettingsFragment;
import qfind.com.qfindappandroid.termsandconditionfragment.TermsandConditionFragment;

public class ContainerActivity extends AppCompatActivity

        implements ContainerActivityView {


    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.sideMenu)
    ImageView hambergerMenu;
    @BindView(R.id.side_menu_hamburger)
    ImageView sideMenuHamburger;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
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
    public Typeface mtypeFace;
    @BindView(R.id.search_icon)
    ImageView searchButton;
    @BindView(R.id.autoCompleteEditText)
    AutoCompleteTextView autoCompleteTextView;
    ContainerActivityPresenter containerActivityPresenter = new ContainerActivityPresenter();
    Fragment fragment;
    View keyboard;
    InputMethodManager imm;
    Intent intent;
    Boolean isSearchResults;
    ArrayAdapter<String> adapter;

//<<<<<<< HEAD
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.favorite_categories_bottom_menu:
////                    bottomNavigationMenu.findItem(R.id.qfind_us_menu).setIcon(R.drawable.ic_home_black_24dp);
////                    bottomNavigationMenu.findItem(R.id.category_history_menu).setIcon(R.drawable.ic_home_black_24dp);
//                    fragment = new CategoryFragment();
//                    break;
//                case R.id.qfind_us_menu:
////                    fragment = new CategoryFragment();
//                    Intent intent= new Intent(getApplicationContext(), InformationPage.class);
//                    startActivity(intent);
//                    break;
//                case R.id.category_history_menu:
//                    fragment = new HistoryFragment();
//                    break;
//            }
//            if (fragment != null) {
//                containerActivityPresenter.loadFragmentOnButtonClick(fragment);
//            }
//            return true;
//        }
//=======
//>>>>>>> master


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        ButterKnife.bind(this);
        toolbar.setNavigationIcon(null);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.syncState();
        setFontTypeForText();
        int width = getResources().getDisplayMetrics().widthPixels / 3;
        DrawerLayout.LayoutParams params = (android.support.v4.widget.DrawerLayout.LayoutParams) navigationView.getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels - width;
        navigationView.setLayoutParams(params);

        containerActivityPresenter.loadFragmentOncreate(this, new CategoryFragment());
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.removeShiftMode(navigation);
        setupHamburgerClickListener();
        setupSideMenuItemClickListener();

        String[] FINDINGS = new String[]{
                "Hotel", "Hotel", "Hotel", "Hotel", "Bar", "Dentist", "Exterior Designer", "Restaurant"
        };
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, FINDINGS);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setDropDownBackgroundResource(R.color.color_white);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!autoCompleteTextView.getText().toString().equals("")) {
                    Toast.makeText(ContainerActivity.this, "Finding...", Toast.LENGTH_SHORT).show();
                    keyboard = getCurrentFocus();
                    if (keyboard != null) {
                        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(keyboard.getWindowToken(), 0);
                    }
                    fragment = new SearchResultsFragment();
                    containerActivityPresenter.loadFragmentOnButtonClick(fragment);
                }
            }
        });

        intent = getIntent();
        isSearchResults = intent.getBooleanExtra("SHOW_RESULTS", false);
        if (isSearchResults) {
            fragment = new SearchResultsFragment();
            containerActivityPresenter.loadFragmentOnButtonClick(fragment);
        }
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
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

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.favorite_categories_bottom_menu:
                    fragment = new CategoryFragment();
                    break;
                case R.id.qfind_us_menu:
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.category_history_menu:
                    fragment = new HistoryFragment();
                    break;
            }
            if (fragment != null) {
                containerActivityPresenter.loadFragmentOnButtonClick(fragment);
            }
            return true;
        }

    };

    public void drawerOpenCloseHandler() {
        if (drawer.isDrawerOpen(Gravity.END)) {
            drawer.closeDrawer(Gravity.END);
        } else {
            drawer.openDrawer(Gravity.END);
        }
    }

    public void setupHamburgerClickListener() {
        hambergerMenu.setOnClickListener(new View.OnClickListener() {
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

    public void setFontTypeForText() {
        if (getResources().getConfiguration().locale.getLanguage().equals("en")) {
            mtypeFace = Typeface.createFromAsset(getAssets(),
                    "fonts/Lato-Bold.ttf");
            sideMenuTittleTxt.setTypeface(mtypeFace);
            mtypeFace = Typeface.createFromAsset(getAssets(),
                    "fonts/Lato-Light.ttf");
        } else {
            mtypeFace = Typeface.createFromAsset(getAssets(),
                    "fonts/GE_SS_Unique_Light.otf");
        }
        sideMenuAboutUsTxt.setTypeface(mtypeFace);
        sideMenuQfinderTxt.setTypeface(mtypeFace);
        sideMenuTermAndConditionTxt.setTypeface(mtypeFace);
        sideMenuContactUsTxt.setTypeface(mtypeFace);
        sideMenuSettingsTxt.setTypeface(mtypeFace);
    }

    public void setupSideMenuItemClickListener() {
        sideMenuAboutUslayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragment = new SearchResultsFragment();
                containerActivityPresenter.loadFragmentOnButtonClick(fragment);
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
                    containerActivityPresenter.loadFragmentOnButtonClick(fragment);
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
                    containerActivityPresenter.loadFragmentOnButtonClick(fragment);
                }

                drawer.closeDrawer(GravityCompat.END);
            }
        });

    }


}
