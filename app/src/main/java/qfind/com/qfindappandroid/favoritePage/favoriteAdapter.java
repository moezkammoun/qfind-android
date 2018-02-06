package qfind.com.qfindappandroid.favoritePage;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import qfind.com.qfindappandroid.DataBaseHandler;
import qfind.com.qfindappandroid.R;
import qfind.com.qfindappandroid.searchResultsFragment.ResultsAdapter;
import qfind.com.qfindappandroid.searchResultsFragment.SearchedItem;

/**
 * Created by MoongedePC on 23-Jan-18.
 */

public class favoriteAdapter extends RecyclerView.Adapter<favoriteAdapter.MyViewHolder> {

    private Context mContext;
    private List<FavoriteModel> itemList;
    private Typeface mTypeFace;
    FavoriteModel favoriteModel;

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
        holder.title.setText(favoriteModel.getItem());
        holder.description.setText(favoriteModel.getItemDescription());
        // loading album cover using Glide library
        Picasso.with(mContext).load(favoriteModel.getUrl()).into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description;
        public ImageView thumbnail, favoriteStar;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.favorite_item_title);
            description = (TextView) view.findViewById(R.id.favorite_item_description);
            thumbnail = (ImageView) view.findViewById(R.id.favorite_thumbnail);
            favoriteStar = (ImageView) view.findViewById(R.id.favorite_star);
            favoriteStar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int i = getAdapterPosition();

                    String fav=favoriteModel.getItem();
                    int in= favoriteModel.getPageId();
                    System.out.println("fav "+ in);
                    delete(getAdapterPosition(),in);
                }
            });
            setFontTypeForText(title, description);
        }
    }

    public void setFontTypeForText(TextView title, TextView description) {
        if (mContext.getResources().getConfiguration().locale.getLanguage().equals("en")) {
            mTypeFace = Typeface.createFromAsset(mContext.getAssets(),
                    "fonts/Lato-Light.ttf");
        } else {
            mTypeFace = Typeface.createFromAsset(mContext.getAssets(),
                    "fonts/GE_SS_Unique_Light.otf");
        }
        title.setTypeface(mTypeFace);
        description.setTypeface(mTypeFace);
    }

    public favoriteAdapter(Context mContext, List<FavoriteModel> itemList) {
        this.mContext = mContext;
        this.itemList = itemList;
    }

    public void delete(int position,int id) {  //removes the row
        DataBaseHandler db = new DataBaseHandler(mContext);
        db.deleteFavorite(id);
        itemList.remove(position);
        notifyItemRemoved(position);
    }

}


