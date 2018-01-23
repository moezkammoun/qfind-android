package qfind.com.qfindappandroid.categoryfragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lightsky.infiniteindicator.IndicatorConfiguration;
import cn.lightsky.infiniteindicator.InfiniteIndicator;
import cn.lightsky.infiniteindicator.OnPageClickListener;
import cn.lightsky.infiniteindicator.Page;
import qfind.com.qfindappandroid.InformationPage.InformationPage;
import qfind.com.qfindappandroid.R;

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
    private List<Categories> categoriesList;
    private InfiniteIndicator mAnimCircleIndicator;
    CategoryFragmentPresenterImpl categoryFragmentPresenterImpl;
    public Typeface mtypeFace;
    RecyclerViewClickListener recyclerViewClickListener;
    public static int categoryPageStatus;

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
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setupRecyclerViewClickListener();
        setFontTypeForText();
        initialSetUp();
        mAnimCircleIndicator = (InfiniteIndicator) view.findViewById(R.id.indicator_default_circle);

        categoryFragmentPresenterImpl.getImagesForAds();
        if (CategoryPageCurrentStatus.categoryPageStatus == 1) {
            categoryFragmentPresenterImpl.getCategoryItemsDetails(getContext());
        } else {
            //categoryFragmentPresenterImpl.getSubCategoryItemsDetails(getContext());
        }
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
                    categoryFragmentPresenterImpl.getSubCategoryItemsDetails(getContext());
                    CategoryPageCurrentStatus.categoryPageStatus = 2;
                    categoryFragmentTittleText.setText(R.string.sub_categoies_text);
                    subCategoryBackButton.setVisibility(View.VISIBLE);
                    setClickListenerForSubCategoryButton();
                } else if (CategoryPageCurrentStatus.categoryPageStatus == 2) {
                    Intent intent = new Intent(getActivity(), InformationPage.class);
                    startActivity(intent);
                }
            }
        };
    }

    public void initialSetUp() {
        if (subCategoryBackButton.getVisibility() == View.VISIBLE) {
            subCategoryBackButton.setVisibility(View.GONE);
        }
        CategoryPageCurrentStatus.categoryPageStatus = 1;
        categoryFragmentPresenterImpl = new CategoryFragmentPresenterImpl(this, recyclerViewClickListener);
        categoryFragmentTittleText.setText(R.string.categories_text);

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
        categoryFragmentPresenterImpl.getCategoryItemsDetails(getContext());
        CategoryPageCurrentStatus.categoryPageStatus = 1;
        categoryFragmentTittleText.setText(R.string.categories_text);
        subCategoryBackButton.setVisibility(View.GONE);
    }

}
