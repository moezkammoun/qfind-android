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
import qfind.com.qfindappandroid.BaseActivity;
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
        favoriteModelList = new ArrayList<>();
        DataBaseHandler db = new DataBaseHandler(getContext());
        Log.d("Reading: ", "Reading all item..");
        List<FavoriteModel> fav = db.getAllFavorites();
        for (FavoriteModel cn : fav) {
            String log = "Id: " + cn.getId() + " ,item: " + cn.getItem() + " ,des: " + cn.getItemDescription()
                    + " ,pid: " + cn.getPageId();
            // Writing Contacts to log
            Log.d("item: ", log);
            item = new FavoriteModel(cn.getItem(), cn.getItemDescription(), cn.getUrl(), cn.getPageId(),
                    cn.getProviderPhone(), cn.getProviderWebsite(), cn.getProviderAddress(), cn.getProviderOpeningTime(),
                    cn.getProviderMail(), cn.getProviderFacebook(), cn.getProviderLinkedIn(),
                    cn.getProviderInstagram(), cn.getProviderTwitter(), cn.getProviderSnapchat(), cn.getProviderGooglePlus(),
                    cn.getProviderLatlong()
            );
            favoriteModelList.add(item);
        }
        setFontTypeForText();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        favoriteAdapter adapter = new favoriteAdapter(getContext(), favoriteModelList);
        favoriteView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        favoriteView.setAdapter(adapter);

        favoriteView.addOnItemTouchListener(new RecyclerViewTouchListener(getContext(), favoriteView, new FavoriteClickListener() {
            @Override
            public void onClick(View view, int position) {
                ((BaseActivity) getActivity()).showServiceProviderDetailPage(
                        favoriteModelList.get(position).getItem(),
                        favoriteModelList.get(position).getItemDescription(),
                        favoriteModelList.get(position).getProviderPhone(),
                        favoriteModelList.get(position).getProviderAddress(),
                        favoriteModelList.get(position).getProviderWebsite(),
                        favoriteModelList.get(position).getProviderOpeningTime(),
                        favoriteModelList.get(position).getProviderMail(),
                        favoriteModelList.get(position).getProviderFacebook(),
                        favoriteModelList.get(position).getProviderLinkedIn(),
                        favoriteModelList.get(position).getProviderInstagram(),
                        favoriteModelList.get(position).getProviderTwitter(),
                        favoriteModelList.get(position).getProviderSnapchat(),
                        favoriteModelList.get(position).getProviderGooglePlus(),
                        favoriteModelList.get(position).getProviderLatlong(),
                        favoriteModelList.get(position).getUrl(),
                        favoriteModelList.get(position).getPageId());
            }
        }));


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
                    "fonts/GE_SS_Unique_Light.otf");
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
