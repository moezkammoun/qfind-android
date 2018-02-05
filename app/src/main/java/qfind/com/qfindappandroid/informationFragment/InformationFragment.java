package qfind.com.qfindappandroid.informationFragment;

import android.content.Context;
import android.content.SharedPreferences;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import qfind.com.qfindappandroid.DataBaseHandler;
import qfind.com.qfindappandroid.R;
import qfind.com.qfindappandroid.SimpleDividerItemDecoration;
import qfind.com.qfindappandroid.Util;
import qfind.com.qfindappandroid.categorycontaineractivity.ContainerActivity;
import qfind.com.qfindappandroid.favoritePage.FavoriteModel;
import qfind.com.qfindappandroid.historyPage.HistoryItem;
import qfind.com.qfindappandroid.historyPage.HistoryPageMainModel;
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
    int subCategoryId;
    Bundle bundle;

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
        bundle = getArguments();
        DataBaseHandler db = new DataBaseHandler(getContext());
        HistoryItem dataModel = new HistoryItem();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        dataModel.setDay(sdf.format(new Date()));
        dataModel.setTitke(bundle.getString("subCategoryName"));
        dataModel.setImage(bundle.getString("subCategoryImage"));
        dataModel.setDescription(bundle.getString("subCategoryName"));
        db.addHistory(dataModel);

        subCategoryId = bundle.getInt("subCategoryId");
        infoPageTittle = bundle.getString("subCategoryName");
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
        adapter = new InformationFragmentAdapter(getContext(), informationData);
        recyclerView.setAdapter(adapter);
        ((ContainerActivity) getActivity()).setupBottomNavigationBar();
        ((ContainerActivity) getActivity()).showInfoToolbar(infoPageTittle);
        getServiceProviderData(subCategoryId);
        memoryLeakingCode();
    }

    public ArrayList<InformationFragmentModel> getInformationData() {
        informationData.add(new InformationFragmentModel(R.drawable.phone_icon,
                R.drawable.dot_icon, serviceProviderData.getServiceProviderMobile(), R.drawable.right_arrow));
        informationData.add(new InformationFragmentModel(R.drawable.web_icon,
                R.drawable.dot_icon, serviceProviderData.getServiceProviderWebsite(), R.drawable.right_arrow));
        informationData.add(new InformationFragmentModel(R.drawable.location_icon,
                R.drawable.dot_icon, serviceProviderData.getServiceProviderLocation(), R.drawable.right_arrow));
        informationData.add(new InformationFragmentModel(R.drawable.clock_icon,
                R.drawable.dot_icon, serviceProviderData.getServiceProviderOpeningTime(), R.drawable.right_arrow));
        informationData.add(new InformationFragmentModel(R.drawable.mail_icon,
                R.drawable.dot_icon, serviceProviderData.getServiceProviderMail(), R.drawable.right_arrow));
        informationData.add(new InformationFragmentModel(R.drawable.facebook_icon,
                R.drawable.dot_icon, serviceProviderData.getServiceProviderFacebook(), R.drawable.right_arrow));
        return informationData;
    }

    public void getServiceProviderData(int subCategoryId) {
        qFindPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        accessToken = qFindPreferences.getString("AccessToken", null);
        if (accessToken != null) {
            apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<ApiResponse> call = apiService.getServiceProviderData(accessToken, 1, subCategoryId, "");
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            apiResponse = response.body();
                            if (apiResponse.getCode().equals("200")) {
                                serviceProviderData = apiResponse.getResult();
                                getInformationData();
                                adapter.notifyDataSetChanged();
                            } else {
                                Util.showToast(getResources().getString(R.string.un_authorised), getContext());
                                emptyTextView.setVisibility(View.VISIBLE);
                            }
                        }

                    } else {
                        Util.showToast(getResources().getString(R.string.error_in_connecting), getContext());
                        emptyTextView.setVisibility(View.VISIBLE);
                    }
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    Util.showToast(getResources().getString(R.string.check_network), getContext());
                    progressBar.setVisibility(View.GONE);
                    emptyTextView.setVisibility(View.VISIBLE);
                }
            });
        }
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

    public void getBundle() {
        DataBaseHandler db = new DataBaseHandler(getContext());
        FavoriteModel favoriteModel = new FavoriteModel();
        favoriteModel.setItem(bundle.getString("subCategoryName"));
        favoriteModel.setItemDescription(bundle.getString("subCategoryName"));
        favoriteModel.setUrl(bundle.getString("subCategoryImage"));
        db.addFavorite(favoriteModel);
    }

}
