package qfind.com.qfindappandroid.informationFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import qfind.com.qfindappandroid.DataBaseHandler;
import qfind.com.qfindappandroid.R;
import qfind.com.qfindappandroid.SimpleDividerItemDecoration;
import qfind.com.qfindappandroid.Util;
import qfind.com.qfindappandroid.categorycontaineractivity.ContainerActivity;
import qfind.com.qfindappandroid.categoryfragment.RecyclerViewClickListener;
import qfind.com.qfindappandroid.historyPage.HistoryItem;
import qfind.com.qfindappandroid.predictiveSearch.ServiceProviderResult;
import qfind.com.qfindappandroid.retrofitinstance.ApiClient;
import qfind.com.qfindappandroid.retrofitinstance.ApiInterface;
import qfind.com.qfindappandroid.webviewactivity.WebviewActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InformationFragment extends Fragment {
    ArrayList<InformationFragmentModel> informationData = new ArrayList<>();
    RecyclerView recyclerView;
    InformationFragmentAdapter adapter;
    ProgressBar progressBar;
    TextView emptyTextView;
    ImageView infoStarIcon;
    ServiceProviderDataResponse serviceProviderDataResponse;
    ServiceProviderResult serviceProviderResult;
    SharedPreferences qFindPreferences;
    String accessToken;
    Bundle bundle;

    String providerName, providerLocation, providerNameArabic, providerLocationArabic,
            providerMobile, providerWebsite, providerAddress,
            providerMail, providerFacebook, providerLinkedin, providerInstagram,
            providerTwitter, providerSnapchat, providerGooglePlus, providerAddressArabic,
            providerLatLong, providerLogo;
    String[] providerOpeningTime = new String[7], providerOpeningTimeDay = new String[7], providerOpeningTimeArabic = new String[7], providerClosingTime = new String[7], providerClosingTimeArabic = new String[7], providerOpeningTitle = new String[7], providerClosingTitle = new String[7], providerOpeningTitleArabic = new String[7], providerClosingTitleArabic = new String[7];
    int providerPageId;
    URI uri = null;
    String path;
    private int language;
    RecyclerViewClickListener recyclerViewClickListener;

    public InformationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_information, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBarLoading);
        bundle = getArguments();
        String callfrom = bundle.getString("callFrom");
        if (callfrom.equals("others")) {
            providerPageId = bundle.getInt("providerId");
            getServiceProviderDataInner(providerPageId);
        } else {
            progressBar.setVisibility(View.GONE);
            providerPageId = bundle.getInt("providerId");
            providerName = bundle.getString("providerName");
            providerLocation = bundle.getString("providerLocation");
            providerNameArabic = bundle.getString("providerNameArabic");
            providerLocationArabic = bundle.getString("providerLocationArabic");
            providerMobile = bundle.getString("providerMobile");
            providerWebsite = bundle.getString("providerWebsite");
            if (providerWebsite.contains("http")) {

                try {
                    uri = new URI(providerWebsite);
                    path = uri.getAuthority();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

            }
            providerAddress = bundle.getString("providerAddress");
            providerMail = bundle.getString("providerMail");
            providerFacebook = bundle.getString("providerFacebook");
            providerLinkedin = bundle.getString("providerLinkedin");
            providerInstagram = bundle.getString("providerInstagram");
            providerTwitter = bundle.getString("providerTwitter");
            providerSnapchat = bundle.getString("providerSnapchat");
            providerGooglePlus = bundle.getString("providerGooglePlus");
            providerLatLong = bundle.getString("providerLatLong");
            providerLogo = bundle.getString("providerLogo");
            providerPageId = bundle.getInt("providerId");
            providerOpeningTime = bundle.getStringArray("providerOpeningTime");
            providerOpeningTimeDay = bundle.getStringArray("providerOpeningday");
            providerOpeningTimeArabic = bundle.getStringArray("providerOpeningTimeArabic");
            providerAddressArabic = bundle.getString("providerAddressArabic");
            providerClosingTime = bundle.getStringArray("providerClosingTime");
            providerClosingTimeArabic = bundle.getStringArray("providerClosingTimeArabic");
            providerOpeningTitle = bundle.getStringArray("providerOpeningTitle");
            providerClosingTitle = bundle.getStringArray("providerClosingTitle");
            providerOpeningTitleArabic = bundle.getStringArray("providerOpeningTitleArabic");
            providerClosingTitleArabic = bundle.getStringArray("providerClosingTitleArabic");
        }


        DataBaseHandler db = new DataBaseHandler(getContext());
        HistoryItem dataModel = new HistoryItem();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        SimpleDateFormat sdftime = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat sdfdatetime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
        dataModel.setDay(sdf.format(new Date()));
        dataModel.setTime(sdftime.format(new Date()));
        dataModel.setTitke(bundle.getString("providerName"));
        dataModel.setImage(bundle.getString("providerLogo"));
        dataModel.setDescription(bundle.getString("providerLocation"));
        dataModel.setTitleArabic(bundle.getString("providerNameArabic"));
        dataModel.setDescriptionArabic(bundle.getString("providerLocationArabic"));
        dataModel.setPageId(bundle.getInt("providerId"));
        dataModel.setDayTime(sdfdatetime.format(new Date()));

        Cursor cursor = db.checkHistoryByDay(bundle.getInt("providerId"));
        ArrayList<String> dayList = new ArrayList<String>();
        if (cursor.moveToFirst()) {
            do {
                dayList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        } else {
            dayList.add("0");
        }
        String formattedDate = "";
        Date d = null;
        try {
            d = sdfdatetime.parse(dayList.get(0));
            long milliseconds = d.getTime();
            formattedDate = getDate(milliseconds);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (formattedDate.equals(sdf.format(new Date()))) {
            db.updateHistory(dataModel, bundle.getInt("providerId"), formattedDate);
        } else {
            db.addHistory(dataModel);
        }

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        emptyTextView = (TextView) view.findViewById(R.id.empty_text_view_info);
        infoStarIcon = (ImageView) view.findViewById(R.id.fav_star_icon);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.ItemDecoration dividerItemDecoration = new SimpleDividerItemDecoration(getContext());
        recyclerView.addItemDecoration(dividerItemDecoration);
//        if (informationData == null)
//            emptyTextView.setVisibility(View.VISIBLE);
//        else
//            progressBar.setVisibility(View.GONE);
        ((ContainerActivity) getActivity()).setupBottomNavigationBar();

        providerPageId = bundle.getInt("providerId");
        providerName = bundle.getString("providerName");
        providerLocation = bundle.getString("providerLocation");
        providerNameArabic = bundle.getString("providerNameArabic");
        providerLocationArabic = bundle.getString("providerLocationArabic");

        if (getResources().getConfiguration().locale.getLanguage().equals("en")) {
            ((ContainerActivity) getActivity()).showInfoToolbar(providerName, providerLocation);
        } else {
            ((ContainerActivity) getActivity()).showInfoToolbar(providerNameArabic, providerLocationArabic);
        }

        ((ContainerActivity) getActivity()).isFavoriteSelected(providerPageId);

        if(bundle.getString("callFrom").equals("category")){
            showInformationData();
        }

    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);

    }

    public void showInformationData() {
        if (adapter != null)
            adapter.clear();
        setupRecyclerViewClickListener();
        adapter = new InformationFragmentAdapter(getContext(), getInformationData(), recyclerViewClickListener);
        recyclerView.setAdapter(adapter);

        if (providerName.equals("")) {
            progressBar.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.GONE);
                    emptyTextView.setVisibility(View.VISIBLE);
                }
            }, 3000);

        }
    }

    public ArrayList<InformationFragmentModel> getInformationData() {
        if (providerMobile != null && !providerMobile.equals(""))
            informationData.add(new InformationFragmentModel(R.drawable.phone_icon,
                    R.drawable.dot_icon, providerMobile, R.drawable.right_arrow));
        if (path == null && providerWebsite != null && !providerWebsite.equals(""))
            informationData.add(new InformationFragmentModel(R.drawable.web_icon,
                    R.drawable.dot_icon, providerWebsite, R.drawable.right_arrow));
        else if (path != null && !path.equals(""))
            informationData.add(new InformationFragmentModel(R.drawable.web_icon,
                    R.drawable.dot_icon, path, R.drawable.right_arrow));
        if (getResources().getConfiguration().locale.getLanguage().equals("en")) {
            if (providerAddress != null && !providerAddress.equals(""))
                informationData.add(new InformationFragmentModel(R.drawable.location_icon,
                        R.drawable.dot_icon, providerAddress, R.drawable.right_arrow));
        } else {
            if (providerAddressArabic != null && !providerAddressArabic.equals(""))
                informationData.add(new InformationFragmentModel(R.drawable.location_icon,
                        R.drawable.dot_icon, providerAddressArabic, R.drawable.right_arrow));
        }
        if (getResources().getConfiguration().locale.getLanguage().equals("en")) {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
            Date d = new Date();
            String dayOfTheWeek = sdf.format(d);
            String value = "";
            if (providerOpeningTime[0] != null && !providerOpeningTime[0].equals("")) {
                for (int i = 0; i < providerOpeningTimeDay.length; i++) {
                    if (providerOpeningTimeDay[i].equalsIgnoreCase(dayOfTheWeek)) {
                        if (providerOpeningTime[i].equalsIgnoreCase("Holiday")) {
                            value = getResources().getString(R.string.Closed);
                            break;
                        } else {
                            value = providerOpeningTime[i] + providerOpeningTitle[i] + "-" + providerClosingTime[i] + providerClosingTitle[i];
                            break;
                        }
                    }
                }
                informationData.add(new InformationFragmentModel(R.drawable.clock_icon,
                        R.drawable.dot_icon, value, R.drawable.right_arrow));
            }
        } else {
            String value = "";
            if (providerOpeningTimeArabic[0] != null && !providerOpeningTimeArabic[0].equals("")) {
                SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
                Date d = new Date();
                String dayOfTheWeek = sdf.format(d);
                for (int i = 0; i < providerOpeningTimeDay.length; i++) {
                    if (providerOpeningTimeDay[i].equalsIgnoreCase(dayOfTheWeek)) {
                        if (providerOpeningTime[i].equalsIgnoreCase("Holiday")) {
                            value = getResources().getString(R.string.Closed);
                            break;
                        } else {
                            value = providerOpeningTimeArabic[i] + providerOpeningTitleArabic[i] + "-" + providerClosingTimeArabic[i]
                                    + providerClosingTitleArabic[i];
                            break;
                        }
                    }
                }
                informationData.add(new InformationFragmentModel(R.drawable.clock_icon,
                        R.drawable.dot_icon, value, R.drawable.right_arrow));
            }

        }
        if (providerMail != null && !providerMail.equals(""))
            informationData.add(new InformationFragmentModel(R.drawable.mail_icon,
                    R.drawable.dot_icon, providerMail, R.drawable.right_arrow));
        if (providerFacebook != null && !providerFacebook.equals(""))
            informationData.add(new InformationFragmentModel(R.drawable.facebook_icon,
                    R.drawable.dot_icon, providerFacebook, R.drawable.right_arrow));
//        if (providerLinkedin != null && !providerLinkedin.equals(""))
//            informationData.add(new InformationFragmentModel(R.drawable.linkedin,
//                    R.drawable.dot_icon, providerLinkedin, R.drawable.right_arrow));
        if (providerInstagram != null && !providerInstagram.equals(""))
            informationData.add(new InformationFragmentModel(R.drawable.instagram,
                    R.drawable.dot_icon, providerInstagram, R.drawable.right_arrow));
        if (providerTwitter != null && !providerTwitter.equals(""))
            informationData.add(new InformationFragmentModel(R.drawable.twitter,
                    R.drawable.dot_icon, providerTwitter, R.drawable.right_arrow));
        if (providerSnapchat != null && !providerSnapchat.equals(""))
            informationData.add(new InformationFragmentModel(R.drawable.snapchat,
                    R.drawable.dot_icon, providerSnapchat, R.drawable.right_arrow));
//        if (providerGooglePlus != null && !providerGooglePlus.equals(""))
//            informationData.add(new InformationFragmentModel(R.drawable.google_plus,
//                    R.drawable.dot_icon, providerGooglePlus, R.drawable.right_arrow));
        return informationData;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setupRecyclerViewClickListener() {
        recyclerViewClickListener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (informationData.get(position).getInfo_icon() == R.drawable.facebook_icon && providerFacebook != null) {
                    String facebookUrl = getFacebookPageURL(getContext());
                    if (facebookUrl.substring(0, 2).equalsIgnoreCase("fb")) {
                        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                        facebookIntent.setData(Uri.parse(facebookUrl));
                        startActivity(facebookIntent);
                    } else {
                        callWebviewWithUrl("https://www.facebook.com/" + providerFacebook);
                    }

                }
                if (informationData.get(position).getInfo_icon() == R.drawable.phone_icon && providerMobile != null) {

                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + providerMobile));
                    startActivity(intent);

                }
                if (informationData.get(position).getInfo_icon() == R.drawable.web_icon && providerWebsite != null) {

                    callWebviewWithUrl(providerWebsite);

                }
                if (informationData.get(position).getInfo_icon() == R.drawable.location_icon &&
                        providerLatLong != null && providerLocation != null) {
                    String geoUri = "http://maps.google.com/maps?q=loc:" + providerLatLong + " (" + providerLocation + ")";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                    startActivity(intent);
                }
                if (informationData.get(position).getInfo_icon() == R.drawable.mail_icon &&
                        providerMail != null) {
                    setUpEmail();
                }

                if (informationData.get(position).getInfo_icon() == R.drawable.twitter &&
                        providerTwitter != null) {
                    openTwitter(getContext());
                }
                if (informationData.get(position).getInfo_icon() == R.drawable.instagram &&
                        providerInstagram != null) {
                    openInstagram(getContext());
                }
                if (informationData.get(position).getInfo_icon() == R.drawable.snapchat &&
                        providerSnapchat != null) {
                    openSnapchat(getContext());
                }
                if (informationData.get(position).getInfo_icon() == R.drawable.clock_icon &&
                        providerOpeningTime != null) {
                    openTimingsPopUp(getContext());
                }

            }
        };
    }

    public String getFacebookPageURL(Context context) {

        String FACEBOOK_URL = "https://www.facebook.com/publictheband/";
        String FACEBOOK_PAGE_ID = "publictheband";
        String packageNameFacebook = "com.facebook.katana";
        String packageNameFacebookLite = "com.facebook.lite";
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageNameFacebook, 0);
            String installedPackages = packageInfo.toString();

            ApplicationInfo ai =
                    getActivity().getPackageManager().getApplicationInfo(packageNameFacebook, 0);
            boolean appStatus = ai.enabled;
            if (installedPackages.contains(packageNameFacebook) || (installedPackages.contains(packageNameFacebookLite))) {
                if (appStatus) {
                    return weburl(context);
                }
            }

        } catch (PackageManager.NameNotFoundException e) {
            return "https://www.facebook.com/" + providerFacebook + "/"; //normal web url
        }
        return "https://www.facebook.com/" + providerFacebook + "/"; //normal web url
    }

    public String weburl(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
//            if (versionCode >= 3002850) { //newer versions of fb app
//                //return "fb://facewebmodal/f?href=https://www.facebook.com/" + providerFacebook + "/";
//                return "fb://profile/" + providerFacebook;
//            } else { //older versions of fb app
//                return "fb://profile/" + providerFacebook;
//            }
            return "fb://profile/" + providerFacebook;
        } catch (PackageManager.NameNotFoundException e) {
            return "https://www.facebook.com/" + providerFacebook + "/"; //normal web url
        }
    }


    public void setUpEmail() {
        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{providerMail});
//        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
//        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Text");
        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }

    public void callWebviewWithUrl(String url) {
        Intent intent = new Intent(getContext(), WebviewActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }

    public void openTwitter(Context context) {
        PackageManager pkManager = context.getPackageManager();
//        providerTwitter="ShashiTharoor";
        try {
            PackageInfo pkgInfo = pkManager.getPackageInfo("com.twitter.android", 0);
            String getPkgInfo = pkgInfo.toString();
            ApplicationInfo ai =
                    getActivity().getPackageManager().getApplicationInfo("com.twitter.android", 0);
            boolean appStatus = ai.enabled;
            if (getPkgInfo.contains("com.twitter.android") && appStatus) {
                // APP NOT INSTALLED
                Intent intent = new Intent(Intent.ACTION_VIEW,

                        Uri.parse("twitter://user?screen_name=" + providerTwitter));
                startActivity(intent);
            } else {
                callWebviewWithUrl("https://twitter.com/" + providerTwitter);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

            // APP NOT INSTALLED
            //callWebviewWithUrl("https://twitter.com/"+providerTwitter,providerTwitter);
            callWebviewWithUrl("https://twitter.com/" + providerTwitter);
        }
    }

    public void openInstagram(Context context) {
        PackageManager pkManager = context.getPackageManager();
//        providerInstagram="cinemawoodofficial";
        try {
            PackageInfo pkgInfo = pkManager.getPackageInfo("com.instagram.android", 0);
            String getPkgInfo = pkgInfo.toString();
            ApplicationInfo ai =
                    getActivity().getPackageManager().getApplicationInfo("com.instagram.android", 0);
            boolean appStatus = ai.enabled;

            if (getPkgInfo.contains("com.instagram.android") && appStatus) {
                // APP  INSTALLED
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://instagram.com/_u/" + providerInstagram));
                startActivity(intent);
            } else {
                callWebviewWithUrl("http://instagram.com/" + providerInstagram);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

            // APP NOT INSTALLED
            callWebviewWithUrl("http://instagram.com/" + providerInstagram);
        }

    }

    public void openSnapchat(Context context) {
//        providerSnapchat = "exalturesnapch2";
        PackageManager pkManager = context.getPackageManager();
        try {
            PackageInfo pkgInfo = pkManager.getPackageInfo("com.snapchat.android", 0);
            String getPkgInfo = pkgInfo.toString();
            ApplicationInfo ai =
                    getActivity().getPackageManager().getApplicationInfo("com.snapchat.android", 0);
            boolean appStatus = ai.enabled;
            if (getPkgInfo.contains("com.snapchat.android") && appStatus) {
                // APP NOT INSTALLED
//                Intent intent = new Intent(Intent.ACTION_VIEW,
//                        Uri.parse("https://snapchat.com/add/" + providerSnapchat));
//                startActivity(intent);
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("snapchat://add/" + providerSnapchat));
                startActivity(intent);

            } else {
                callWebviewWithUrl("https://snapchat.com/add/" + providerSnapchat);

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

            // APP NOT INSTALLED
            //callWebviewWithUrl("https://twitter.com/"+providerTwitter,providerTwitter);
            callWebviewWithUrl("https://snapchat.com/add/" + providerSnapchat);
        }


    }

    public void openTimingsPopUp(Context context) {

        ArrayList<PopupTimeItem> item = new ArrayList<PopupTimeItem>();
        if (getResources().getConfiguration().locale.getLanguage().equals("en")) {
            for (int i = 0; i < providerOpeningTimeDay.length; i++) {
                if (providerOpeningTime[i].equals("Holiday")) {
                    item.add(new PopupTimeItem(providerOpeningTimeDay[i], providerOpeningTime[i], "", "", ""));
                } else {
                    item.add(new PopupTimeItem(providerOpeningTimeDay[i], providerOpeningTime[i], providerOpeningTitle[i], providerClosingTime[i], providerClosingTitle[i]));
                }
            }
        } else {
            for (int i = 0; i < providerOpeningTimeDay.length; i++) {
                item.add(new PopupTimeItem(providerOpeningTimeDay[i], providerOpeningTimeArabic[i], providerOpeningTitleArabic[i], providerClosingTimeArabic[i], providerClosingTitleArabic[i]));
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.popup_listview_layout, null);
        ListView listView = (ListView) view.findViewById(R.id.popupview);
        listView.setAdapter(new PopupAdapter(getContext(), item));
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private String getDate(long milliSeconds) {
//        // Create a DateFormatter object for displaying date in specified
//        // format.
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
//        // Create a calendar object that will convert the date and time value in
//        // milliseconds to date
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }


    public void getServiceProviderDataInner(int providerId) {
        progressBar.setVisibility(View.VISIBLE);
        qFindPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        accessToken = qFindPreferences.getString("AccessToken", null);
        if (accessToken != null) {
            ApiInterface apiInterface;
            apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<ServiceProviderDataResponse> call = apiInterface.getServiceProviderData(accessToken, providerId, "");
            call.enqueue(new Callback<ServiceProviderDataResponse>() {
                @Override
                public void onResponse(Call<ServiceProviderDataResponse> call, Response<ServiceProviderDataResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            serviceProviderDataResponse = response.body();
                            if (serviceProviderDataResponse.getCode().equals("200")) {
                                serviceProviderResult = serviceProviderDataResponse.getResult();
                                String[] day = new String[7], openingTime = new String[7], openingTimeArabic = new String[7], closingTime = new String[7], closingTimeArabic = new String[7], openingTitle = new String[7], openingTitleArabic = new String[7], closingTitle = new String[7], closingTitleArabic = new String[7];
                                for (int i = 0; i < serviceProviderResult.getServiceProviderTimeLists().size(); i++) {
                                    day[i] = serviceProviderResult.getServiceProviderTimeLists().get(i).getServiceProviderTimeDay();
                                    openingTime[i] = serviceProviderResult.getServiceProviderTimeLists().get(i).getServiceProviderOpeningTime();
                                    openingTimeArabic[i] = serviceProviderResult.getServiceProviderTimeLists().get(i).getServiceProviderOpeningTimeArabic();
                                    closingTime[i] = serviceProviderResult.getServiceProviderTimeLists().get(i).getServiceProviderClosingTime();
                                    closingTimeArabic[i] = serviceProviderResult.getServiceProviderTimeLists().get(i).getServiceProviderClosingTimeArabic();
                                    openingTitle[i] = serviceProviderResult.getServiceProviderTimeLists().get(i).getServiceProviderOpeningTitle();
                                    openingTitleArabic[i] = serviceProviderResult.getServiceProviderTimeLists().get(i).getServiceProviderOpeningTitleArabic();
                                    closingTitle[i] = serviceProviderResult.getServiceProviderTimeLists().get(i).getServiceProviderClosingTitle();
                                    closingTitleArabic[i] = serviceProviderResult.getServiceProviderTimeLists().get(i).getServiceProviderClosingTitleArabic();
                                }

                                providerName = serviceProviderResult.getServiceProviderName();
                                providerNameArabic = serviceProviderResult.getServiceProviderNameArabic();
                                providerLocation = serviceProviderResult.getServiceProviderLocation();
                                providerLocationArabic = serviceProviderResult.getServiceProviderLocationArabic();
                                providerLogo = serviceProviderResult.getServiceProviderLogo();
                                providerPageId = serviceProviderResult.getServiceProviderId();
                                providerAddress = serviceProviderResult.getServiceProviderAddress();
                                providerAddressArabic = serviceProviderResult.getServiceProviderAddressArabic();
                                providerMail = serviceProviderResult.getServiceProviderMail();
                                providerWebsite = serviceProviderResult.getServiceProviderWebsite();
                                if (providerWebsite.contains("http")) {

                                    try {
                                        uri = new URI(providerWebsite);
                                        path = uri.getAuthority();
                                    } catch (URISyntaxException e) {
                                        e.printStackTrace();
                                    }

                                }
                                providerMobile = serviceProviderResult.getServiceProviderMobile();
                                providerLatLong = serviceProviderResult.getServiceProviderMapLocation();
                                providerFacebook = serviceProviderResult.getServiceProviderFacebook();
                                providerLinkedin = serviceProviderResult.getServiceProviderLinkedin();
                                providerInstagram = serviceProviderResult.getServiceProviderInstagram();
                                providerTwitter = serviceProviderResult.getServiceProviderTwitter();
                                providerSnapchat = serviceProviderResult.getServiceProviderSnapchat();
                                providerGooglePlus = serviceProviderResult.getServiceProviderGoogleplus();
                                providerOpeningTimeDay = day;
                                providerOpeningTime = openingTime;
                                providerClosingTime = closingTime;
                                providerOpeningTimeArabic = openingTimeArabic;
                                providerClosingTimeArabic = closingTimeArabic;
                                providerOpeningTitle = openingTitle;
                                providerClosingTitle = closingTitle;
                                providerOpeningTitleArabic = openingTitleArabic;
                                providerClosingTitleArabic = closingTitleArabic;
                                progressBar.setVisibility(View.GONE);
                                showInformationData();

                            }
                        }
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Util.showToast(getResources().getString(R.string.error_in_connecting), getContext());
                    }
                }

                @Override
                public void onFailure(Call<ServiceProviderDataResponse> call, Throwable t) {
                    if (t instanceof IOException) {
                        progressBar.setVisibility(View.GONE);
                        Util.showToast(getResources().getString(R.string.check_network), getContext());
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Util.showToast(getResources().getString(R.string.error_in_connecting), getContext());
                    }
                }
            });
        }
    }


}
