package qfind.com.qfindappandroid.historyPage;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import qfind.com.qfindappandroid.R;
import qfind.com.qfindappandroid.searchResultsFragment.SearchedItem;


public class HistoryFragment extends Fragment {

    @BindView(R.id.history_view)
    RecyclerView recyclerView;
    Typeface mtypeFace;
    @BindView(R.id.history_title)
    TextView history;
    @BindView(R.id.back_button)
    ImageView backButton;


    public HistoryFragment() {
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
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setFontTypeForText();
        ArrayList<HistoryPageMainModel> arrayListMain= new ArrayList<HistoryPageMainModel>();
        ArrayList<String> days = new ArrayList<String>();
        days.add("Today");
        days.add("Yesterday");
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

        for (int i = 0; i < 2; i++) {
            HistoryPageMainModel mainModel = new HistoryPageMainModel();
            mainModel.setDay(days.get(i));
            ArrayList<HistoryPageDataModel> singleItem = new ArrayList<HistoryPageDataModel>();
            for (int j = 0; j <categoryItems.length; j++) {
                singleItem.add(new HistoryPageDataModel(categoryItems[j],thumbnails[j],categoryItemsDescription[j]));
            }
            mainModel.setHistoryPageDataModels(singleItem);
            arrayListMain.add(mainModel);
        }

        recyclerView.setHasFixedSize(true);
        HistoryPageMainAdapter adapter = new HistoryPageMainAdapter(arrayListMain,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }

    public void setFontTypeForText() {
        if (getResources().getConfiguration().locale.getLanguage().equals("en")) {
            mtypeFace = Typeface.createFromAsset(getActivity().getAssets(),
                    "fonts/Lato-Bold.ttf");
        }else {
            mtypeFace = Typeface.createFromAsset(getActivity().getAssets(),
                    "fonts/GE_SS_Unique_Light.otf");
        }

        history.setTypeface(mtypeFace);
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
