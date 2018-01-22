package qfind.com.qfindappandroid.historyPage;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import qfind.com.qfindappandroid.R;


public class HistoryFragment extends Fragment {

    @BindView(R.id.history_view)
    RecyclerView recyclerView;


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

        ArrayList<HistoryPageMainModel> arrayListMain= new ArrayList<HistoryPageMainModel>();
        ArrayList<String> days = new ArrayList<String>();
        days.add("Today");
        days.add("Yesterday");

        for (int i = 0; i < 2; i++) {
            HistoryPageMainModel mainModel = new HistoryPageMainModel();
            mainModel.setDay(days.get(i));
            ArrayList<HistoryPageDataModel> singleItem = new ArrayList<HistoryPageDataModel>();
//            for (int j = 0; j <2; j++) {
                singleItem.add(new HistoryPageDataModel("car service", R.drawable.car_service));
                singleItem.add(new HistoryPageDataModel("cloth_stores", R.drawable.cloth_stores));
                singleItem.add(new HistoryPageDataModel("dentist", R.drawable.dentist));
                singleItem.add(new HistoryPageDataModel("exterior_designers", R.drawable.exterior_designers));
//            }
            mainModel.setHistoryPageDataModels(singleItem);
            arrayListMain.add(mainModel);
        }


        recyclerView.setHasFixedSize(true);
        HistoryPageMainAdapter adapter = new HistoryPageMainAdapter(arrayListMain,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
