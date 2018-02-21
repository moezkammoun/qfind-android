package qfind.com.qfindappandroid.searchResultsFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import qfind.com.qfindappandroid.BaseActivity;
import qfind.com.qfindappandroid.R;
import qfind.com.qfindappandroid.Util;
import qfind.com.qfindappandroid.informationFragment.InformationFragment;
import qfind.com.qfindappandroid.predictiveSearch.SearchResultsResponse;
import qfind.com.qfindappandroid.predictiveSearch.ServiceProviderResult;
import qfind.com.qfindappandroid.retrofitinstance.ApiClient;
import qfind.com.qfindappandroid.retrofitinstance.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultsFragment extends Fragment {

    RecyclerView mRecyclerView;
    TextView mEmtyTextView, pageTitle;
    ProgressBar mProgressBarLoading;
    ResultsAdapter resultsAdapter;
    List<SearchedItem> searchedItemList = new ArrayList<>();
    SearchedItem item;
    Typeface mTypeFace;
    ImageView backButton;
    private ApiInterface apiService;
    private SharedPreferences qFindPreferences;
    private String accessToken;
    private int language;
    SearchResultsResponse searchResultsResponse;
    List<ServiceProviderResult> serviceProviderResultList;
    int searchType;
    String searchKey;

    public SearchResultsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_results, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.search_results_list);
        mEmtyTextView = (TextView) view.findViewById(R.id.searchEmptyTextView);
        mProgressBarLoading = (ProgressBar) view.findViewById(R.id.progressBarLoading);
        pageTitle = (TextView) view.findViewById(R.id.search_title);
        backButton = (ImageView) view.findViewById(R.id.back_button);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        resultsAdapter = new ResultsAdapter(getContext(), searchedItemList);
        mRecyclerView.setAdapter(resultsAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), mRecyclerView, new SearchResultsClickListener() {
            @Override
            public void onClick(View view, int position) {
                ((BaseActivity) getActivity()).showServiceProviderDetailPage(
                        searchedItemList.get(position).getProviderName(),
                        searchedItemList.get(position).getProviderLocation(),
                        searchedItemList.get(position).getProviderNameArabic(),
                        searchedItemList.get(position).getProviderLocationArabic(),
                        searchedItemList.get(position).getProviderPhone(),
                        searchedItemList.get(position).getProviderAddress(),
                        searchedItemList.get(position).getProviderWebsite(),
                        searchedItemList.get(position).getProviderOpeningTime(),
                        searchedItemList.get(position).getProviderMail(),
                        searchedItemList.get(position).getProviderFacebook(),
                        searchedItemList.get(position).getProviderLinkedIn(),
                        searchedItemList.get(position).getProviderInstagram(),
                        searchedItemList.get(position).getProviderTwitter(),
                        searchedItemList.get(position).getProviderSnapchat(),
                        searchedItemList.get(position).getProviderGooglePlus(),
                        searchedItemList.get(position).getProviderLatlong(),
                        searchedItemList.get(position).getProviderLogo(),
                        searchedItemList.get(position).getProviderId()
                        );
            }

        }));
        Bundle bundle = getArguments();
        searchType = bundle.getInt("searchType", 0);
        searchKey = bundle.getString("searchKey");
        if (searchKey != null)
            searchKey = searchKey.trim();
        mProgressBarLoading.setVisibility(View.VISIBLE);
        getSearchResults(searchKey, searchType);
        return view;
    }

    public void setFontTypeForText() {
        if (getResources().getConfiguration().locale.getLanguage().equals("en")) {
            mTypeFace = Typeface.createFromAsset(getActivity().getAssets(),
                    "fonts/Lato-Bold.ttf");
        } else {
            mTypeFace = Typeface.createFromAsset(getActivity().getAssets(),
                    "fonts/GE_SS_Unique_Bold.otf");
        }

        pageTitle.setTypeface(mTypeFace);
    }

    public void getSearchResults(String searchKey, Integer searchType) {
        qFindPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        accessToken = qFindPreferences.getString("AccessToken", null);
        language = qFindPreferences.getInt("AppLanguage", 1);
        if (accessToken != null) {
            apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<SearchResultsResponse> call = apiService.getSearchResults(accessToken, searchKey, language, searchType);
            call.enqueue(new Callback<SearchResultsResponse>() {
                @Override
                public void onResponse(Call<SearchResultsResponse> call, Response<SearchResultsResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            searchResultsResponse = response.body();
                            if (searchResultsResponse.getCode().equals("200")) {
                                serviceProviderResultList = searchResultsResponse.getResult();
                                for (int i = 0; i < serviceProviderResultList.size(); i++) {
                                    item = new SearchedItem(serviceProviderResultList.get(i).getServiceProviderName(),
                                            serviceProviderResultList.get(i).getServiceProviderLocation(),
                                            serviceProviderResultList.get(i).getServiceProviderNameArabic(),
                                            serviceProviderResultList.get(i).getServiceProviderLocationArabic(),
                                            serviceProviderResultList.get(i).getServiceProviderLogo(),
                                            serviceProviderResultList.get(i).getServiceProviderMobile(),
                                            serviceProviderResultList.get(i).getServiceProviderWebsite(),
                                            serviceProviderResultList.get(i).getServiceProviderAddress(),
                                            serviceProviderResultList.get(i).getServiceProviderOpeningTime(),
                                            serviceProviderResultList.get(i).getServiceProviderMail(),
                                            serviceProviderResultList.get(i).getServiceProviderFacebook(),
                                            serviceProviderResultList.get(i).getServiceProviderLinkedin(),
                                            serviceProviderResultList.get(i).getServiceProviderInstagram(),
                                            serviceProviderResultList.get(i).getServiceProviderTwitter(),
                                            serviceProviderResultList.get(i).getServiceProviderSnapchat(),
                                            serviceProviderResultList.get(i).getServiceProviderGoogleplus(),
                                            serviceProviderResultList.get(i).getServiceProviderMapLocation(),
                                            serviceProviderResultList.get(i).getServiceProviderLogo(),
                                            serviceProviderResultList.get(i).getServiceProviderId());
                                    searchedItemList.clear();
                                    searchedItemList.add(item);
                                }
                                resultsAdapter.notifyDataSetChanged();
                            } else {
                                mEmtyTextView.setVisibility(View.VISIBLE);
                            }
                        }

                    } else {
                        Util.showToast(getResources().getString(R.string.error_in_connecting), getContext());
                        mEmtyTextView.setVisibility(View.VISIBLE);
                    }
                    mProgressBarLoading.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<SearchResultsResponse> call, Throwable t) {
                    Util.showToast(getResources().getString(R.string.check_network), getContext());
                    mEmtyTextView.setVisibility(View.VISIBLE);
                    mProgressBarLoading.setVisibility(View.GONE);
                }
            });
        }

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setFontTypeForText();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
