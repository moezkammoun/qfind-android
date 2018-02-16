package qfind.com.qfindappandroid.historyPage;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import qfind.com.qfindappandroid.BaseActivity;
import qfind.com.qfindappandroid.R;

/**
 * Created by MoongedePC on 19-Jan-18.
 */

public class HistoryPageMainAdapter extends RecyclerView.Adapter<HistoryPageMainAdapter.ItemRowHolder> {

    private String day;
    private ArrayList<HistoryPageMainModel> dataList;
    private ArrayList<HistoryPageDataModel> singleItem;
    private Context mContext;
    Typeface mtypeFace;


    public HistoryPageMainAdapter(ArrayList<HistoryPageMainModel> dataList, Context mContext) {

        this.dataList = dataList;
        this.mContext = mContext;
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        @Nullable
        @BindView(R.id.history_row_view)
        RecyclerView recycler_view_list;
        @Nullable
        @BindView(R.id.history_row_text)
        TextView history_row_text;

        public ItemRowHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }

    @Override
    public HistoryPageMainAdapter.ItemRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_row_layout, null);
        ItemRowHolder itemRowHolder = new ItemRowHolder(v);
        setFontTypeForText();
        return itemRowHolder;
    }

    public void setFontTypeForText() {
        if (mContext.getResources().getConfiguration().locale.getLanguage().equals("en")) {
            mtypeFace = Typeface.createFromAsset(mContext.getApplicationContext().getAssets(),
                    "fonts/Lato-Bold.ttf");
        }else {
            mtypeFace = Typeface.createFromAsset(mContext.getApplicationContext().getAssets(),
                    "fonts/GE_SS_Unique_Bold.otf");
        }
    }

    @Override
    public void onBindViewHolder(HistoryPageMainAdapter.ItemRowHolder holder, int position) {

        singleItem = dataList.get(position).getHistoryPageDataModels();
        HistoryPageDataAdapter historyPageDataAdapter = new HistoryPageDataAdapter(singleItem, mContext);
        holder.history_row_text.setTypeface(mtypeFace);
        holder.history_row_text.setText(dataList.get(position).getDay());
        holder.recycler_view_list.setHasFixedSize(true);
        holder.recycler_view_list.setLayoutManager
                (new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        holder.recycler_view_list.setAdapter(historyPageDataAdapter);
        holder.recycler_view_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                dataList.get(get).getPageId();
            }
        });
        holder.recycler_view_list.addOnItemTouchListener(new RecyclerViewTouchListener(mContext, holder.recycler_view_list, new HistoryClickListener() {
            @Override
            public void onClick(View view, int position) {
                singleItem.get(position).getPageId();

                ((BaseActivity) mContext).showServiceProviderDetailPage(
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

    @Override
    public int getItemCount() {
        return dataList.size();
    }


}
