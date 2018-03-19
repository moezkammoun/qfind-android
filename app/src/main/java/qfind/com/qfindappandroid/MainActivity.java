package qfind.com.qfindappandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lightsky.infiniteindicator.IndicatorConfiguration;
import cn.lightsky.infiniteindicator.InfiniteIndicator;
import cn.lightsky.infiniteindicator.OnPageClickListener;
import cn.lightsky.infiniteindicator.Page;
import qfind.com.qfindappandroid.categorycontaineractivity.ContainerActivity;
import qfind.com.qfindappandroid.homeactivty.QFindOfTheDayDetails;
import qfind.com.qfindappandroid.homeactivty.RegistrationDetails;
import qfind.com.qfindappandroid.homeactivty.SearchData;
import qfind.com.qfindappandroid.predictiveSearch.DelayAutoCompleteTextView;
import qfind.com.qfindappandroid.predictiveSearch.SearchAutoCompleteAdapter;
import qfind.com.qfindappandroid.retrofitinstance.ApiClient;
import qfind.com.qfindappandroid.retrofitinstance.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static cn.lightsky.infiniteindicator.IndicatorConfiguration.LEFT;
import static cn.lightsky.infiniteindicator.IndicatorConfiguration.RIGHT;


public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener, OnPageClickListener {

    @BindView(R.id.toolbar)
    android.support.v7.widget.Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    @BindView(R.id.findByCategoryBtn)
    Button findByCategoryBtn;
    @BindView(R.id.hamburger_home)
    ImageView hamburgerMenu;
    @BindView(R.id.home_search_icon)
    ImageView searchButton;
    @BindView(R.id.q_find_of_the_day)
    TextView qFindOfTheDayText;
    @BindView(R.id.or)
    TextView orText;
    @BindView(R.id.ads_place_holder)
    ImageView adsPlaceHolder;
    @BindView(R.id.progressBarForloading)
    ProgressBar progressBar;
    Typeface mTypeFace;
    Intent navigationIntent;
    String accessToken;
    SharedPreferences qFindPreferences;
    ApiInterface apiService;
    SharedPreferences.OnSharedPreferenceChangeListener listener;
    ArrayList<Page> ads;
    QFindOfTheDayDetails qfindOfTheDayDetails;
    Integer updatedDate;
    Date d;
    SimpleDateFormat sdf;
    SharedPreferences.Editor editor;
    private IndicatorConfiguration configuration;
    private InfiniteIndicator animCircleIndicator;
    private PicassoLoader picassoLoader;
    DelayAutoCompleteTextView autoCompleteTextView;
    SearchData searchData;
    Locale myLocale;
    private final String clientId = "80581B4C-C060-D166-7893-A4424C15A63D";
    private final String clientSecret = "0488AFF2-BCE0-BC87-F614-10F055107FEB";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        animCircleIndicator = (InfiniteIndicator) findViewById(R.id.main_indicator_default_circle);
        Util.categoryPageStatus = 1;
        setupHamburgerClickListener();

        qFindPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        accessToken = qFindPreferences.getString("AccessToken", null);

        autoCompleteTextView = (DelayAutoCompleteTextView) findViewById(R.id.home_autocomplete_edit_text);
        autoCompleteTextView.setThreshold(2);
        autoCompleteTextView.setAdapter(new SearchAutoCompleteAdapter(this));
        autoCompleteTextView.setLoadingIndicator(
                (android.widget.ProgressBar) findViewById(R.id.home_loading_indicator), searchButton);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                searchData = (SearchData) adapterView.getItemAtPosition(position);
                autoCompleteTextView.setText(searchData.getSearchName());
                searchButton.performClick();
            }
        });
        autoCompleteTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    searchButton.performClick();
                    return true;
                }
                return false;
            }
        });
        hamburgerMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerOpenCloseHandler();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!autoCompleteTextView.getText().toString().equals("")) {
                    if (isNetworkAvailable()) {
                        navigationIntent = new Intent(MainActivity.this, ContainerActivity.class);
                        navigationIntent.putExtra("SHOW_FRAGMENT", AppConfig.Fragments.SEARCH_RESULTS.toString());
                        navigationIntent.putExtra("SEARCH_TEXT", autoCompleteTextView.getText().toString());
                        if (searchData != null) {
                            navigationIntent.putExtra("SEARCH_TYPE", searchData.getSearchType());
                            searchData = null;
                        } else
                            navigationIntent.putExtra("SEARCH_TYPE", 4);
                        startActivity(navigationIntent);
                    }
                }
            }
        });
        findByCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isNetworkAvailable()) {
                    accessToken = qFindPreferences.getString("AccessToken", null);
                    if (accessToken == null) {
                        registerTheApp();
                    } else {
                        openContainerActivity();
                    }
                }

            }
        });
        getQFind();
//        setFontTypeForHomeText();
        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                if (key.equals("AccessToken"))
                    getQFind();
            }
        };
        qFindPreferences.registerOnSharedPreferenceChangeListener(listener);

    }

    public void getQFind() {
        accessToken = qFindPreferences.getString("AccessToken", null);
        updatedDate = qFindPreferences.getInt("UPDATED_ON", 0);
        if (updatedDate == 0) {
            updateAds();
//            Util.showToast("first launch", getApplicationContext());
        } else {
            if (isAdExpired(updatedDate)) {
                updateAds();
//                Util.showToast("Expired", getApplicationContext());
            } else {
                getAdsFromPreference();
//                Util.showToast("not expired", getApplicationContext());
            }
        }
    }

    public void updateAds() {
        if (accessToken != null) {
            apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<QFindOfTheDayDetails> call = apiService.getQFindOfTheDay(accessToken);
            call.enqueue(new Callback<QFindOfTheDayDetails>() {
                @Override
                public void onResponse(Call<QFindOfTheDayDetails> call, Response<QFindOfTheDayDetails> response) {
                    if (response.isSuccessful()) {
                        int i;
                        if (response.body() != null) {
                            qfindOfTheDayDetails = response.body();
                            if (qfindOfTheDayDetails.getCode().equals("200")) {
                                editor = qFindPreferences.edit();
                                for (i = 0; i < qfindOfTheDayDetails.getAdsData().getImages().size(); i++) {
                                    editor.putString("AD" + (i + 1), qfindOfTheDayDetails.getAdsData().getImages().get(i));
                                }
                                editor.putInt("COUNT", (i));
                                editor.putInt("UPDATED_ON", getCurrentDate());
                                editor.commit();
                                getAdsFromPreference();
                            } else {
                                Util.showToast(getResources().getString(R.string.un_authorised), getApplicationContext());
                            }
                        }

                    } else {
                        Util.showToast(getResources().getString(R.string.error_in_connecting), getApplicationContext());
                    }
                }

                @Override
                public void onFailure(Call<QFindOfTheDayDetails> call, Throwable t) {
                    Util.showToast(getResources().getString(R.string.check_network), getApplicationContext());
                }
            });
        }
    }

    public boolean isAdExpired(Integer updatedDate) {
        boolean isExpired;
        if (updatedDate != getCurrentDate())
            isExpired = true;
        else
            isExpired = false;
        return isExpired;
    }

    public int getCurrentDate() {
        d = new Date();
        sdf = new SimpleDateFormat("dd");
        return Integer.valueOf(sdf.format(d));
    }

    public void getAdsFromPreference() {
        ads = new ArrayList<>();
        for (int i = 0; i < qFindPreferences.getInt("COUNT", 0); i++) {
            ads.add(new Page("", qFindPreferences.getString("AD" + (i + 1), null)));
        }
        loadAdsToSlider(ads);

    }

    @Override
    protected boolean useToolbar() {
        return false;
    }

    @Override
    protected boolean useBottomBar() {
        return false;
    }

    @Override
    public void setupSideMenuItemClickListener() {
        sideMenuAboutUsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullView.closeDrawer(GravityCompat.END);
                language = qFindPreferences.getInt("AppLanguage", 1);
                if (language == 1) {
                    callWebviewWithUrl("http://ec2-18-219-90-185.us-east-2.compute.amazonaws.com/static-pages/1/1");
                } else {
                    callWebviewWithUrl("http://ec2-18-219-90-185.us-east-2.compute.amazonaws.com/static-pages/1/2");
                }

            }
        });
        sideMenuQFinderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullView.closeDrawer(GravityCompat.END);
                language = qFindPreferences.getInt("AppLanguage", 1);
                if (language == 1) {
                    callWebviewWithUrl("http://ec2-18-219-90-185.us-east-2.compute.amazonaws.com/static-pages/2/1");
                } else {
                    callWebviewWithUrl("http://ec2-18-219-90-185.us-east-2.compute.amazonaws.com/static-pages/2/2");
                }
            }
        });
        sideMenuTermsAndConditionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showFragment(AppConfig.Fragments.TERMS_AND_CONDITIONS.toString());
                fullView.closeDrawer(GravityCompat.END);
                language = qFindPreferences.getInt("AppLanguage", 1);
                if (language == 1) {
                    callWebviewWithUrl("http://ec2-18-219-90-185.us-east-2.compute.amazonaws.com/static-pages/3/1");
                } else {
                    callWebviewWithUrl("http://ec2-18-219-90-185.us-east-2.compute.amazonaws.com/static-pages/3/2");
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
                    callWebviewWithUrl("http://ec2-18-219-90-185.us-east-2.compute.amazonaws.com/static-pages/4/2"
                    );
                }
            }
        });
        sideMenuSettingsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFragment(AppConfig.Fragments.SETTINGS.toString());
                fullView.closeDrawer(GravityCompat.END);
            }
        });

    }

    public void showFragment(String fragment) {
        if (isNetworkAvailable()) {
            navigationIntent = new Intent(MainActivity.this, ContainerActivity.class);
            navigationIntent.putExtra("SHOW_FRAGMENT", fragment);
            startActivity(navigationIntent);
        }
    }

    public void setupHamburgerClickListener() {
        hamburgerMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerOpenCloseHandler();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (fullView.isDrawerOpen(Gravity.END)) {
            fullView.closeDrawer(Gravity.END);
        } else {
            finishAffinity();
        }
    }

    public void setFontTypeForHomeText() {
        if (getResources().getConfiguration().locale.getLanguage().equals("en")) {
            mTypeFace = Typeface.createFromAsset(getAssets(),
                    "fonts/Lato-Bold.ttf");
            findByCategoryBtn.setText(getResources().getString(R.string.find_by_category));
            findByCategoryBtn.setTypeface(mTypeFace);
            mTypeFace = Typeface.createFromAsset(getAssets(),
                    "fonts/Lato-Regular.ttf");
        } else {
            mTypeFace = Typeface.createFromAsset(getAssets(),
                    "fonts/GE_SS_Unique_Bold.otf");
            findByCategoryBtn.setText(getResources().getString(R.string.find_by_category));
            findByCategoryBtn.setTypeface(mTypeFace);
            mTypeFace = Typeface.createFromAsset(getAssets(),
                    "fonts/GE_SS_Unique_Light.otf");
        }
        qFindOfTheDayText.setText(getResources().getString(R.string.qfind_of_the_day));
        qFindOfTheDayText.setTypeface(mTypeFace);
        orText.setText(getResources().getString(R.string.or));
        orText.setTypeface(mTypeFace);
        autoCompleteTextView.setHint(getResources().getString(R.string.search_hint));
        autoCompleteTextView.setTypeface(mTypeFace);
    }

    public void loadAdsToSlider(ArrayList<Page> adsImages) {
        if (adsImages.size() > 1) {
            picassoLoader = new PicassoLoader();
            if (getResources().getConfiguration().locale.getLanguage().equals("en")) {
                configuration = new IndicatorConfiguration.Builder()
                        .imageLoader(picassoLoader)
                        .isStopWhileTouch(true)
                        .onPageChangeListener(this)
                        .scrollDurationFactor(6)
                        .internal(3000)
                        .isLoop(true)
                        .isAutoScroll(true)
                        .onPageClickListener(this)
                        .direction(RIGHT)
                        .position(IndicatorConfiguration.IndicatorPosition.Center_Bottom)
                        .build();
                animCircleIndicator.init(configuration);
                animCircleIndicator.notifyDataChange(adsImages);
            } else {
                configuration = new IndicatorConfiguration.Builder()
                        .imageLoader(picassoLoader)
                        .isStopWhileTouch(true)
                        .onPageChangeListener(this)
                        .internal(3000)
                        .scrollDurationFactor(6)
                        .isLoop(true)
                        .isAutoScroll(true)
                        .onPageClickListener(this)
                        .direction(LEFT)
                        .position(IndicatorConfiguration.IndicatorPosition.Center_Bottom)
                        .build();
                animCircleIndicator.init(configuration);
                animCircleIndicator.notifyDataChange(adsImages);
            }
            adsPlaceHolder.setVisibility(View.GONE);
        } else {
            picassoLoader = new PicassoLoader();
            configuration = new IndicatorConfiguration.Builder()
                    .imageLoader(picassoLoader)
                    .isStopWhileTouch(true)
                    .onPageChangeListener(null)
                    .scrollDurationFactor(6)
                    .internal(3000)
                    .isLoop(false)
                    .isAutoScroll(false)
                    .onPageClickListener(null)
                    .direction(RIGHT)
                    .position(IndicatorConfiguration.IndicatorPosition.Center_Bottom)
                    .build();
            animCircleIndicator.init(configuration);
            animCircleIndicator.notifyDataChange(adsImages);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setFontTypeForHomeText();
        if (configuration != null)
            animCircleIndicator.start();
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
        setFontTypeForText();
        autoCompleteTextView.setText(null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (configuration != null)
            animCircleIndicator.stop();
        if (listener != null)
            qFindPreferences.unregisterOnSharedPreferenceChangeListener(listener);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (ads.size() < 1) {

        }

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

    public void openContainerActivity() {
        navigationIntent = new Intent(MainActivity.this, ContainerActivity.class);
        navigationIntent.putExtra("SHOW_FRAGMENT", AppConfig.Fragments.CATEGORIES.toString());
        startActivity(navigationIntent);
    }

    public void registerTheApp() {
        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<RegistrationDetails> call = apiService.getAccessToken(clientId, clientSecret);
        call.enqueue(new Callback<RegistrationDetails>() {
            @Override
            public void onResponse(Call<RegistrationDetails> call, Response<RegistrationDetails> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        RegistrationDetails registrationDetails = response.body();
                        if (registrationDetails.getCode().equals("200")) {
                            SharedPreferences qfindPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = qfindPreferences.edit();
                            editor.putString("AccessToken", registrationDetails.getAccessToken());
                            editor.putBoolean("AuthTokenStatus", true);
                            editor.commit();
                            progressBar.setVisibility(View.GONE);
                            openContainerActivity();
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Util.showToast(getResources().getString(R.string.un_authorised), getApplicationContext());
                        }
                    }

                } else {
                    progressBar.setVisibility(View.GONE);
                    Util.showToast(getResources().getString(R.string.error_in_connecting), getApplicationContext());

                }
            }

            @Override
            public void onFailure(Call<RegistrationDetails> call, Throwable t) {
                Util.showToast(getResources().getString(R.string.check_network), getApplicationContext());
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
