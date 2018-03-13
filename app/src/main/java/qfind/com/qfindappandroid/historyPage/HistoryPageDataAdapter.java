package qfind.com.qfindappandroid.historyPage;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import qfind.com.qfindappandroid.BaseActivity;
import qfind.com.qfindappandroid.R;

/**
 * Created by MoongedePC on 19-Jan-18.
 */

public class HistoryPageDataAdapter extends RecyclerView.Adapter<HistoryPageDataAdapter.SingleItemRowHolder> {

    private ArrayList<HistoryPageDataModel> itemsList;
    private Context mContext;
    Typeface mtypeFaceLight;
    HistoryPageDataModel historyPageDataModel;


    public HistoryPageDataAdapter(ArrayList<HistoryPageDataModel> itemsList, Context mContext) {
        this.itemsList = itemsList;
        this.mContext = mContext;
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        @Nullable
        @BindView(R.id.history_item_title)
        TextView title;
        @Nullable
        @BindView(R.id.history_thumbnail)
        ImageView url;
        @Nullable
        @BindView(R.id.history_item_description)
        TextView description;
        @Nullable
        @BindView(R.id.history_layout)
        RelativeLayout historyLayout;



        public SingleItemRowHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            historyLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemsList.get(getAdapterPosition()).getPageId();

                    ((BaseActivity) mContext).showServiceProviderDetailPage(
                            itemsList.get(getAdapterPosition()).getPageName(),
                            itemsList.get(getAdapterPosition()).getDescription(),
                            itemsList.get(getAdapterPosition()).getPageNameArabic(),
                            itemsList.get(getAdapterPosition()).getDescriptionArabic(),
                            itemsList.get(getAdapterPosition()).getProviderPhone(),
                            itemsList.get(getAdapterPosition()).getProviderAddress(),
                            itemsList.get(getAdapterPosition()).getProviderWebsite(),
                            itemsList.get(getAdapterPosition()).getProviderOpeningTime(),
                            itemsList.get(getAdapterPosition()).getProviderMail(),
                            itemsList.get(getAdapterPosition()).getProviderFacebook(),
                            itemsList.get(getAdapterPosition()).getProviderLinkedIn(),
                            itemsList.get(getAdapterPosition()).getProviderInstagram(),
                            itemsList.get(getAdapterPosition()).getProviderTwitter(),
                            itemsList.get(getAdapterPosition()).getProviderSnapchat(),
                            itemsList.get(getAdapterPosition()).getProviderGooglePlus(),
                            itemsList.get(getAdapterPosition()).getProviderLatlong(),
                            itemsList.get(getAdapterPosition()).getUrl(),
                            itemsList.get(getAdapterPosition()).getPageId(),
                            itemsList.get(getAdapterPosition()).getProviderOpeningTimeArabic(),
                            itemsList.get(getAdapterPosition()).getProviderAddressArabic(),
                            itemsList.get(getAdapterPosition()).getProviderClosingTime(),
                            itemsList.get(getAdapterPosition()).getProviderClosingTimeArabic(),
                            itemsList.get(getAdapterPosition()).getProviderOpeningTitle(),
                            itemsList.get(getAdapterPosition()).getProviderClosingTitle(),
                            itemsList.get(getAdapterPosition()).getProviderOpeningTitleArabic(),
                            itemsList.get(getAdapterPosition()).getProviderClosingTitleArabic()
                            );

                }
            });
            setFontTypeForText(title, description);
        }

    }

    @Override
    public HistoryPageDataAdapter.SingleItemRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewHolder = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_card_layout, null, false);
        viewHolder.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        SingleItemRowHolder mh = new SingleItemRowHolder(viewHolder);
        return mh;
    }

    @Override
    public void onBindViewHolder(HistoryPageDataAdapter.SingleItemRowHolder holder, final int position) {

        historyPageDataModel = itemsList.get(position);
        if (mContext.getResources().getConfiguration().locale.getLanguage().equals("en")) {
            holder.title.setText(historyPageDataModel.getPageName());
            holder.description.setText(historyPageDataModel.getDescription());
        }else{
            holder.title.setText(historyPageDataModel.getPageNameArabic());
            holder.description.setText(historyPageDataModel.getDescriptionArabic());
        }

        Picasso.with(mContext).load(historyPageDataModel.getUrl()).placeholder(R.drawable.placeholder)
                .fit().into(holder.url);
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public void setFontTypeForText(TextView title, TextView description) {
        if (mContext.getResources().getConfiguration().locale.getLanguage().equals("en")) {
            mtypeFaceLight = Typeface.createFromAsset(mContext.getAssets(),
                    "fonts/Lato-Light.ttf");
        } else {
            mtypeFaceLight = Typeface.createFromAsset(mContext.getAssets(),
                    "fonts/GE_SS_Unique_Light.otf");
        }

        title.setTypeface(mtypeFaceLight);
        description.setTypeface(mtypeFaceLight);
    }
}
