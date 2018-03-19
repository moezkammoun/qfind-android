package qfind.com.qfindappandroid.favoritePage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
    favoriteAdapter adapter;
    List<FavoriteModel> favoriteModelList;
    FavoriteModel item;
    Typeface mTypeFace;
    @BindView(R.id.back_button)
    ImageView backButton;
    @BindView(R.id.empty_text_view_info)
    TextView emptyTextView;
    FavoriteClickListener favoriteClickListener;
    DataBaseHandler db;

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
        setupRecyclerViewClickListener();
        favoriteModelList = new ArrayList<>();
        db = new DataBaseHandler(getContext());
        Log.d("Reading: ", "Reading all item..");
        List<FavoriteModel> fav = db.getAllFavorites();
        if (fav.size() != 0) {
            emptyTextView.setVisibility(View.GONE);
            for (FavoriteModel cn : fav) {
                String log = "Id: " + cn.getId() + " ,item: " + cn.getItem() + " ,des: " + cn.getItemDescription()
                        + " ,pid: " + cn.getPageId();
                // Writing Contacts to log
                Log.d("item: ", log);
                item = new FavoriteModel(cn.getItem(), cn.getItemDescription(), cn.getItemArabic(), cn.getItemDescriptionArabic(),
                        cn.getUrl(), cn.getPageId(), cn.getProviderPhone(), cn.getProviderWebsite(), cn.getProviderAddress(), cn.getProviderOpeningTime(),
                        cn.getProviderMail(), cn.getProviderFacebook(), cn.getProviderLinkedIn(),
                        cn.getProviderInstagram(), cn.getProviderTwitter(), cn.getProviderSnapchat(), cn.getProviderGooglePlus(),
                        cn.getProviderLatlong(), cn.getDatetime(), cn.getProviderOpeningTimeArabic(), cn.getProviderAddressArabic(),
                        cn.getProviderClosingTime(), cn.getProviderClosingTimeArabic(), cn.getProviderOpeningTitle(),
                        cn.getProviderClosingTitle(), cn.getProviderOpeningTitleArabic(), cn.getProviderClosingTitleArabic()
                );
                favoriteModelList.add(item);

            }
            adapter = new favoriteAdapter(getContext(), favoriteModelList, favoriteClickListener);
            favoriteView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            favoriteView.setAdapter(adapter);
            adapter.notifyItemInserted(0);
            favoriteView.smoothScrollToPosition(0);
        } else {
            emptyTextView.setVisibility(View.VISIBLE);
            emptyTextView.setText(R.string.no_favorites_yet);

        }
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        ((ContainerActivity) getActivity()).setupBottomNavigationBar();
    }

    public void setupRecyclerViewClickListener() {
        favoriteClickListener = new FavoriteClickListener() {
            @Override
            public void onClick(View view, final int position, final ArrayList<Integer> arrayList) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());

                // Setting Dialog Title
                alertDialog.setTitle(R.string.favorite_alert_title);

                // Setting Dialog Message
                alertDialog.setMessage(R.string.favorite_alert_message);

                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton(R.string.favorite_alert_title, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        delete(position, arrayList.get(position), arrayList);
                        List<FavoriteModel> isCheck = db.getAllFavorites();
                        if (isCheck.size() == 0) {
                            emptyTextView.setVisibility(View.VISIBLE);
                            emptyTextView.setText(R.string.no_favorites_yet);
                        } else {
                            emptyTextView.setVisibility(View.GONE);
                        }
                    }
                });

                // Setting Negative "NO" Button
                alertDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                // Showing Alert Message
                alertDialog.show();

            }
        };
    }

    public void delete(int position, int id, ArrayList<Integer> arrayList) {  //removes the row
        DataBaseHandler db = new DataBaseHandler(getContext());
        db.deleteFavorite(id);
        favoriteModelList.remove(position);
        adapter.notifyItemRemoved(position);
        arrayList.remove(position);
//        adapter.notifyDataSetChanged();
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

