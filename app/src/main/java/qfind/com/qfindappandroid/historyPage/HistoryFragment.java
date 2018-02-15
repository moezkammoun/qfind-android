package qfind.com.qfindappandroid.historyPage;

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
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import qfind.com.qfindappandroid.BaseActivity;
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
    List<HistoryItem> list = new ArrayList<HistoryItem>();
    ArrayList<Integer> positions = new ArrayList<Integer>();
    ArrayList<HistoryPageDataModel> singleItem = new ArrayList<HistoryPageDataModel>();


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
        Calendar cal;
        cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        System.out.println("check date from" + sdf.format(cal.getTime()));
        String filterDate = sdf.format(cal.getTime());
        String currentDate = sdf.format(new Date());
        db.deleteHistory(filterDate);
        List<HistoryDateCount> countList = new ArrayList<HistoryDateCount>();
        countList = db.getDateCount();
        for (HistoryDateCount count : countList) {
            String log = "Id: " + count.getDay() + " ,count: " + count.getCount();
            Log.d("Log: ", log);
        }
        int today = 0, yesterday = 0, day = 0;
        String strDate = null;
        String todayDate = null;
        String dayVar = null;
        String yesDay = null;
        for (int i = 0; i < countList.size(); i++) {
            HistoryPageMainModel mainModel = new HistoryPageMainModel();
//            ArrayList<HistoryPageDataModel> singleItem = new ArrayList<HistoryPageDataModel>();
            try {
                Calendar calendar;
                calendar = Calendar.getInstance();
                calendar.add(Calendar.DATE, -1);
                yesDay = sdf.format(calendar.getTime());
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
            } else if (yesDay.compareTo(strDate) == 0) {
                if (yesterday < 1) {
                    dayVar = "Yesterday";
                    yesterday = yesterday + 1;
                }

            } else if (todayDate.compareTo(strDate) < 0) {
                continue;
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

            list = db.getAllHistory(countList.get(i).getDay());
            System.out.println(list);
            for (int j = 0; j < list.size(); j++) {
                HistoryPageDataModel model = new HistoryPageDataModel();
                model.setPageName(list.get(j).getTitke());
                model.setUrl(list.get(j).getImage());
                model.setDescription(list.get(j).getDescription());
                model.setProviderPhone(list.get(j).getProviderPhone());
                model.setProviderAddress(list.get(j).getProviderAddress());
                model.setProviderWebsite(list.get(j).getProviderWebsite());
                model.setProviderOpeningTime(list.get(j).getProviderOpeningTime());
                model.setProviderMail(list.get(j).getProviderMail());
                model.setProviderFacebook(list.get(j).getProviderFacebook());
                model.setProviderLinkedIn(list.get(j).getProviderLinkedIn());
                model.setProviderInstagram(list.get(j).getProviderInstagram());
                model.setProviderTwitter(list.get(j).getProviderTwitter());
                model.setProviderSnapchat(list.get(j).getProviderSnapchat());
                model.setProviderGooglePlus(list.get(j).getProviderGooglePlus());
                model.setProviderLatlong(list.get(j).getProviderLatlong());
                model.setPageId(list.get(j).getPageId());
                singleItem.add(model);
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
        ((ContainerActivity) getActivity()).setupBottomNavigationBar();
    }


    public void setAdapter(final ArrayList<HistoryPageMainModel> arrayListMain) {

        recyclerView.setHasFixedSize(true);
        HistoryPageMainAdapter adapter = new HistoryPageMainAdapter(arrayListMain, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getContext(), recyclerView, new HistoryClickListener() {
            @Override
            public void onClick(View view, int position) {
                arrayListMain.get(position).getHistoryPageDataModels().get(position).getPageId();

                ((BaseActivity) getActivity()).showServiceProviderDetailPage(
                        singleItem.get(position).getPageName(),
                        singleItem.get(position).getDescription(),
                        singleItem.get(position).getProviderPhone(),
                        singleItem.get(position).getProviderAddress(),
                        singleItem.get(position).getProviderWebsite(),
                        singleItem.get(position).getProviderOpeningTime(),
                        singleItem.get(position).getProviderMail(),
                        singleItem.get(position).getProviderFacebook(),
                        singleItem.get(position).getProviderLinkedIn(),
                        singleItem.get(position).getProviderInstagram(),
                        singleItem.get(position).getProviderTwitter(),
                        singleItem.get(position).getProviderSnapchat(),
                        singleItem.get(position).getProviderGooglePlus(),
                        singleItem.get(position).getProviderLatlong(),
                        singleItem.get(position).getUrl(),
                        singleItem.get(position).getPageId());
            }
        }));
    }

    public void setFontTypeForText() {
        if (getResources().getConfiguration().locale.getLanguage().equals("en")) {
            mtypeFace = Typeface.createFromAsset(getActivity().getAssets(),
                    "fonts/Lato-Bold.ttf");
        } else {
            mtypeFace = Typeface.createFromAsset(getActivity().getAssets(),
                    "fonts/GE_SS_Unique_Bold.otf");
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
