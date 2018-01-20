package qfind.com.qfindappandroid.searchResultsFragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import qfind.com.qfindappandroid.R;

public class SearchResultsFragment extends Fragment {

    RecyclerView mRecyclerView;
    TextView mTextViewEmpty, pageTitle;
    ProgressBar mProgressBarLoading;
    ResultsAdapter resultsAdapter;
    List<SearchedItem> searchedItemList;
    SearchedItem item;
    private Typeface mTypeFace;

    public SearchResultsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_results, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.search_results_list);
        mTextViewEmpty = (TextView) view.findViewById(R.id.textViewEmpty);
        mProgressBarLoading = (ProgressBar) view.findViewById(R.id.progressBarLoading);
        pageTitle = (TextView) view.findViewById(R.id.search_title);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        resultsAdapter = new ResultsAdapter(getContext(), getSearchDetails());
        mRecyclerView.setAdapter(resultsAdapter);

        return view;
    }

    public void setFontTypeForText() {
        if (getResources().getConfiguration().locale.getLanguage().equals("en")) {
            mTypeFace = Typeface.createFromAsset(getActivity().getAssets(),
                    "fonts/Lato-Bold.ttf");
        }else {
            mTypeFace = Typeface.createFromAsset(getActivity().getAssets(),
                    "fonts/GE_SS_Unique_Light.otf");
        }

        pageTitle.setTypeface(mTypeFace);
    }

    public List<SearchedItem> getSearchDetails() {
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
        searchedItemList = new ArrayList<>();
        for (int i = 0; i < categoryItems.length; i++) {
            item = new SearchedItem(categoryItems[i], categoryItemsDescription[i], thumbnails[i]);
            searchedItemList.add(item);
        }
        return searchedItemList;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setFontTypeForText();
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
