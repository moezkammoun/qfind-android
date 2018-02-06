package qfind.com.qfindappandroid.informationFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import qfind.com.qfindappandroid.R;
import qfind.com.qfindappandroid.SimpleDividerItemDecoration;
import qfind.com.qfindappandroid.categorycontaineractivity.ContainerActivity;
import qfind.com.qfindappandroid.Util;
import qfind.com.qfindappandroid.categoryfragment.RecyclerViewClickListener;
import qfind.com.qfindappandroid.retrofitinstance.ApiClient;
import qfind.com.qfindappandroid.retrofitinstance.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InformationFragment extends Fragment {
    ArrayList<InformationFragmentModel> informationData = new ArrayList<>();
    RecyclerView recyclerView;
    private ApiResponse apiResponse;
    SharedPreferences qFindPreferences;
    String accessToken, infoPageTittle;
    ApiInterface apiService;
    ServiceProviderData serviceProviderData;
    InformationFragmentAdapter adapter;
    ProgressBar progressBar;
    TextView emptyTextView;
    String providerName, providerLocation, providerMobile, providerWebsite, providerAddress,
            providerOpeningTime, providerMail, providerFacebook, providerLinkedin, providerInstagram,
            providerTwitter, providerSnapchat, providerGooglePlus, providerLatLong;
    URI uri = null;
    String path;
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
        Bundle bundle = getArguments();
        providerName = bundle.getString("providerName");
        providerLocation = bundle.getString("providerLocation");
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
        providerOpeningTime = bundle.getString("providerOpeningTime");
        providerMail = bundle.getString("providerMail");
        providerFacebook = bundle.getString("providerFacebook");
        providerLinkedin = bundle.getString("providerLinkedin");
        providerInstagram = bundle.getString("providerInstagram");
        providerTwitter = bundle.getString("providerTwitter");
        providerSnapchat = bundle.getString("providerSnapchat");
        providerGooglePlus = bundle.getString("providerGooglePlus");
        providerLatLong = bundle.getString("providerLatLong");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBarLoading);
        emptyTextView = (TextView) view.findViewById(R.id.empty_text_view_info);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.ItemDecoration dividerItemDecoration = new SimpleDividerItemDecoration(getContext());
        recyclerView.addItemDecoration(dividerItemDecoration);
        setupRecyclerViewClickListener();
        adapter = new InformationFragmentAdapter(getContext(), getInformationData(), recyclerViewClickListener);
        recyclerView.setAdapter(adapter);
        if (informationData == null)
            emptyTextView.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);
        ((ContainerActivity) getActivity()).setupBottomNavigationBar();
        ((ContainerActivity) getActivity()).showInfoToolbar(providerName, providerLocation);


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
        if (providerAddress != null && !providerAddress.equals(""))
            informationData.add(new InformationFragmentModel(R.drawable.location_icon,
                    R.drawable.dot_icon, providerAddress, R.drawable.right_arrow));
        if (providerOpeningTime != null && !providerOpeningTime.equals(""))
            informationData.add(new InformationFragmentModel(R.drawable.clock_icon,
                    R.drawable.dot_icon, providerOpeningTime, R.drawable.right_arrow));
        if (providerMail != null && !providerMail.equals(""))
            informationData.add(new InformationFragmentModel(R.drawable.mail_icon,
                    R.drawable.dot_icon, providerMail, R.drawable.right_arrow));
        if (providerFacebook != null && !providerFacebook.equals(""))
            informationData.add(new InformationFragmentModel(R.drawable.facebook_icon,
                    R.drawable.dot_icon, providerFacebook, R.drawable.right_arrow));
        if (providerLinkedin != null && !providerLinkedin.equals(""))
            informationData.add(new InformationFragmentModel(R.drawable.linkedin,
                    R.drawable.dot_icon, providerLinkedin, R.drawable.right_arrow));
        if (providerInstagram != null && !providerInstagram.equals(""))
            informationData.add(new InformationFragmentModel(R.drawable.instagram,
                    R.drawable.dot_icon, providerInstagram, R.drawable.right_arrow));
        if (providerTwitter != null && !providerTwitter.equals(""))
            informationData.add(new InformationFragmentModel(R.drawable.twitter,
                    R.drawable.dot_icon, providerTwitter, R.drawable.right_arrow));
        if (providerSnapchat != null && !providerSnapchat.equals(""))
            informationData.add(new InformationFragmentModel(R.drawable.snapchat,
                    R.drawable.dot_icon, providerSnapchat, R.drawable.right_arrow));
        if (providerGooglePlus != null && !providerGooglePlus.equals(""))
            informationData.add(new InformationFragmentModel(R.drawable.google_plus,
                    R.drawable.dot_icon, providerGooglePlus, R.drawable.right_arrow));
        return informationData;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setupRecyclerViewClickListener() {
        recyclerViewClickListener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (informationData.get(position).getInfo_icon() == R.drawable.facebook_icon) {
                    Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                    String facebookUrl = getFacebookPageURL(getContext());
                    facebookIntent.setData(Uri.parse(facebookUrl));
                    startActivity(facebookIntent);
                }
                if (informationData.get(position).getInfo_icon() == R.drawable.phone_icon && providerMobile != null) {

                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + providerMobile));
                    startActivity(intent);

                }
                if (informationData.get(position).getInfo_icon() == R.drawable.web_icon && providerWebsite != null) {

                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(providerWebsite));
                    startActivity(browserIntent);

                }
                if (informationData.get(position).getInfo_icon() == R.drawable.location_icon &&
                        providerLatLong != null && providerLocation!=null) {
                    String geoUri = "http://maps.google.com/maps?q=loc:" + providerLatLong + " (" + providerLocation + ")";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                    startActivity(intent);
                }
                if (informationData.get(position).getInfo_icon() == R.drawable.mail_icon &&
                        providerMail != null) {
                    setUpEmail();
                }
            }
        };
    }

    public String getFacebookPageURL(Context context) {
        String FACEBOOK_URL = "https://www.facebook.com/publictheband/";
        String FACEBOOK_PAGE_ID = "publictheband";
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=https://www.facebook.com/" + providerFacebook+"/";
            } else { //older versions of fb app
                return "fb://page/" + providerFacebook;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return "https://www.facebook.com/"+providerFacebook+"/"; //normal web url
        }
    }

    public void setUpEmail(){
        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{providerMail});
//        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
//        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Text");
        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }
}
