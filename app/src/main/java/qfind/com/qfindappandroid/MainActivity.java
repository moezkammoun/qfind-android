package qfind.com.qfindappandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lightsky.infiniteindicator.IndicatorConfiguration;
import cn.lightsky.infiniteindicator.InfiniteIndicator;
import cn.lightsky.infiniteindicator.OnPageClickListener;
import cn.lightsky.infiniteindicator.Page;
import qfind.com.qfindappandroid.categorycontaineractivity.ContainerActivity;
import qfind.com.qfindappandroid.categoryfragment.CategoryFragmentModel;
import qfind.com.qfindappandroid.categoryfragment.CategoryPageCurrentStatus;
import qfind.com.qfindappandroid.categoryfragment.PicassoLoader;
import qfind.com.qfindappandroid.homeactivty.AdsData;
import qfind.com.qfindappandroid.homeactivty.QFindOfTheDayDetails;
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
    @BindView(R.id.homeAutoCompleteEditText)
    AutoCompleteTextView autoCompleteTextView;
    @BindView(R.id.q_find_of_the_day)
    TextView qFindOfTheDayText;
    @BindView(R.id.or)
    TextView orText;
    @BindView(R.id.ads_place_holder)
    ImageView adsPlaceHolder;
    private InfiniteIndicator mAnimCircleIndicator;
    Typeface mTypeFace;
    Intent navigationIntent;
    String accessToken;
    SharedPreferences qFindPreferences;
    ApiInterface apiService;
    SharedPreferences.OnSharedPreferenceChangeListener listener;
    ArrayList<Page> ads;
    QFindOfTheDayDetails qfindOfTheDayDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        mAnimCircleIndicator = (InfiniteIndicator) findViewById(R.id.indicator_default_circle);
        CategoryPageCurrentStatus.categoryPageStatus = 1;
        setupHamburgerClickListener();
        qFindPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        accessToken = qFindPreferences.getString("AccessToken", null);

        String[] FINDINGS = new String[]{
                "Hotel", "Hotel", "Hotel", "Hotel", "Bar", "Dentist", "Exterior Designer", "Restaurant",
                "الفندق", "الفندق", "الفندق"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, FINDINGS);
        autoCompleteTextView.setAdapter(adapter);

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
                    navigationIntent = new Intent(MainActivity.this, ContainerActivity.class);
                    navigationIntent.putExtra("SHOW_FRAGMENT", AppConfig.Fragments.SEARCH_RESULTS.toString());
                    navigationIntent.putExtra("SEARCH_TEXT", autoCompleteTextView.getText().toString());
                    startActivity(navigationIntent);
                } else {
                    Toast.makeText(MainActivity.this, R.string.please_type, Toast.LENGTH_SHORT).show();
                }
            }
        });
        findByCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigationIntent = new Intent(MainActivity.this, ContainerActivity.class);
                navigationIntent.putExtra("SHOW_FRAGMENT", AppConfig.Fragments.CATEGORIES.toString());
                startActivity(navigationIntent);
            }
        });
        getQFind();
        setFontTypeForHomeText();
        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                getQFind();
            }
        };
        qFindPreferences.registerOnSharedPreferenceChangeListener(listener);
    }

    public void getQFind() {
        accessToken = qFindPreferences.getString("AccessToken", null);
        if (accessToken != null) {
            apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<QFindOfTheDayDetails> call = apiService.getQFindOfTheDay(accessToken);
            call.enqueue(new Callback<QFindOfTheDayDetails>() {
                @Override
                public void onResponse(Call<QFindOfTheDayDetails> call, Response<QFindOfTheDayDetails> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            qfindOfTheDayDetails = response.body();
                            if (qfindOfTheDayDetails.getCode().equals("200")) {
                                ads = new ArrayList<>();
                                for (int i = 0; i < qfindOfTheDayDetails.getAdsData().getImages().size(); i++) {
                                    ads.add(new Page("", qfindOfTheDayDetails.getAdsData().getImages().get(i)));
                                }
                                loadAdsToSlider(ads);
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
                navigationIntent = new Intent(MainActivity.this, ContainerActivity.class);
                navigationIntent.putExtra("SHOW_FRAGMENT", AppConfig.Fragments.TERMS_AND_CONDITIONS.toString());
                startActivity(navigationIntent);
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
                navigationIntent = new Intent(MainActivity.this, ContainerActivity.class);
                navigationIntent.putExtra("SHOW_FRAGMENT", AppConfig.Fragments.SETTINGS.toString());
                startActivity(navigationIntent);
                fullView.closeDrawer(GravityCompat.END);
            }
        });

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
            findByCategoryBtn.setTypeface(mTypeFace);
            mTypeFace = Typeface.createFromAsset(getAssets(),
                    "fonts/Lato-Regular.ttf");
        } else {
            mTypeFace = Typeface.createFromAsset(getAssets(),
                    "fonts/GE_SS_Unique_Light.otf");
            findByCategoryBtn.setTypeface(mTypeFace);
        }
        qFindOfTheDayText.setTypeface(mTypeFace);
        orText.setTypeface(mTypeFace);
        autoCompleteTextView.setTypeface(mTypeFace);
    }

    public void loadAdsToSlider(ArrayList<Page> adsImages) {
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
        adsPlaceHolder.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        autoCompleteTextView.setText(null);
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
