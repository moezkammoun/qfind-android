package qfind.com.qfindappandroid.categorycontaineractivity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.ButterKnife;
import qfind.com.qfindappandroid.AppConfig;
import qfind.com.qfindappandroid.BaseActivity;
import qfind.com.qfindappandroid.R;
import qfind.com.qfindappandroid.Util;
import qfind.com.qfindappandroid.categoryfragment.CategoryFragment;
import qfind.com.qfindappandroid.homeactivty.QFindOfTheDayDetails;
import qfind.com.qfindappandroid.informationFragment.InformationFragment;
import qfind.com.qfindappandroid.informationFragment.ServiceProviderDataResponse;
import qfind.com.qfindappandroid.predictiveSearch.ServiceProviderResult;
import qfind.com.qfindappandroid.retrofitinstance.ApiClient;
import qfind.com.qfindappandroid.retrofitinstance.ApiInterface;
import qfind.com.qfindappandroid.searchResultsFragment.SearchResultsFragment;
import qfind.com.qfindappandroid.settingspagefragment.SettingsFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ContainerActivity extends BaseActivity implements ContainerActivityView {

    Fragment fragment;
    Intent intent;
    String fragmentToShow, searchText, accessToken;
    SharedPreferences qFindPreferences;
    private Integer searchType;
    private ServiceProviderDataResponse serviceProviderDataResponse;
    private int language;
    private ApiInterface apiService;
    Bundle bundle = new Bundle();
    ServiceProviderResult serviceProviderResult;
    QFindOfTheDayDetails qfindOfTheDayDetails;
    SharedPreferences.Editor editor;
    Date d;
    SimpleDateFormat sdf;
    Locale myLocale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        ButterKnife.bind(this);
        qFindPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        loadFragmentWithoutBackStack(new CategoryFragment());
//        showNormalToolbar();
        //getMainCategoryItemsList();
        intent = getIntent();
        fragmentToShow = intent.getStringExtra("SHOW_FRAGMENT");
        searchText = intent.getStringExtra("SEARCH_TEXT");
        searchType = intent.getIntExtra("SEARCH_TYPE", 0);
        if (fragmentToShow != null) {
            if (fragmentToShow.equals(AppConfig.Fragments.SEARCH_RESULTS.toString())) {
                fragment = new SearchResultsFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("searchType", searchType);
                bundle.putString("searchKey", searchText);
                fragment.setArguments(bundle);
                loadFragmentWithoutBackStack(fragment);
            } else if (fragmentToShow.equals(AppConfig.Fragments.SETTINGS.toString())) {
                fragment = new SettingsFragment();
                loadFragmentWithoutBackStack(fragment);
            }
        }
        if (searchText != null)
            autoCompleteTextView.setText(searchText);

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        // Override previous intent
        setIntent(intent);

        // Handle new intent
        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {
        String appLinkAction = intent.getAction();
        Uri appLinkData = intent.getData();
        if (Intent.ACTION_VIEW.equals(appLinkAction) && appLinkData != null) {
            String recipeId = appLinkData.getLastPathSegment();
            String providerId = appLinkData.getQueryParameter("provider_id");
            showServiceProviderDetailPageWithoutBackStack("", "", "",
                    "", "", "", "",
                    "", "", "",
                    "", "", "", "",
                    "", Integer.parseInt(providerId), "", "",
                    "", "", "", "",
                    "", "");
            hideInfotoolbarBackButton();
            hideStarandShareButton();
            getServiceProviderData(Integer.valueOf(providerId));
        }
    }

    public void showServiceProviderDetailPageWithoutBackStack(String providerName, String providerLocation,
                                                              String providerMobile, String providerAddress,
                                                              String providerWebsite, String providerOpeningTime,
                                                              String providerMail, String providerFacebook,
                                                              String providerLinkedin, String providerInstagram,
                                                              String providerTwitter, String providerSnapchat,
                                                              String providerGooglePlus, String providerLatLong, String providerLogo,
                                                              int providerId, String providerOpeningTimeArabic,
                                                              String providerAddressArabic, String providerClosingTime,
                                                              String providerClosingTimeArabic, String providerOpeningTitle,
                                                              String providerClosingTitle, String providerOpeningTitleArabic,
                                                              String providerClosingTitleArabic) {
        bundle.putString("providerName", providerName);
        bundle.putString("providerLocation", providerLocation);
        bundle.putString("providerMobile", providerMobile);
        bundle.putString("providerAddress", providerAddress);
        bundle.putString("providerWebsite", providerWebsite);
        bundle.putString("providerOpeningTime", providerOpeningTime);
        bundle.putString("providerMail", providerMail);
        bundle.putString("providerFacebook", providerFacebook);
        bundle.putString("providerLinkedIn", providerLinkedin);
        bundle.putString("providerInstagram", providerInstagram);
        bundle.putString("providerTwitter", providerTwitter);
        bundle.putString("providerSnapchat", providerSnapchat);
        bundle.putString("providerGooglePlus", providerGooglePlus);
        bundle.putString("providerLatLong", providerLatLong);
        bundle.putString("providerLogo", providerLogo);
        bundle.putInt("providerId", providerId);
        bundle.putString("providerOpeningTimeArabic", providerOpeningTimeArabic);
        bundle.putString("providerAddressArabic", providerAddressArabic);
        bundle.putString("providerClosingTime", providerClosingTime);
        bundle.putString("providerClosingTimeArabic", providerClosingTimeArabic);
        bundle.putString("providerOpeningTitle", providerOpeningTitle);
        bundle.putString("providerClosingTitle", providerClosingTitle);
        bundle.putString("providerOpeningTitleArabic", providerOpeningTitleArabic);
        bundle.putString("providerClosingTitleArabic", providerClosingTitleArabic);

        InformationFragment informationFragment = new InformationFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        informationFragment.setArguments(bundle);
        transaction.replace(R.id.frame_container, informationFragment);
        transaction.commit();

    }

    public void getServiceProviderData(int providerId) {
        qFindPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        accessToken = qFindPreferences.getString("AccessToken", null);
        if (accessToken != null) {
            apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<ServiceProviderDataResponse> call = apiService.getServiceProviderData(accessToken, providerId, "");
            call.enqueue(new Callback<ServiceProviderDataResponse>() {
                @Override
                public void onResponse(Call<ServiceProviderDataResponse> call, Response<ServiceProviderDataResponse> response) {
                    Fragment f = getSupportFragmentManager().findFragmentById(R.id.frame_container);
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            serviceProviderDataResponse = response.body();
                            if (serviceProviderDataResponse.getCode().equals("200")) {
                                serviceProviderResult = serviceProviderDataResponse.getResult();
                                showStarandShareButton();
                                showServiceProviderDetailPageWithoutBackStack(
                                        serviceProviderResult.getServiceProviderName(),
                                        serviceProviderResult.getServiceProviderLocation(),
                                        serviceProviderResult.getServiceProviderMobile(),
                                        serviceProviderResult.getServiceProviderAddress(),
                                        serviceProviderResult.getServiceProviderWebsite(),
                                        serviceProviderResult.getServiceProviderOpeningTime(),
                                        serviceProviderResult.getServiceProviderMail(),
                                        serviceProviderResult.getServiceProviderFacebook(),
                                        serviceProviderResult.getServiceProviderLinkedin(),
                                        serviceProviderResult.getServiceProviderInstagram(),
                                        serviceProviderResult.getServiceProviderTwitter(),
                                        serviceProviderResult.getServiceProviderSnapchat(),
                                        serviceProviderResult.getServiceProviderGoogleplus(),
                                        serviceProviderResult.getServiceProviderMapLocation(),
                                        serviceProviderResult.getServiceProviderLogo(),
                                        serviceProviderResult.getServiceProviderId(),
                                        serviceProviderResult.getServiceProviderOpeningTimeArabic(),
                                        serviceProviderResult.getServiceProviderAddressArabic(),
                                        serviceProviderResult.getServiceProviderClosingTime(),
                                        serviceProviderResult.getServiceProviderClosingTimeArabic(),
                                        serviceProviderResult.getServiceProviderOpeningTitle(),
                                        serviceProviderResult.getServiceProviderClosingTitle(),
                                        serviceProviderResult.getServiceProviderOpeningTitleArabic(),
                                        serviceProviderResult.getServiceProviderClosingTitleArabic());
                            }
                        }

                    } else {
                        Util.showToast(getResources().getString(R.string.error_in_connecting), getApplicationContext());
                    }
                }

                @Override
                public void onFailure(Call<ServiceProviderDataResponse> call, Throwable t) {
                    if (t instanceof IOException) {
                        Util.showToast(getResources().getString(R.string.check_network), getApplicationContext());
                    } else {
                        Util.showToast(getResources().getString(R.string.error_in_connecting), getApplicationContext());
                    }
                }
            });
        }

    }

    public void showServiceProvider(Uri uri) {
        showServiceProviderDetailPage(
                "ProviderName", "ProviderLocation", "ProviderNameArabic",
                "ProviderLocationArabic", "ProviderPhone",
                "ProviderAddress", "ProviderWebsite", "ProviderOpeningTime",
                "ProviderMail", "ProviderFacebook", "ProviderLinkedIn",
                "ProviderInstagram", "ProviderTwitter", "ProviderSnapchat",
                "ProviderGooglePlus", "ProviderLatlong", "ProviderLogo",
                1, "ProviderOpeningTimeArabic", "ProviderAddressArabic",
                "ProviderClosingTime", "ProvideClosingTimeArabic",
                "ProviderOpeningTitle", "ProviderClosingTitle",
                "ProviderOpeningTitleArabic", "ProviderClosingTitleArabic"
        );

        Toast.makeText(ContainerActivity.this, "URI : " + uri, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (fullView.isDrawerOpen(Gravity.END)) {
            fullView.closeDrawer(Gravity.END);
        } else if (Util.categoryPageStatus == 2 || Util.categoryPageStatus == 3) {
            fragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
            if ((fragment instanceof CategoryFragment)) {
                CategoryFragment fragment = (CategoryFragment) getSupportFragmentManager().findFragmentById(R.id.frame_container);
                fragment.setSubCategoryBackButtonClickAction();
                fragment.hideLoader();
            } else {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
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
        autoCompleteTextView.setHint(getResources().getString(R.string.search_hint));
        setupBottomNavigationBar();
    }

    public void getAds() {
        accessToken = qFindPreferences.getString("AccessToken", null);
        if (accessToken != null) {
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<QFindOfTheDayDetails> call = apiService.getQFindOfTheDay(accessToken);
            call.enqueue(new Callback<QFindOfTheDayDetails>() {
                @Override
                public void onResponse(Call<QFindOfTheDayDetails> call, Response<QFindOfTheDayDetails> response) {
                    Fragment f = getSupportFragmentManager().findFragmentById(R.id.frame_container);
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
                                if ((f instanceof CategoryFragment)) {
                                    CategoryFragment fragment = (CategoryFragment) getSupportFragmentManager().findFragmentById(R.id.frame_container);
                                    fragment.getAdsFromPreference();
                                }
                                //
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
                    if (t instanceof IOException) {
                        Util.showToast(getResources().getString(R.string.check_network), getApplicationContext());
                    } else {
                        Util.showToast(getResources().getString(R.string.error_in_connecting), getApplicationContext());
                    }
                }
            });
        }
    }

    public void getMainCategoryItemsList() {
        qFindPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        accessToken = qFindPreferences.getString("AccessToken", null);
        if (accessToken != null) {
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<MainCategory> call = apiService.getMainCategory(accessToken, qFindPreferences.getInt("AppLanguage", 1));
            call.enqueue(new Callback<MainCategory>() {
                @Override
                public void onResponse(Call<MainCategory> call, Response<MainCategory> response) {
                    Fragment f = getSupportFragmentManager().findFragmentById(R.id.frame_container);
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            MainCategory mainCategory = response.body();
                            if (mainCategory.getCode().equals("200")) {
                                ArrayList<MainCategoryItemList> mainCategoryItemList = mainCategory.getMainCategoryItemList();
                                if ((f instanceof CategoryFragment)) {
                                    CategoryFragment fragment = (CategoryFragment) getSupportFragmentManager().findFragmentById(R.id.frame_container);
                                    fragment.setRecyclerViewDatas(mainCategoryItemList);
                                    fragment.hideLoader();

                                }
                            } else if (mainCategory.getCode().equals("404")) {
                                if ((f instanceof CategoryFragment)) {
                                    CategoryFragment fragment = (CategoryFragment) getSupportFragmentManager().findFragmentById(R.id.frame_container);
                                    fragment.hideLoader();
                                    fragment.showNoDataText();
                                }

                            } else {
                                if ((f instanceof CategoryFragment)) {
                                    CategoryFragment fragment = (CategoryFragment) getSupportFragmentManager().findFragmentById(R.id.frame_container);
                                    fragment.hideLoader();
                                    fragment.showNoDataText();
                                    //Util.showToast(getResources().getString(R.string.something_went_wrong), getApplicationContext());
                                }
                            }
                        }

                    } else {
                        if ((f instanceof CategoryFragment)) {
                            CategoryFragment fragment = (CategoryFragment) getSupportFragmentManager().findFragmentById(R.id.frame_container);
                            fragment.hideLoader();
                            fragment.showServerErrorText();
                            Util.showToast(getResources().getString(R.string.error_in_connecting), getApplicationContext());
                        }


                    }
                }

                @Override
                public void onFailure(Call<MainCategory> call, Throwable t) {
                    Fragment f = getSupportFragmentManager().findFragmentById(R.id.frame_container);
                    if (t instanceof IOException) {
                        if ((f instanceof CategoryFragment)) {
                            CategoryFragment fragment = (CategoryFragment) getSupportFragmentManager().findFragmentById(R.id.frame_container);
                            fragment.hideLoader();
                            Util.showToast(getResources().getString(R.string.check_network), getApplicationContext());
                        }
                    } else {
                        Util.showToast(getResources().getString(R.string.error_in_connecting), getApplicationContext());
                    }

                }
            });
        }
    }

    public int getCurrentDate() {
        d = new Date();
        sdf = new SimpleDateFormat("dd");
        return Integer.valueOf(sdf.format(d));
    }


}
