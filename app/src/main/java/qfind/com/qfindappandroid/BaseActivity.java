package qfind.com.qfindappandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import qfind.com.qfindappandroid.favoritePage.FavoriteFragment;
import qfind.com.qfindappandroid.favoritePage.FavoriteModel;
import qfind.com.qfindappandroid.historyPage.HistoryFragment;
import qfind.com.qfindappandroid.homeactivty.SearchData;
import qfind.com.qfindappandroid.informationFragment.InformationFragment;
import qfind.com.qfindappandroid.predictiveSearch.DelayAutoCompleteTextView;
import qfind.com.qfindappandroid.predictiveSearch.SearchAutoCompleteAdapter;
import qfind.com.qfindappandroid.searchResultsFragment.SearchResultsFragment;
import qfind.com.qfindappandroid.settingspagefragment.SettingsFragment;
import qfind.com.qfindappandroid.webviewactivity.WebviewActivity;


public class BaseActivity extends AppCompatActivity {

    String BASE_URL = "http://ec2-18-219-90-185.us-east-2.compute.amazonaws.com/";
    protected LinearLayout sideMenuAboutUsLayout, sideMenuQFinderLayout, sideMenuTermsAndConditionLayout,
            sideMenuContactUsLayout, sideMenuSettingsLayout;
    TextView sideMenuTittleTxt, sideMenuAboutUsTxt, sideMenuQfinderTxt, sideMenuTermAndConditionTxt,
            sideMenuContactUsTxt;
    TextView sideMenuSettingsTxt;
    Locale myLocale;
    int language;
    Toolbar toolbar;
    Fragment fragment;
    protected DrawerLayout fullView;
    ImageView sideMenuHamburger, hamburger, infoHamburger, infoBackButton, infoStarButton;
    protected ImageView infoShareButton;
    ImageView searchButton;
    protected DelayAutoCompleteTextView autoCompleteTextView;
    View keyboard;
    InputMethodManager imm;
    BottomNavigationView bottomNavigationView;
    FrameLayout activityContainer;
    ActionBarDrawerToggle toggle;
    LinearLayout infoToolbar, normalToolbar;
    TextView infoToolBarMainTittleTxtView, infoToolBarSubTittleTxtView;
    String infoToolBarTittle;
    SearchData searchData;
    Bundle bundle = new Bundle();
    SharedPreferences qFindPreferences;
    Typeface mtypeFaceBold, mtypeFaceLight, mtypeFaceItalic;
    NavigationView navView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        qFindPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        setFontTypeForText();
        int appLanguage = qFindPreferences.getInt("AppLanguage", 1);
        if (appLanguage == 2) {
            Configuration configuration = getResources().getConfiguration();
            configuration.setLayoutDirection(new Locale("ar"));
            getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
            myLocale = new Locale("ar");
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
        } else {
            Configuration configuration = getResources().getConfiguration();
            configuration.setLayoutDirection(new Locale("en"));
            getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
            myLocale = new Locale("en");
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        activityContainer = (FrameLayout) fullView.findViewById(R.id.activity_content);
        navView = (NavigationView) fullView.findViewById(R.id.nav_view);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(fullView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setPadding(0, 0, 0, 0);
        toolbar.setContentInsetsAbsolute(0, 0);
        int fullWidth = getResources().getDisplayMetrics().widthPixels;
        int width = getResources().getDisplayMetrics().widthPixels / 3;
        width = fullWidth - width;
        DrawerLayout.LayoutParams params = (android.support.v4.widget.DrawerLayout.LayoutParams) navView.getLayoutParams();
        params.width = width;
        navView.setLayoutParams(params);
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
        infoToolBarSubTittleTxtView = (TextView) findViewById(R.id.sub_title);
        normalToolbar = (LinearLayout) findViewById(R.id.normal_toolbar);
        infoBackButton = (ImageView) findViewById(R.id.back_button_info);
        infoStarButton = (ImageView) findViewById(R.id.fav_star_icon);
        infoShareButton = (ImageView) findViewById(R.id.info_share);
        setupSideMenuItemClickListener();

        infoBackButton.setImageResource(R.drawable.white_back_icon);

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
        infoStarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!infoToolBarMainTittleTxtView.getText().equals("")) {
                    SimpleDateFormat sdfdatetime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
                    DataBaseHandler db = new DataBaseHandler(getApplicationContext());
                    FavoriteModel favoriteModel = new FavoriteModel();
                    favoriteModel.setItem(bundle.getString("providerName"));
                    favoriteModel.setItemDescription(bundle.getString("providerLocation"));
                    favoriteModel.setItemArabic(bundle.getString("providerNameArabic"));
                    favoriteModel.setItemDescriptionArabic(bundle.getString("providerLocationArabic"));
                    favoriteModel.setUrl(bundle.getString("providerLogo"));
                    favoriteModel.setPageId(bundle.getInt("providerId"));
                    favoriteModel.setDatetime(sdfdatetime.format(new Date()));
                    if (db.checkFavoriteById(bundle.getInt("providerId"))) {
                        db.deleteFavorite(bundle.getInt("providerId"));
                        infoStarButton.setImageResource(R.drawable.favorite_blank_star);
                        Util.showToast(getResources().getString(R.string.removed_to_favorites), getApplicationContext());
                    } else {
                        db.addFavorite(favoriteModel);
                        infoStarButton.setImageResource(R.drawable.star_icon);
                        Util.showToast(getResources().getString(R.string.added_to_favorites), getApplicationContext());
                    }
                } else
                    Util.showToast(getResources().getString(R.string.no_data_to_favorite), getApplicationContext());
            }
        });
        infoShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!infoToolBarMainTittleTxtView.getText().equals("")) {
                    try {
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("text/plain");
                        i.putExtra(Intent.EXTRA_SUBJECT, "Q-Find");
                        String sAux = BASE_URL +
                                "site/play-store?provider_id=" + bundle.getInt("providerId");
                        i.putExtra(Intent.EXTRA_TEXT, sAux);
                        startActivity(Intent.createChooser(i, "choose one"));
                    } catch (Exception e) {
                        //e.toString();
                    }
                } else
                    Util.showToast(getResources().getString(R.string.no_data_to_share), getApplicationContext());
            }
        });
        autoCompleteTextView = (DelayAutoCompleteTextView) findViewById(R.id.base_autocomplete_edit_text);
        autoCompleteTextView.setHint(getResources().getString(R.string.search_hint));
        autoCompleteTextView.setThreshold(2);
        autoCompleteTextView.setAdapter(new SearchAutoCompleteAdapter(this));
        autoCompleteTextView.setLoadingIndicator(
                (android.widget.ProgressBar) findViewById(R.id.base_loading_indicator), searchButton);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                searchData = (SearchData) adapterView.getItemAtPosition(position);
                autoCompleteTextView.setText(searchData.getSearchName());
                autoCompleteTextView.clearFocus();
                searchButton.performClick();
            }
        });
        autoCompleteTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    searchButton.performClick();
                    autoCompleteTextView.dismissDropDown();
                    return true;
                }
                return false;
            }
        });
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
                    Bundle bundle = new Bundle();
                    if (searchData != null) {
                        bundle.putInt("searchType", searchData.getSearchType());
                        searchData = null;
                    } else {
                        bundle.putInt("searchType", 4);
                    }
                    bundle.putString("searchKey", autoCompleteTextView.getText().toString());
                    fragment.setArguments(bundle);
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
                language = qFindPreferences.getInt("AppLanguage", 1);
                if (language == 1) {
                    callWebviewWithUrl("http://ec2-18-219-90-185.us-east-2.compute.amazonaws.com/static-pages/1/1"
                    );
                } else {
                    callWebviewWithUrl("http://ec2-18-219-90-185.us-east-2.compute.amazonaws.com/static-pages/1/2"
                    );
                }

            }
        });
        sideMenuQFinderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullView.closeDrawer(GravityCompat.END);
                language = qFindPreferences.getInt("AppLanguage", 1);
                if (language == 1) {
                    callWebviewWithUrl("http://ec2-18-219-90-185.us-east-2.compute.amazonaws.com/static-pages/2/1"
                    );
                } else {
                    callWebviewWithUrl("http://ec2-18-219-90-185.us-east-2.compute.amazonaws.com/static-pages/2/2");
                }

            }
        });
        sideMenuTermsAndConditionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullView.closeDrawer(GravityCompat.END);
                language = qFindPreferences.getInt("AppLanguage", 1);
                if (language == 1) {
                    callWebviewWithUrl("http://ec2-18-219-90-185.us-east-2.compute.amazonaws.com/static-pages/3/1");
                } else {
                    callWebviewWithUrl("http://ec2-18-219-90-185.us-east-2.compute.amazonaws.com/static-pages/3/2"
                    );
                }

            }
        });
        sideMenuContactUsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullView.closeDrawer(GravityCompat.END);
                language = qFindPreferences.getInt("AppLanguage", 1);
                if (language == 1) {
                    callWebviewWithUrl("http://ec2-18-219-90-185.us-east-2.compute.amazonaws.com/static-pages/4/1"
                    );
                } else {
                    callWebviewWithUrl("http://ec2-18-219-90-185.us-east-2.compute.amazonaws.com/static-pages/4/2");
                }

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

    public void showInfoToolbar(String tittle, String subTittle) {

        infoToolBarTittle = tittle;
        normalToolbar.setVisibility(View.GONE);
        infoBackButton.setImageResource(R.drawable.white_back_icon);
        infoToolbar.setVisibility(View.VISIBLE);
        infoToolBarMainTittleTxtView.setTypeface(mtypeFaceBold);
        infoToolBarMainTittleTxtView.setText(infoToolBarTittle);
        infoToolBarSubTittleTxtView.setTypeface(mtypeFaceItalic);
        infoToolBarSubTittleTxtView.setText(subTittle);

    }

    public void hideInfotoolbarBackButton() {
        infoBackButton.setVisibility(View.INVISIBLE);
    }

    public void hideStarandShareButton() {
        infoStarButton.setVisibility(View.INVISIBLE);
        infoShareButton.setVisibility(View.INVISIBLE);
    }

    public void showStarandShareButton() {
        infoStarButton.setVisibility(View.VISIBLE);
        infoShareButton.setVisibility(View.VISIBLE);
    }

    public void isFavoriteSelected(int providerId) {
        DataBaseHandler db = new DataBaseHandler(this);
        Boolean isFavorite = db.checkFavoriteById(providerId);
        SimpleDateFormat sdfdatetime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
        if (isFavorite) {
            infoStarButton.setImageResource(R.drawable.star_icon);
            db.updateFavorite(providerId, sdfdatetime.format(new Date()));
        } else {
            infoStarButton.setImageResource(R.drawable.favorite_blank_star);
        }
    }

    public void showNormalToolbar() {
        infoToolbar.setVisibility(View.GONE);
        normalToolbar.setVisibility(View.VISIBLE);
    }

    public void showServiceProviderDetailPageInner(String providerName, String providerNameArabic, String providerLocation,
                                                   String providerLocationArabic, String providerLogo, int providerId) {
        bundle.putString("providerName", providerName);
        bundle.putString("providerLocation", providerLocation);
        bundle.putString("providerNameArabic", providerNameArabic);
        bundle.putString("providerLocationArabic", providerLocationArabic);
        bundle.putString("providerLogo", providerLogo);
        bundle.putInt("providerId", providerId);
        bundle.putString("callFrom", "others");

        InformationFragment informationFragment = new InformationFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        informationFragment.setArguments(bundle);
        transaction.replace(R.id.frame_container, informationFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    public void showServiceProviderDetailPage(String providerName, String providerLocation,
                                              String providerNameArabic, String providerLocationArabic,
                                              String providerLogo, int providerId, String providerMobile,
                                              String providerAddress, String providerWebsite, String providerMail,
                                              String providerFacebook, String providerLinkedin, String providerInstagram,
                                              String providerTwitter, String providerSnapchat, String providerGooglePlus,
                                              String providerLatLong, String providerAddressArabic,
                                              String[] providerTimeDay,
                                              String[] providerOpeningTime, String[] providerClosingTime,
                                              String[] providerOpeningTimeArabic, String[] providerClosingTimeArabic,
                                              String[] providerOpeningTitle, String[] providerClosingTitle,
                                              String[] providerOpeningTitleArabic, String[] providerClosingTitleArabic) {


        bundle.putString("providerName", providerName);
        bundle.putString("providerLocation", providerLocation);
        bundle.putString("providerNameArabic", providerNameArabic);
        bundle.putString("providerLocationArabic", providerLocationArabic);
        bundle.putString("providerLogo", providerLogo);
        bundle.putInt("providerId", providerId);
        bundle.putString("providerMobile", providerMobile);
        bundle.putString("providerAddress", providerAddress);
        bundle.putString("providerWebsite", providerWebsite);
        bundle.putString("providerMail", providerMail);
        bundle.putString("providerFacebook", providerFacebook);
        bundle.putString("providerLinkedIn", providerLinkedin);
        bundle.putString("providerInstagram", providerInstagram);
        bundle.putString("providerTwitter", providerTwitter);
        bundle.putString("providerSnapchat", providerSnapchat);
        bundle.putString("providerGooglePlus", providerGooglePlus);
        bundle.putString("providerLatLong", providerLatLong);
        bundle.putStringArray("providerOpeningday", providerTimeDay);
        bundle.putStringArray("providerOpeningTime", providerOpeningTime);
        bundle.putStringArray("providerOpeningTimeArabic", providerOpeningTimeArabic);
        bundle.putString("providerAddressArabic", providerAddressArabic);
        bundle.putStringArray("providerClosingTime", providerClosingTime);
        bundle.putStringArray("providerClosingTimeArabic", providerClosingTimeArabic);
        bundle.putStringArray("providerOpeningTitle", providerOpeningTitle);
        bundle.putStringArray("providerClosingTitle", providerClosingTitle);
        bundle.putStringArray("providerOpeningTitleArabic", providerOpeningTitleArabic);
        bundle.putStringArray("providerClosingTitleArabic", providerClosingTitleArabic);
        bundle.putString("callFrom", "category");

        InformationFragment informationFragment = new InformationFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        informationFragment.setArguments(bundle);
        transaction.replace(R.id.frame_container, informationFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void loadFragment(Fragment fragment) {
        if (isNetworkAvailable()) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_container, fragment, Integer.toString(getFragmentCount()));
            if (!(getCurrentFragment() instanceof SearchResultsFragment))
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
        return getFragmentAt(getFragmentCount() - 1);
    }

    public void setFontTypeForText() {
        if (getResources().getConfiguration().locale.getLanguage().equals("en")) {
            mtypeFaceBold = Typeface.createFromAsset(getAssets(),
                    "fonts/Lato-Bold.ttf");
            mtypeFaceLight = Typeface.createFromAsset(getApplicationContext().getAssets(),
                    "fonts/Lato-Light.ttf");
            mtypeFaceItalic = Typeface.createFromAsset(getAssets(),
                    "fonts/Lato-Italic.ttf");

        } else {
            mtypeFaceBold = Typeface.createFromAsset(getApplicationContext().getAssets(),
                    "fonts/GE_SS_Unique_Bold.otf");
            mtypeFaceLight = Typeface.createFromAsset(getAssets(),
                    "fonts/GE_SS_Unique_Light.otf");
        }

        sideMenuTittleTxt.setText(getResources().getString(R.string.menu));
        sideMenuTittleTxt.setTypeface(mtypeFaceBold);
        sideMenuAboutUsTxt.setText(getResources().getString(R.string.about_us));
        sideMenuAboutUsTxt.setTypeface(mtypeFaceLight);
        sideMenuQfinderTxt.setText(getResources().getString(R.string.how_to_become_qfinder));
        sideMenuQfinderTxt.setTypeface(mtypeFaceLight);
        sideMenuTermAndConditionTxt.setText(getResources().getString(R.string.terms_and_conditions));
        sideMenuTermAndConditionTxt.setTypeface(mtypeFaceLight);
        sideMenuContactUsTxt.setText(getResources().getString(R.string.contact_us));
        sideMenuContactUsTxt.setTypeface(mtypeFaceLight);
        sideMenuSettingsTxt.setText(getResources().getString(R.string.settings));
        sideMenuSettingsTxt.setTypeface(mtypeFaceLight);
    }

    public void setupBottomNavigationBar() {
        fragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
        bottomNavigationView.setSelected(true);
        if ((fragment instanceof HistoryFragment)) {
            MenuItem homeItem = bottomNavigationView.getMenu().getItem(2);
            bottomNavigationView.setSelectedItemId(homeItem.getItemId());
            homeItem.setCheckable(true);
            //bottomNavigationView.getMenu().findItem(R.id.category_history_menu).setCheckable(true);
        } else if ((fragment instanceof FavoriteFragment)) {
            // bottomNavigationView.getMenu().findItem(R.id.favorite_categories_bottom_menu).setCheckable(true);
            MenuItem homeItem = bottomNavigationView.getMenu().getItem(0);
            homeItem.setCheckable(true);
            bottomNavigationView.setSelectedItemId(homeItem.getItemId());
        } else {
            Menu menu = bottomNavigationView.getMenu();
            for (int i = 0, size = menu.size(); i < size; i++) {
                MenuItem item = menu.getItem(i);
                item.setCheckable(false);
            }
        }

    }

    public void callWebviewWithUrl(String url) {
        Intent intent = new Intent(getApplicationContext(), WebviewActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }


}
