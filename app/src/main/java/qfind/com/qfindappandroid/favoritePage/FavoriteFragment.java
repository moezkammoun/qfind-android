package qfind.com.qfindappandroid.favoritePage;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import qfind.com.qfindappandroid.DataBaseHandler;
import qfind.com.qfindappandroid.R;
import qfind.com.qfindappandroid.categorycontaineractivity.ContainerActivity;

public class FavoriteFragment extends Fragment {

    @BindView(R.id.favorite_list)
    RecyclerView favoriteView;
    @BindView(R.id.favorite_title)
    TextView favoriteTitle;
    @BindView(R.id.progressBarLoading)
    ProgressBar mProgressBarLoading;
    favoriteAdapter resultsAdapter;
    List<FavoriteModel> favoriteModelList;
    FavoriteModel item;
    Typeface mTypeFace;
    @BindView(R.id.back_button)
    ImageView backButton;
    @BindView(R.id.empty_text_view_info)
    TextView emptyTextView;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setFontTypeForText();
        favoriteModelList = new ArrayList<>();
        DataBaseHandler db = new DataBaseHandler(getContext());
        Log.d("Reading: ", "Reading all item..");
        List<FavoriteModel> fav = db.getAllFavorites();
        if (fav.size() != 0) {
            emptyTextView.setVisibility(View.GONE);
            for (FavoriteModel cn : fav) {
                String log = "Id: " + cn.getId() + " ,item: " + cn.getItem() + " ,des: " + cn.getItemDescription()
                        + " ,pid: " + cn.getPageId();
                // Writing Contacts to log
                Log.d("item: ", log);
                item = new FavoriteModel(cn.getItem(), cn.getItemDescription(),cn.getItemArabic(),cn.getItemDescriptionArabic(),
                        cn.getUrl(), cn.getPageId(),
                        cn.getProviderPhone(), cn.getProviderWebsite(), cn.getProviderAddress(), cn.getProviderOpeningTime(),
                        cn.getProviderMail(), cn.getProviderFacebook(), cn.getProviderLinkedIn(),
                        cn.getProviderInstagram(), cn.getProviderTwitter(), cn.getProviderSnapchat(), cn.getProviderGooglePlus(),
                        cn.getProviderLatlong()
                );
                favoriteModelList.add(0,item);

            }
            favoriteAdapter adapter = new favoriteAdapter(getContext(), favoriteModelList);
            favoriteView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            favoriteView.setAdapter(adapter);
            adapter.notifyItemInserted(0);
            favoriteView.smoothScrollToPosition(0);
        }
        else{
            emptyTextView.setVisibility(View.VISIBLE);

        }
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        ((ContainerActivity) getActivity()).setupBottomNavigationBar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    public void setFontTypeForText() {
        if (getResources().getConfiguration().locale.getLanguage().equals("en")) {
            mTypeFace = Typeface.createFromAsset(getActivity().getAssets(),
                    "fonts/Lato-Bold.ttf");
        } else {
            mTypeFace = Typeface.createFromAsset(getActivity().getAssets(),
                    "fonts/GE_SS_Unique_Bold.otf");
        }

        favoriteTitle.setTypeface(mTypeFace);
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
