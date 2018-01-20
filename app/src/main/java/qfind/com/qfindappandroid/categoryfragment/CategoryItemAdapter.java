package qfind.com.qfindappandroid.categoryfragment;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import qfind.com.qfindappandroid.R;

/**
 * Created by dilee on 07-01-2018.
 */

public class CategoryItemAdapter extends RecyclerView.Adapter<CategoryItemAdapter.MyViewHolder> {

    private Context mContext;
    private List<Categories> categoriesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        @BindView(R.id.category_item)
        TextView categoryName;
        @Nullable
        @BindView(R.id.category_thumbnail)
        ImageView categoryThumbnail;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public CategoryItemAdapter(Context mContext, List<Categories> categoriesList) {
        this.mContext = mContext;
        this.categoriesList = categoriesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_items_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        Typeface mtypeFace = null ;
        if (mContext.getResources().getConfiguration().locale.getLanguage().equals("en")) {
            mtypeFace = Typeface.createFromAsset(mContext.getAssets(),
                    "fonts/Lato-Light.ttf");
        }else {
            mtypeFace = Typeface.createFromAsset(mContext.getAssets(),
                    "fonts/GE_SS_Unique_Light.otf");
        }
        holder.categoryName.setTypeface(mtypeFace);

        Categories categories = categoriesList.get(position);
        holder.categoryName.setText(categories.getItem());
        // loading album cover using Picasso library
        Picasso.with(mContext).load(categories.getThumbnail()).into(holder.categoryThumbnail);


    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }


}
