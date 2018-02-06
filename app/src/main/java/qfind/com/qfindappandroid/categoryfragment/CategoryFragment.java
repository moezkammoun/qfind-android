package qfind.com.qfindappandroid.categoryfragment;

import android.content.SharedPreferences;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lightsky.infiniteindicator.IndicatorConfiguration;
import cn.lightsky.infiniteindicator.InfiniteIndicator;
import cn.lightsky.infiniteindicator.OnPageClickListener;
import cn.lightsky.infiniteindicator.Page;
import qfind.com.qfindappandroid.BaseActivity;
import qfind.com.qfindappandroid.PicassoLoader;
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
    CategoryFragmentPresenterImpl categoryFragmentPresenterImpl;
    public Typeface mtypeFace;
    RecyclerViewClickListener recyclerViewClickListener;
    ArrayList<MainCategoryItemList> mainCategoryItemList;
    ArrayList<SubCategoryItemList> subCategoryItemList;
    ArrayList<ServiceProviderListDetails> serviceProviderListDetails;
    String accessToken, subCategoryNameForFragmentTittle, serviceProviderNameForFragmentTittle;
    SharedPreferences qFindPreferences;
    @Nullable
    @BindView(R.id.progressBarLoading)
    ProgressBar progressBar;
    @Nullable
    @BindView(R.id.empty_text_view_info)
    TextView emptyTextView;
    private IndicatorConfiguration configurationForFragment;
    private InfiniteIndicator mAnimCircleIndicator;
    private PicassoLoader picassoLoaderForFragment;
    boolean isSubCategory = false;
    boolean isSecondPage = false;

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.categoryPageStatus = 1;

        ((ContainerActivity) getActivity()).getMainCategoryItemsList();


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
        categoryFragmentPresenterImpl = new CategoryFragmentPresenterImpl(getContext(), this, recyclerViewClickListener);
        initialSetUp();
        if (subCategoryItemList != null || mainCategoryItemList != null || serviceProviderListDetails != null)
            hideLoader(false);
        setClickListenerForSubCategoryButton();
        ((ContainerActivity) getActivity()).setupBottomNavigationBar();

    }


    @Override
    public void loadAds(ArrayList<Page> adsImages) {
        picassoLoaderForFragment = new PicassoLoader();
        if (getResources().getConfiguration().locale.getLanguage().equals("en")) {
            configurationForFragment = new IndicatorConfiguration.Builder()
                    .imageLoader(picassoLoaderForFragment)
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
            mAnimCircleIndicator.init(configurationForFragment);
            mAnimCircleIndicator.notifyDataChange(adsImages);
        } else {
            configurationForFragment = new IndicatorConfiguration.Builder()
                    .imageLoader(picassoLoaderForFragment)
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
            mAnimCircleIndicator.init(configurationForFragment);
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
        if (configurationForFragment != null)
            mAnimCircleIndicator.stop();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (configurationForFragment != null)
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
                if (Util.categoryPageStatus == 1) {
                    isSubCategory = mainCategoryItemList.get(position).getSubCategoryStatus();
                    subCategoryNameForFragmentTittle = mainCategoryItemList.get(position).getCategoryName();
                    if (isSubCategory) {
                        getSubCategoryItemDetails(mainCategoryItemList.get(position).getCategoryId(),
                                subCategoryNameForFragmentTittle);
                    } else {
                        isSubCategory = false;
                        getServiceProviderList(mainCategoryItemList.get(position).getCategoryId(), subCategoryNameForFragmentTittle);
                    }

                } else if (Util.categoryPageStatus == 2) {
                    if (isSubCategory)
                        serviceProviderNameForFragmentTittle = subCategoryItemList.get(position).getSubCategoryName();
                    else
                        serviceProviderNameForFragmentTittle = mainCategoryItemList.get(position).getCategoryName();

                    getServiceProviderList(subCategoryItemList.get(position).getSubCategoryId(), serviceProviderNameForFragmentTittle);

                } else if (Util.categoryPageStatus == 3) {
                    if (isSubCategory) {
                        serviceProviderNameForFragmentTittle = subCategoryItemList.get(position).getSubCategoryName();
                        ((BaseActivity) getActivity()).showServiceProviderDetailPage(
                                serviceProviderListDetails.get(position).getServiceProviderName(),
                                serviceProviderListDetails.get(position).getServiceProviderLocation(),
                                serviceProviderListDetails.get(position).getServiceProviderMobile(),
                                serviceProviderListDetails.get(position).getServiceProviderAddress(),
                                serviceProviderListDetails.get(position).getServiceProviderWebsite(),
                                serviceProviderListDetails.get(position).getServiceProviderOpeningTime(),
                                serviceProviderListDetails.get(position).getServiceProviderMail(),
                                serviceProviderListDetails.get(position).getServiceProviderFacebook(),
                                serviceProviderListDetails.get(position).getServiceProviderLinkedin(),
                                serviceProviderListDetails.get(position).getServiceProviderInstagram(),
                                serviceProviderListDetails.get(position).getServiceProviderTwitter(),
                                serviceProviderListDetails.get(position).getServiceProviderSnapchat(),
                                serviceProviderListDetails.get(position).getServiceProviderGoogleplus(),
                                serviceProviderListDetails.get(position).getServiceProviderMapLocation(),
                                serviceProviderListDetails.get(position).getServiceProviderLogo());

                        ((ContainerActivity) getActivity()).showInfoToolbar(serviceProviderListDetails.
                                        get(position).getServiceProviderLocation(),
                                serviceProviderListDetails.get(position).getServiceProviderLocation());
                    } else {
                        serviceProviderNameForFragmentTittle = mainCategoryItemList.get(position).getCategoryName();
                        ((BaseActivity) getActivity()).showServiceProviderDetailPage(
                                serviceProviderListDetails.get(position).getServiceProviderName(),
                                serviceProviderListDetails.get(position).getServiceProviderLocation(),
                                serviceProviderListDetails.get(position).getServiceProviderMobile(),
                                serviceProviderListDetails.get(position).getServiceProviderAddress(),
                                serviceProviderListDetails.get(position).getServiceProviderWebsite(),
                                serviceProviderListDetails.get(position).getServiceProviderOpeningTime(),
                                serviceProviderListDetails.get(position).getServiceProviderMail(),
                                serviceProviderListDetails.get(position).getServiceProviderFacebook(),
                                serviceProviderListDetails.get(position).getServiceProviderLinkedin(),
                                serviceProviderListDetails.get(position).getServiceProviderInstagram(),
                                serviceProviderListDetails.get(position).getServiceProviderTwitter(),
                                serviceProviderListDetails.get(position).getServiceProviderSnapchat(),
                                serviceProviderListDetails.get(position).getServiceProviderGoogleplus(),
                                serviceProviderListDetails.get(position).getServiceProviderMapLocation(),
                                serviceProviderListDetails.get(position).getServiceProviderLogo());

                        ((ContainerActivity) getActivity()).showInfoToolbar(serviceProviderListDetails.
                                get(position).getServiceProviderName(), serviceProviderListDetails.
                                get(position).getServiceProviderLocation());

                    }
                }
            }
        };
    }

    public void initialSetUp() {
        if (Util.categoryPageStatus == 1) {
            if (subCategoryBackButton.getVisibility() == View.VISIBLE) {
                subCategoryBackButton.setVisibility(View.GONE);
            }
            categoryFragmentTittleText.setText(R.string.categories_text);
            categoryFragmentPresenterImpl.getImagesForAds();
            categoryFragmentPresenterImpl.getCategoryItemsDetails(mainCategoryItemList);

        } else if (Util.categoryPageStatus == 2) {
            if (subCategoryBackButton.getVisibility() == View.GONE) {
                subCategoryBackButton.setVisibility(View.VISIBLE);
            }
            if (isSubCategory) {
                categoryFragmentTittleText.setText(subCategoryNameForFragmentTittle);
                categoryFragmentPresenterImpl.getImagesForAds();
                categoryFragmentPresenterImpl.getSubCategoryItemsDetails(subCategoryItemList);
            } else {
                categoryFragmentTittleText.setText(subCategoryNameForFragmentTittle);
                categoryFragmentPresenterImpl.getImagesForAds();
                categoryFragmentPresenterImpl.getServieProvidersList(serviceProviderListDetails);

            }
        } else if (Util.categoryPageStatus == 3) {

            if (subCategoryBackButton.getVisibility() == View.GONE) {
                subCategoryBackButton.setVisibility(View.VISIBLE);
            }
            if (isSubCategory) {
                categoryFragmentTittleText.setText(serviceProviderNameForFragmentTittle);
            } else {
                categoryFragmentTittleText.setText(subCategoryNameForFragmentTittle);
            }

            categoryFragmentPresenterImpl.getImagesForAds();
            categoryFragmentPresenterImpl.getServieProvidersList(serviceProviderListDetails);
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
        if (Util.categoryPageStatus == 3) {
            Util.categoryPageStatus = 2;
        }
        if (Util.categoryPageStatus == 2) {
            if (isSubCategory) {
                if (isSecondPage) {
                    categoryFragmentPresenterImpl.getCategoryItemsDetails(mainCategoryItemList);
                    categoryFragmentTittleText.setText(R.string.categories_text);
                    subCategoryBackButton.setVisibility(View.GONE);
                    Util.categoryPageStatus = 1;
                    isSecondPage = false;
                } else {
                    categoryFragmentPresenterImpl.getSubCategoryItemsDetails(subCategoryItemList);
                    categoryFragmentTittleText.setText(subCategoryNameForFragmentTittle);
                    subCategoryBackButton.setVisibility(View.VISIBLE);
                    Util.categoryPageStatus = 2;
                    isSubCategory = false;
                }

            } else {
                categoryFragmentPresenterImpl.getCategoryItemsDetails(mainCategoryItemList);
                categoryFragmentTittleText.setText(R.string.categories_text);
                subCategoryBackButton.setVisibility(View.GONE);
                Util.categoryPageStatus = 1;
                isSecondPage = false;
            }

        }
        hideLoader(false);

    }

    public void setRecyclerViewDatas(ArrayList<MainCategoryItemList> mainCategoryItemList) {
        this.mainCategoryItemList = mainCategoryItemList;
        categoryFragmentPresenterImpl.getCategoryItemsDetails(mainCategoryItemList);

    }

    public void getSubCategoryItemDetails(int categoryId, final String subCategoryName) {
        progressBar.setVisibility(View.VISIBLE);
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
                                Util.categoryPageStatus = 2;
                                isSecondPage = true;
                                categoryFragmentTittleText.setText(subCategoryName);
                                subCategoryBackButton.setVisibility(View.VISIBLE);

                            } else {
                                isSubCategory = false;
                                Util.showToast(getResources().getString(R.string.something_went_wrong), getContext());
                                //emptyTextView.setVisibility(View.VISIBLE);
                            }
                        }

                    } else {
                        isSubCategory = false;
                        Util.showToast(getResources().getString(R.string.error_in_connecting), getContext());
                        //emptyTextView.setVisibility(View.VISIBLE);

                    }
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<SubCategory> call, Throwable t) {
                    Util.showToast(getResources().getString(R.string.check_network), getContext());
                    isSubCategory = false;
                    progressBar.setVisibility(View.GONE);
                    //emptyTextView.setVisibility(View.VISIBLE);

                }
            });

        }
    }

    public void hideLoader(boolean emptyTextStatus) {
        progressBar.setVisibility(View.GONE);
        if (emptyTextStatus) {
            emptyTextView.setVisibility(View.VISIBLE);
        } else {
            emptyTextView.setVisibility(View.GONE);
        }
    }

    public void getServiceProviderList(int categoryId, final String subCategoryName) {
        progressBar.setVisibility(View.VISIBLE);
        int mainCategoryId = categoryId;
        accessToken = qFindPreferences.getString("AccessToken", null);
        if (accessToken != null) {
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<ServiceProviderList> call = apiService.
                    getListOfServiceProvider(accessToken, qFindPreferences.getInt("AppLanguage", 1),
                            mainCategoryId, 10, "dsdf");
            call.enqueue(new Callback<ServiceProviderList>() {
                @Override
                public void onResponse(Call<ServiceProviderList> call, Response<ServiceProviderList> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            ServiceProviderList serviceProviderList = response.body();
                            if (serviceProviderList.getCode().equals("200")) {
                                serviceProviderListDetails = serviceProviderList.getServiceProviderListDetails();
                                categoryFragmentPresenterImpl.getServieProvidersList(serviceProviderListDetails);
                                Util.categoryPageStatus = 3;
                                isSecondPage = false;
                                categoryFragmentTittleText.setText(subCategoryName);
                                subCategoryBackButton.setVisibility(View.VISIBLE);

                            } else {
                                Util.showToast(getResources().getString(R.string.something_went_wrong), getContext());
                                //emptyTextView.setVisibility(View.VISIBLE);
                                if (isSubCategory)
                                    Util.categoryPageStatus = 2;
                                else
                                    Util.categoryPageStatus = 1;
                            }

                        }
                    } else {
                        Util.showToast(getResources().getString(R.string.error_in_connecting), getContext());
                        //emptyTextView.setVisibility(View.VISIBLE);
                        if (isSubCategory)
                            Util.categoryPageStatus = 2;
                        else
                            Util.categoryPageStatus = 1;
                    }
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<ServiceProviderList> call, Throwable t) {
                    Util.showToast(getResources().getString(R.string.check_network), getContext());
                    progressBar.setVisibility(View.GONE);
                    if (isSubCategory)
                        Util.categoryPageStatus = 2;
                    else
                        Util.categoryPageStatus = 1;
                }
            });
        }
    }


}
