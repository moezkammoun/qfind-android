package qfind.com.qfindappandroid.historyPage;

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
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import qfind.com.qfindappandroid.DataBaseHandler;
import qfind.com.qfindappandroid.R;
import qfind.com.qfindappandroid.categorycontaineractivity.ContainerActivity;


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
        ArrayList<HistoryPageMainModel> arrayListMain = new ArrayList<HistoryPageMainModel>();
        DataBaseHandler db = new DataBaseHandler(getContext());
//        db.deleteHistory();
        List<HistoryDateCount> countList = new ArrayList<HistoryDateCount>();
        countList = db.getDateCount();
        int today = 0, yesterday = 0, day = 0;
        String strDate = null;
        String todayDate = null;
        String dayVar = null;
        for (int i = 0; i < countList.size(); i++) {
            HistoryPageMainModel mainModel = new HistoryPageMainModel();
            ArrayList<HistoryPageDataModel> singleItem = new ArrayList<HistoryPageDataModel>();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            try {
                todayDate = sdf.format(new Date());
                strDate = sdf.format(sdf.parse(countList.get(i).getDay()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (todayDate.compareTo(strDate) == 0) {
                if (today < 1) {
                    dayVar = "Today";
                    today = today + 1;
                }
            } else if (todayDate.compareTo(strDate) > 0) {
                if (yesterday < 1) {
                    dayVar = "Yesterday";
                    yesterday = yesterday + 1;
                }

            } else {
                Date ds = null;
                DateFormat dateFormat = new SimpleDateFormat("d MMM yyyy");
                try {
                    ds = sdf.parse(countList.get(i).getDay());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String desDate = dateFormat.format(ds);
                if (day < 1) {
                    dayVar = desDate;
                    day = day + 1;
                } else if (!(dayVar.equals(countList.get(i).getDay()))) {
                    dayVar = desDate;
                    day = day + 1;
                }
            }

            mainModel.setDay(dayVar);
            List<HistoryItem> list = new ArrayList<HistoryItem>();
            list = db.getAllHistory(countList.get(i).getDay());
            System.out.println(list);
            for (int j = 0; j < list.size(); j++) {
                singleItem.add(new HistoryPageDataModel(list.get(j).getTitke(),
                        R.drawable.dentist, list.get(j).getDescription()));
            }
            mainModel.setHistoryPageDataModels(singleItem);
            arrayListMain.add(mainModel);
        }
        setAdapter(arrayListMain);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        ((ContainerActivity)getActivity()).setupBottomNavigationBar();
    }

    public void setAdapter(ArrayList<HistoryPageMainModel> arrayListMain) {

        recyclerView.setHasFixedSize(true);
        HistoryPageMainAdapter adapter = new HistoryPageMainAdapter(arrayListMain, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    public void setFontTypeForText() {
        if (getResources().getConfiguration().locale.getLanguage().equals("en")) {
            mtypeFace = Typeface.createFromAsset(getActivity().getAssets(),
                    "fonts/Lato-Bold.ttf");
        } else {
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
