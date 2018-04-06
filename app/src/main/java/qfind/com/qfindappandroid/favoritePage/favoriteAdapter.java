package qfind.com.qfindappandroid.favoritePage;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import qfind.com.qfindappandroid.BaseActivity;
import qfind.com.qfindappandroid.DataBaseHandler;
import qfind.com.qfindappandroid.R;

/**
 * Created by MoongedePC on 23-Jan-18.
 */

public class favoriteAdapter extends RecyclerView.Adapter<favoriteAdapter.MyViewHolder> {

    private Context mContext;
    private List<FavoriteModel> itemList;
    private Typeface mTypeFaceLight;
    FavoriteModel favoriteModel;
    ArrayList<Integer> positions = new ArrayList<Integer>();
    private FavoriteClickListener mListener;

    public favoriteAdapter() {
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_card_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        favoriteModel = itemList.get(position);
        if (mContext.getResources().getConfiguration().locale.getLanguage().equals("en")) {
            holder.title.setText(favoriteModel.getItem());
            holder.description.setText(favoriteModel.getItemDescription());
        } else {
            holder.title.setText(favoriteModel.getItemArabic());
            holder.description.setText(favoriteModel.getItemDescriptionArabic());
        }

        positions.add(position, favoriteModel.getPageId());
        // loading album cover using Glide library
        Picasso.with(mContext).load(favoriteModel.getUrl()).placeholder(R.drawable.placeholder).fit().into(holder.thumbnail);

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title, description;
        public ImageView thumbnail, favoriteStar;
        public LinearLayout layout;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.favorite_item_title);
            description = (TextView) view.findViewById(R.id.favorite_item_description);
            thumbnail = (ImageView) view.findViewById(R.id.favorite_thumbnail);
            favoriteStar = (ImageView) view.findViewById(R.id.favorite_star);
            layout = (LinearLayout) view.findViewById(R.id.fav_layout);
            favoriteStar.setOnClickListener(this);

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("position " + getAdapterPosition());
                    ((BaseActivity) mContext).showServiceProviderDetailPageInner(
                            itemList.get(getAdapterPosition()).getItem(),
                            itemList.get(getAdapterPosition()).getItemArabic(),
                            itemList.get(getAdapterPosition()).getItemDescription(),
                            itemList.get(getAdapterPosition()).getItemDescriptionArabic(),
                            itemList.get(getAdapterPosition()).getUrl(),
                            itemList.get(getAdapterPosition()).getPageId()
                    );
                }
            });
            setFontTypeForText(title, description);
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition(), positions);
        }
    }

    public void setFontTypeForText(TextView title, TextView description) {
        if (mContext.getResources().getConfiguration().locale.getLanguage().equals("en")) {
            mTypeFaceLight = Typeface.createFromAsset(mContext.getAssets(),
                    "fonts/Lato-Light.ttf");
        } else {
            mTypeFaceLight = Typeface.createFromAsset(mContext.getAssets(),
                    "fonts/GE_SS_Unique_Light.otf");
        }
        title.setTypeface(mTypeFaceLight);
        description.setTypeface(mTypeFaceLight);
    }

    public favoriteAdapter(Context mContext, List<FavoriteModel> itemList, FavoriteClickListener mListener) {
        this.mContext = mContext;
        this.itemList = itemList;
        this.mListener = mListener;
    }

    public void delete(int position, int id) {  //removes the row
        DataBaseHandler db = new DataBaseHandler(mContext);
        db.deleteFavorite(id);
        itemList.remove(position);
        notifyItemRemoved(position);
    }

}


