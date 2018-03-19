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
    ArrayList<HistoryPageDataModel> singleItem;
    @BindView(R.id.empty_text_view_info)
    TextView emptyTextView;


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
        String filterDate = sdf.format(cal.getTime());
        String currentDate = sdf.format(new Date());
        db.deleteHistory(filterDate);
        db.deleteHistoryAfter(currentDate);
        List<HistoryDateCount> countList = new ArrayList<HistoryDateCount>();
        countList = db.getDateCount();

        if (countList.size() != 0) {
            emptyTextView.setVisibility(View.GONE);
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
                singleItem = new ArrayList<HistoryPageDataModel>();
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
                        dayVar = getResources().getString(R.string.today);
                        today = today + 1;
                    }
                } else if (yesDay.compareTo(strDate) == 0) {
                    if (yesterday < 1) {
                        dayVar = getResources().getString(R.string.yesterday);
                        yesterday = yesterday + 1;
                    }

                } else if (todayDate.compareTo(strDate) < 0) {
                    continue;
                } else {
                    Date ds = null;
                    DateFormat dateFormat = null;
                    dateFormat = new SimpleDateFormat("d MMM yyyy");
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

                    String[] parts = dayVar.split(" ");

                    if (getResources().getConfiguration().locale.getLanguage().equals("ar")) {
                        String month;
                        switch (parts[1]) {
                            case "Jan":
                                month = getResources().getString(R.string.January);
                                dayVar = dayVar.replace("Jan", month);
                                break;
                            case "Feb":
                                month = getResources().getString(R.string.February);
                                dayVar = dayVar.replace("Feb", month);
                                break;
                            case "Mar":
                                month = getResources().getString(R.string.March);
                                dayVar = dayVar.replace("Mar", month);
                                break;
                            case "Apr":
                                month = getResources().getString(R.string.April);
                                dayVar = dayVar.replace("Apr", month);
                                break;
                            case "May":
                                month = getResources().getString(R.string.May);
                                dayVar = dayVar.replace("May", month);
                                break;
                            case "Jun":
                                month = getResources().getString(R.string.June);
                                dayVar = dayVar.replace("Jun", month);
                                break;
                            case "Jul":
                                month = getResources().getString(R.string.July);
                                dayVar = dayVar.replace("Jul", month);
                                break;
                            case "Aug":
                                month = getResources().getString(R.string.August);
                                dayVar = dayVar.replace("Aug", month);
                                break;
                            case "Sep":
                                month = getResources().getString(R.string.September);
                                dayVar = dayVar.replace("Sep", month);
                                break;
                            case "Oct":
                                month = getResources().getString(R.string.October);
                                dayVar = dayVar.replace("Oct", month);
                                break;
                            case "Nov":
                                month = getResources().getString(R.string.November);
                                dayVar = dayVar.replace("Nov", month);
                                break;
                            case "Dec":
                                month = getResources().getString(R.string.December);
                                dayVar = dayVar.replace("Dec", month);
                                break;
                            default:
                                break;

                        }
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
                    model.setPageNameArabic(list.get(j).getTitleArabic());
                    model.setDescriptionArabic(list.get(j).getDescriptionArabic());
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
                    model.setProviderOpeningTimeArabic(list.get(j).getProviderOpeningTimeArabic());
                    model.setProviderAddressArabic(list.get(j).getProviderAddressArabic());
                    model.setProviderClosingTime(list.get(j).getProviderClosingTime());
                    model.setProviderClosingTimeArabic(list.get(j).getProviderClosingTimeArabic());
                    model.setProviderOpeningTitle(list.get(j).getProviderOpeningTitle());
                    model.setProviderClosingTitle(list.get(j).getProviderClosingTitle());
                    model.setProviderOpeningTitleArabic(list.get(j).getProviderOpeningTitleArabic());
                    model.setProviderClosingTitleArabic(list.get(j).getProviderClosingTitleArabic());
                    singleItem.add(model);
                }
                mainModel.setHistoryPageDataModels(singleItem);
                arrayListMain.add(mainModel);
            }
            setAdapter(arrayListMain);

        } else {
            emptyTextView.setVisibility(View.VISIBLE);
            emptyTextView.setText(R.string.no_history_yet);
        }

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
        adapter.notifyItemInserted(0);
        recyclerView.smoothScrollToPosition(0);
        //up here
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
