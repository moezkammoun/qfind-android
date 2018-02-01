package qfind.com.qfindappandroid.informationFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import qfind.com.qfindappandroid.DataBaseHandler;
import qfind.com.qfindappandroid.R;
import qfind.com.qfindappandroid.SimpleDividerItemDecoration;
import qfind.com.qfindappandroid.favoritePage.FavoriteModel;
import qfind.com.qfindappandroid.historyPage.HistoryItem;
import qfind.com.qfindappandroid.historyPage.HistoryPageMainModel;


public class InformationFragment extends Fragment {
    ArrayList<InformationFragmentModel> informationData;
    RecyclerView recyclerView;

    public InformationFragment() {
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
        View view = inflater.inflate(R.layout.fragment_information, container, false);

        HistoryPageMainModel mainModel = new HistoryPageMainModel();
        Bundle bundle = getArguments();
        DataBaseHandler db = new DataBaseHandler(getContext());
        HistoryItem dataModel = new HistoryItem();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        dataModel.setDay(sdf.format(new Date()));
        dataModel.setTitke(bundle.getString("title"));
//        dataModel.setImages(getIntent().getIntExtra("thumbnail"));
        dataModel.setDescription(bundle.getString("description"));
        db.addHistory(dataModel);

        FavoriteModel favoriteModel = new FavoriteModel();
        favoriteModel.setItem(bundle.getString("title"));
        favoriteModel.setItemDescription(bundle.getString("description"));
        db.addFavorite(favoriteModel);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.ItemDecoration dividerItemDecoration = new SimpleDividerItemDecoration(getContext());
        recyclerView.addItemDecoration(dividerItemDecoration);
        InformationFragmentAdapter adapter = new InformationFragmentAdapter(getContext(), getInformationData());
        recyclerView.setAdapter(adapter);
        return view;
    }

    public ArrayList<InformationFragmentModel> getInformationData() {
        informationData = new ArrayList<>();

        informationData.add(new InformationFragmentModel(R.drawable.phone_icon,
                R.drawable.dot_icon, "00974 5551 5566", R.drawable.right_arrow));
        informationData.add(new InformationFragmentModel(R.drawable.web_icon,
                R.drawable.dot_icon, "www.4season.com", R.drawable.right_arrow));
        informationData.add(new InformationFragmentModel(R.drawable.location_icon,
                R.drawable.dot_icon, "Doha", R.drawable.right_arrow));
        informationData.add(new InformationFragmentModel(R.drawable.clock_icon,
                R.drawable.dot_icon, "9:30 am-6:30 pm", R.drawable.right_arrow));
        informationData.add(new InformationFragmentModel(R.drawable.mail_icon,
                R.drawable.dot_icon, "mo@hotmail.com", R.drawable.right_arrow));
        informationData.add(new InformationFragmentModel(R.drawable.facebook_icon,
                R.drawable.dot_icon, "4season", R.drawable.right_arrow));
        return informationData;
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
