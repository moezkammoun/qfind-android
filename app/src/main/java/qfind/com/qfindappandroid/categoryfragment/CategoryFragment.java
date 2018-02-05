package qfind.com.qfindappandroid.categoryfragment;

import android.content.SharedPreferences;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lightsky.infiniteindicator.IndicatorConfiguration;
import cn.lightsky.infiniteindicator.InfiniteIndicator;
import cn.lightsky.infiniteindicator.OnPageClickListener;
import cn.lightsky.infiniteindicator.Page;
import qfind.com.qfindappandroid.R;
import qfind.com.qfindappandroid.Util;
import qfind.com.qfindappandroid.categorycontaineractivity.ContainerActivity;
import qfind.com.qfindappandroid.categorycontaineractivity.MainCategoryItemList;
import qfind.com.qfindappandroid.informationFragment.InformationFragment;
import qfind.com.qfindappandroid.retrofitinstance.ApiClient;
import qfind.com.qfindappandroid.retrofitinstance.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static cn.lightsky.infiniteindicator.IndicatorConfiguration.LEFT;
import static cn.lightsky.infiniteindicator.IndicatorConfiguration.RIGHT;

public class CategoryFragment extends Fragment implements CategoryFragmentView, ViewPager.OnPageChangeListener, OnPageClickListener {

    @BindView(R.id.category_item_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.sub_category_back_button)
    ImageView subCategoryBackButton;
    @BindView(R.id.category_fragment_tittle_text)
    TextView categoryFragmentTittleText;
    private CategoryItemAdapter categoryItemAdapter;
    private InfiniteIndicator mAnimCircleIndicator;
    CategoryFragmentPresenterImpl categoryFragmentPresenterImpl;
    public Typeface mtypeFace;
    RecyclerViewClickListener recyclerViewClickListener;
    public static int categoryPageStatus;
    ArrayList<MainCategoryItemList> mainCategoryItemList;
    ArrayList<SubCategoryItemList> subCategoryItemList;
    String accessToken, subCategoryName;
    SharedPreferences qFindPreferences;

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_category, container, false);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAnimCircleIndicator = (InfiniteIndicator) view.findViewById(R.id.indicator_default_circle);
        ButterKnife.bind(this, view);
        qFindPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        setupRecyclerViewClickListener();
        setFontTypeForText();
        initialSetUp();
        setClickListenerForSubCategoryButton();
        ((ContainerActivity) getActivity()).setupBottomNavigationBar();

    }


    @Override
    public void loadAds(ArrayList<Page> adsImages) {
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


    }

    @Override
    public void setCategoryItemRecyclerView(CategoryItemAdapter categoryItemAdapter) {
        this.categoryItemAdapter = categoryItemAdapter;
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(categoryItemAdapter);
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

    //To avoid memory leak ,you should release the res
    @Override
    public void onPause() {
        super.onPause();
        mAnimCircleIndicator.stop();
    }

    @Override
    public void onResume() {
        super.onResume();
        mAnimCircleIndicator.start();
    }

    public void setFontTypeForText() {
        if (getResources().getConfiguration().locale.getLanguage().equals("en")) {
            mtypeFace = Typeface.createFromAsset(getActivity().getAssets(),
                    "fonts/Lato-Bold.ttf");
        } else {
            mtypeFace = Typeface.createFromAsset(getActivity().getAssets(),
                    "fonts/GE_SS_Unique_Light.otf");
        }

        categoryFragmentTittleText.setTypeface(mtypeFace);
    }

    public void setupRecyclerViewClickListener() {
        recyclerViewClickListener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (CategoryPageCurrentStatus.categoryPageStatus == 1) {
                    subCategoryName = mainCategoryItemList.get(position).getCategoryName();
                    getSubCategoryItemDetails(mainCategoryItemList.get(position).getCategoryId(),
                            subCategoryName);

                } else if (CategoryPageCurrentStatus.categoryPageStatus == 2) {

                    loadInformationFragmentWithBundle(subCategoryItemList.get(position).getSubCategoryId(),
                            subCategoryItemList.get(position).getSubCategoryName(),
                            subCategoryItemList.get(position).getSubCategoryImage());
                    ((ContainerActivity) getActivity()).showInfoToolbar(subCategoryItemList.get(position).getSubCategoryName());
                }
            }
        };
    }

    public void initialSetUp() {
        categoryFragmentPresenterImpl = new CategoryFragmentPresenterImpl(getContext(), this, recyclerViewClickListener);
        if (CategoryPageCurrentStatus.categoryPageStatus == 1) {
            if (subCategoryBackButton.getVisibility() == View.VISIBLE) {
                subCategoryBackButton.setVisibility(View.GONE);
            }
            categoryFragmentTittleText.setText(R.string.categories_text);
            categoryFragmentPresenterImpl.getImagesForAds();
            categoryFragmentPresenterImpl.getCategoryItemsDetails(mainCategoryItemList);

        } else if (CategoryPageCurrentStatus.categoryPageStatus == 2) {
            if (subCategoryBackButton.getVisibility() == View.GONE) {
                subCategoryBackButton.setVisibility(View.VISIBLE);
            }
            categoryFragmentTittleText.setText(subCategoryName);
            categoryFragmentPresenterImpl.getImagesForAds();
            categoryFragmentPresenterImpl.getSubCategoryItemsDetails(subCategoryItemList);
        }


    }

    public void setClickListenerForSubCategoryButton() {
        subCategoryBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSubCategoryBackButtonClickAction();
            }
        });
    }

    public void setSubCategoryBackButtonClickAction() {
        categoryFragmentPresenterImpl.getCategoryItemsDetails(mainCategoryItemList);
        CategoryPageCurrentStatus.categoryPageStatus = 1;
        categoryFragmentTittleText.setText(R.string.categories_text);
        subCategoryBackButton.setVisibility(View.GONE);
    }


    public void setRecyclerViewDatas(ArrayList<MainCategoryItemList> mainCategoryItemList) {
        this.mainCategoryItemList = mainCategoryItemList;
        categoryFragmentPresenterImpl.getCategoryItemsDetails(mainCategoryItemList);

    }

    public void loadInformationFragmentWithBundle(int subCategoryId, String subCategoryNameForInfoPage, String subCategoryImage) {
        Bundle bundle = new Bundle();
        bundle.putInt("subCategoryId", subCategoryId);
        bundle.putString("subCategoryName", subCategoryNameForInfoPage);
        bundle.putString("subCategoryImage", subCategoryImage);
        InformationFragment informationFragment = new InformationFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        informationFragment.setArguments(bundle);
        transaction.replace(R.id.frame_container, informationFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void getSubCategoryItemDetails(int categoryId, final String subCategoryName) {
        int mainCategoryId = categoryId;
        accessToken = qFindPreferences.getString("AccessToken", null);
        if (accessToken != null) {
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<SubCategory> call = apiService.getSubCategory(accessToken, qFindPreferences.getInt("AppLanguage", 1), mainCategoryId);
            call.enqueue(new Callback<SubCategory>() {
                @Override
                public void onResponse(Call<SubCategory> call, Response<SubCategory> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            SubCategory subCategory = response.body();
                            if (subCategory.getCode().equals("200")) {
                                subCategoryItemList = subCategory.getSubCategoryItemList();
                                categoryFragmentPresenterImpl.getSubCategoryItemsDetails(subCategoryItemList);
                                CategoryPageCurrentStatus.categoryPageStatus = 2;
                                categoryFragmentTittleText.setText(subCategoryName);
                                subCategoryBackButton.setVisibility(View.VISIBLE);

                            } else {
                                Util.showToast(getResources().getString(R.string.something_went_wrong), getContext());
                            }
                        }

                    } else {
                        Util.showToast(getResources().getString(R.string.error_in_connecting), getContext());

                    }
                }

                @Override
                public void onFailure(Call<SubCategory> call, Throwable t) {
                    Util.showToast(getResources().getString(R.string.check_network), getContext());

                }
            });

        }
    }

}
