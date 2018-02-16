package qfind.com.qfindappandroid.informationFragment;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import qfind.com.qfindappandroid.BaseActivity;
import qfind.com.qfindappandroid.DataBaseHandler;
import qfind.com.qfindappandroid.R;
import qfind.com.qfindappandroid.SimpleDividerItemDecoration;
import qfind.com.qfindappandroid.Util;
import qfind.com.qfindappandroid.categorycontaineractivity.ContainerActivity;
import qfind.com.qfindappandroid.historyPage.HistoryItem;
import qfind.com.qfindappandroid.predictiveSearch.ServiceProviderResult;
import qfind.com.qfindappandroid.retrofitinstance.ApiClient;
import qfind.com.qfindappandroid.retrofitinstance.ApiInterface;
import qfind.com.qfindappandroid.searchResultsFragment.SearchedItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InformationFragment extends Fragment {
    ArrayList<InformationFragmentModel> informationData = new ArrayList<>();
    RecyclerView recyclerView;
    InformationFragmentAdapter adapter;
    ProgressBar progressBar;
    TextView emptyTextView;

    String providerName, providerLocation, providerMobile, providerWebsite, providerAddress,
            providerOpeningTime, providerMail, providerFacebook, providerLinkedin, providerInstagram,
            providerTwitter, providerSnapchat, providerGooglePlus, providerLatLong, providerLogo;
    URI uri = null;
    String path;
    private int language;
    private int providerId;

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

        DataBaseHandler db = new DataBaseHandler(getContext());
        HistoryItem dataModel = new HistoryItem();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        dataModel.setDay(sdf.format(new Date()));
        dataModel.setTitke(bundle.getString("providerName"));
        dataModel.setImage(bundle.getString("providerLogo"));
        dataModel.setDescription(bundle.getString("providerLocation"));
        dataModel.setPageId(bundle.getInt("providerId"));
        dataModel.setProviderPhone(bundle.getString("providerMobile"));
        dataModel.setProviderAddress(bundle.getString("providerAddress"));
        dataModel.setProviderWebsite(bundle.getString("providerWebsite"));
        dataModel.setProviderOpeningTime(bundle.getString("providerOpeningTime"));
        dataModel.setProviderMail(bundle.getString("providerMail"));
        dataModel.setProviderFacebook(bundle.getString("providerFacebook"));
        dataModel.setProviderLinkedIn(bundle.getString("providerLinkedIn"));
        dataModel.setProviderInstagram(bundle.getString("providerInstagram"));
        dataModel.setProviderTwitter(bundle.getString("providerTwitter"));
        dataModel.setProviderSnapchat(bundle.getString("providerSnapchat"));
        dataModel.setProviderGooglePlus(bundle.getString("providerGooglePlus"));
        dataModel.setProviderLatlong(bundle.getString("providerLatLong"));


        if (db.checkHistoryById(bundle.getInt("providerId"))) {
            db.updateHistory(dataModel, bundle.getInt("providerId"));
        } else {
            db.addHistory(dataModel);

        }

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
        providerLogo = bundle.getString("providerLogo");
        providerId = bundle.getInt("providerId");
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
        if (adapter != null)
            adapter.clear();
        adapter = new InformationFragmentAdapter(getContext(), getInformationData());
        recyclerView.setAdapter(adapter);

        if (informationData == null)
            emptyTextView.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);
        ((ContainerActivity) getActivity()).setupBottomNavigationBar();
        ((ContainerActivity) getActivity()).showInfoToolbar(providerName, providerLocation);
        memoryLeakingCode();
        if (providerName.equals("")) {
            progressBar.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.GONE);
                    emptyTextView.setVisibility(View.VISIBLE);
                }
            }, 1500);

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

    public void memoryLeakingCode() {

    }
}
