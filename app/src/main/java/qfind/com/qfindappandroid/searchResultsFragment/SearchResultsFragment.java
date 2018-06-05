package qfind.com.qfindappandroid.searchResultsFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import qfind.com.qfindappandroid.BaseActivity;
import qfind.com.qfindappandroid.R;
import qfind.com.qfindappandroid.Util;
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
                ((BaseActivity) getActivity()).showServiceProviderDetailPageInner(
                        searchedItemList.get(position).getProviderName(),
                        searchedItemList.get(position).getProviderNameArabic(),
                        searchedItemList.get(position).getProviderLocation(),
                        searchedItemList.get(position).getProviderLocationArabic(),
                        searchedItemList.get(position).getProviderLogo(),
                        searchedItemList.get(position).getProviderId()

                );
            }

        }));

        /* cn.getProviderClosingTime(),cn.getProviderClosingTimeArabic(),cn.getProviderOpeningTitle(),
                        cn.getProviderClosingTitle(),cn.getProviderOpeningTitleArabic(),cn.getProviderClosingTitleArabic()
*/
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
                                searchedItemList.clear();
                                for (int i = 0; i < serviceProviderResultList.size(); i++) {
                                    String[] day = new String[7], openingTime = new String[7], openingTimeArabic = new String[7], closingTime = new String[7], closingTimeArabic = new String[7], openingTitle = new String[7], openingTitleArabic = new String[7], closingTitle = new String[7], closingTitleArabic = new String[7];
                                    for (int j = 0; j < serviceProviderResultList.get(i).getServiceProviderTimeLists().size(); j++) {
                                        day[j] = serviceProviderResultList.get(i).getServiceProviderTimeLists().get(j).getServiceProviderTimeDay();
                                        openingTime[j] = serviceProviderResultList.get(i).getServiceProviderTimeLists().get(j).getServiceProviderOpeningTime();
                                        openingTimeArabic[j] = serviceProviderResultList.get(i).getServiceProviderTimeLists().get(j).getServiceProviderOpeningTimeArabic();
                                        closingTime[j] = serviceProviderResultList.get(i).getServiceProviderTimeLists().get(j).getServiceProviderClosingTime();
                                        closingTimeArabic[j] = serviceProviderResultList.get(i).getServiceProviderTimeLists().get(j).getServiceProviderClosingTimeArabic();
                                        openingTitle[j] = serviceProviderResultList.get(i).getServiceProviderTimeLists().get(j).getServiceProviderOpeningTitle();
                                        openingTitleArabic[j] = serviceProviderResultList.get(i).getServiceProviderTimeLists().get(j).getServiceProviderOpeningTitleArabic();
                                        closingTitle[j] = serviceProviderResultList.get(i).getServiceProviderTimeLists().get(j).getServiceProviderClosingTitle();
                                        closingTitleArabic[j] = serviceProviderResultList.get(i).getServiceProviderTimeLists().get(j).getServiceProviderClosingTitleArabic();
                                    }
                                    item = new SearchedItem(
                                            serviceProviderResultList.get(i).getServiceProviderName(),
                                            serviceProviderResultList.get(i).getServiceProviderLocation(),
                                            serviceProviderResultList.get(i).getServiceProviderNameArabic(),
                                            serviceProviderResultList.get(i).getServiceProviderLocationArabic(),
                                            serviceProviderResultList.get(i).getServiceProviderLogo(),
                                            serviceProviderResultList.get(i).getServiceProviderMobile(),
                                            serviceProviderResultList.get(i).getServiceProviderWebsite(),
                                            serviceProviderResultList.get(i).getServiceProviderAddress(),
                                            serviceProviderResultList.get(i).getServiceProviderMail(),
                                            serviceProviderResultList.get(i).getServiceProviderFacebook(),
                                            serviceProviderResultList.get(i).getServiceProviderLinkedin(),
                                            serviceProviderResultList.get(i).getServiceProviderInstagram(),
                                            serviceProviderResultList.get(i).getServiceProviderTwitter(),
                                            serviceProviderResultList.get(i).getServiceProviderSnapchat(),
                                            serviceProviderResultList.get(i).getServiceProviderGoogleplus(),
                                            serviceProviderResultList.get(i).getServiceProviderMapLocation(),
                                            serviceProviderResultList.get(i).getServiceProviderLogo(),
                                            serviceProviderResultList.get(i).getServiceProviderId(),
                                            serviceProviderResultList.get(i).getServiceProviderAddressArabic(),day,
                                            openingTime,openingTimeArabic,closingTime,closingTimeArabic,
                                            openingTitle,closingTitle,openingTitleArabic,closingTitleArabic);
                                    searchedItemList.add(item);
                                }
                                resultsAdapter.notifyDataSetChanged();
                            } else {
                                mEmtyTextView.setVisibility(View.VISIBLE);
                            }
                        }
//                        if(searchResultsResponse.getCode().equals("404")){
//                            mEmtyTextView.setVisibility(View.VISIBLE);
//                        }

                    } else {
                       if(response.code()==500){
                           mEmtyTextView.setVisibility(View.VISIBLE);
                       }
                        Util.showToast(getResources().getString(R.string.error_in_connecting), getContext());
                        mEmtyTextView.setVisibility(View.VISIBLE);
                    }
                    mProgressBarLoading.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<SearchResultsResponse> call, Throwable t) {
                    if (t instanceof IOException) {
                        Util.showToast(getResources().getString(R.string.check_network), getContext());
                    } else {
                        Util.showToast(getResources().getString(R.string.error_in_connecting), getContext());
                    }
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
