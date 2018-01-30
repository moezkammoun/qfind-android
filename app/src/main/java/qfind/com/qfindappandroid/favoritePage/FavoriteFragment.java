package qfind.com.qfindappandroid.favoritePage;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import qfind.com.qfindappandroid.R;
import qfind.com.qfindappandroid.categorycontaineractivity.ContainerActivity;
import qfind.com.qfindappandroid.historyPage.HistoryPageMainAdapter;
import qfind.com.qfindappandroid.searchResultsFragment.ResultsAdapter;
import qfind.com.qfindappandroid.searchResultsFragment.SearchedItem;

public class FavoriteFragment extends Fragment {

    @BindView(R.id.favorite_list)
    RecyclerView favoriteView;
    @BindView(R.id.favorite_title)
    TextView favoriteTitle;
    @BindView(R.id.progressBarLoading)
    ProgressBar mProgressBarLoading;
    favoriteAdapter resultsAdapter;
    List<FavoriteModel> favoriteModelList;
//    @BindView(R.id.favorite_star)
//     ImageView favoriteStar;
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
        int[] thumbnails = new int[]{
                R.drawable.dentist,
                R.drawable.dentist,
                R.drawable.dentist,
                R.drawable.dentist,
                R.drawable.dentist,
                R.drawable.dentist,
                R.drawable.dentist,
                R.drawable.dentist,
                R.drawable.dentist,
                R.drawable.dentist,
                R.drawable.dentist,
                R.drawable.dentist,
                R.drawable.dentist,
                R.drawable.dentist,
                R.drawable.dentist
        };
        String[] categoryItems = new String[]{
                "Four Season Hotel",
                "Four Season Hotel",
                "Four Season Hotel",
                "Four Season Hotel",
                "Four Season Hotel",
                "Four Season Hotel",
                "Four Season Hotel",
                "Four Season Hotel",
                "Four Season Hotel",
                "Four Season Hotel",
                "Four Season Hotel",
                "Four Season Hotel",
                "Four Season Hotel",
                "Four Season Hotel",
                "Four Season Hotel"

        };
        String[] categoryItemsDescription = new String[]{
                "Lorem ipsum dolor sit amet,",
                "Lorem ipsum dolor sit amet,",
                "Lorem ipsum dolor sit amet,",
                "Lorem ipsum dolor sit amet,",
                "Lorem ipsum dolor sit amet,",
                "Lorem ipsum dolor sit amet,",
                "Lorem ipsum dolor sit amet,",
                "Lorem ipsum dolor sit amet,",
                "Lorem ipsum dolor sit amet,",
                "Lorem ipsum dolor sit amet,",
                "Lorem ipsum dolor sit amet,",
                "Lorem ipsum dolor sit amet,",
                "Lorem ipsum dolor sit amet,",
                "Lorem ipsum dolor sit amet,",
                "Lorem ipsum dolor sit amet,"

        };

        favoriteModelList = new ArrayList<>();
        for (int i = 0; i < categoryItems.length; i++) {
            item = new FavoriteModel(categoryItems[i], categoryItemsDescription[i], thumbnails[i]);
            favoriteModelList.add(item);
        }

        setFontTypeForText();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });


        favoriteAdapter adapter = new favoriteAdapter(getContext(),favoriteModelList);
        favoriteView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        favoriteView.setAdapter(adapter);
        ((ContainerActivity)getActivity()).setupBottomNavigationBar();
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
