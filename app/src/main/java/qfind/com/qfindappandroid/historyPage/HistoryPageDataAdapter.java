package qfind.com.qfindappandroid.historyPage;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import qfind.com.qfindappandroid.R;

/**
 * Created by MoongedePC on 19-Jan-18.
 */

public class HistoryPageDataAdapter extends RecyclerView.Adapter<HistoryPageDataAdapter.SingleItemRowHolder>{

    private ArrayList<HistoryPageDataModel> itemsList;
    private Context mContext;

    public HistoryPageDataAdapter(ArrayList<HistoryPageDataModel> itemsList, Context mContext) {
        this.itemsList = itemsList;
        this.mContext = mContext;
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        @Nullable
        @BindView(R.id.history_item)
        protected TextView pageName;
        @Nullable
        @BindView(R.id.history_thumbnail)
        protected ImageView url;


        public SingleItemRowHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }

    @Override
    public HistoryPageDataAdapter.SingleItemRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_card_layout, null);
        View viewHolder= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_card_layout, null, false);
        viewHolder.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
//        return new ViewOffersHolder(viewHolder);
        SingleItemRowHolder mh = new SingleItemRowHolder(viewHolder);
        return mh;
    }

    @Override
    public void onBindViewHolder(HistoryPageDataAdapter.SingleItemRowHolder holder, int position) {

        HistoryPageDataModel historyPageDataModel= itemsList.get(position);
        holder.pageName.setText(historyPageDataModel.getPageName());
        holder.url.setImageResource(historyPageDataModel.getImages());


    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }
}
