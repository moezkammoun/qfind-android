package qfind.com.qfindappandroid.categoryfragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import qfind.com.qfindappandroid.InformationPage.InformationPage;
import qfind.com.qfindappandroid.R;

public class CategoryFragment extends Fragment implements CategoryFragmentView,
        BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    @BindView(R.id.slider)
    SliderLayout sliderLayout;
    @BindView(R.id.category_item_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.category_text)
    TextView category;
    private CategoryItemAdapter categoryItemAdapter;
    private List<Categories> categoriesList;

    CategoryFragmentPresenterImpl categoryFragmentPresenterImpl;

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryFragmentPresenterImpl = new CategoryFragmentPresenterImpl(this);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        categoryFragmentPresenterImpl.getImagesForAds();
        categoryFragmentPresenterImpl.getCategoryItemsDetails(getContext());
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

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
    public void loadAds(ArrayList<String> adsImages) {

        for (int i = 0; i < adsImages.size(); i++) {
            CustomSlider textSliderView = new CustomSlider(getContext());
            textSliderView
                    .image(adsImages.get(i))
                    .setScaleType(BaseSliderView.ScaleType.CenterInside)
                    .setOnSliderClickListener(CategoryFragment.this);
            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(3000);
        sliderLayout.addOnPageChangeListener(CategoryFragment.this);
    }

    @Override
    public void setCategoryItemRecyclerView(CategoryItemAdapter categoryItemAdapter) {
        this.categoryItemAdapter = categoryItemAdapter;
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(categoryItemAdapter);
    }


}
