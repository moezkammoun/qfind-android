package qfind.com.qfindappandroid.categoryfragment;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
    //private List<Categories> subCategoriesList;
    private RecyclerViewClickListener mListener;
    Categories categories;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Nullable
        @BindView(R.id.category_item)
        TextView categoryName;
        @Nullable
        @BindView(R.id.category_thumbnail)
        ImageView categoryThumbnail;
        @Nullable
        @BindView(R.id.main_category_card_item)
        LinearLayout mainCategoryCardItemLayout;
        @Nullable
        @BindView(R.id.sub_category_card_item)
        LinearLayout subCategoryCardItemLayout;
        @Nullable
        @BindView(R.id.sub_category_item_name)
        TextView subCategoryName;
        @Nullable
        @BindView(R.id.sub_category_item_description)
        TextView subCategoryDescription;


        public MyViewHolder(View view,RecyclerViewClickListener listener) {
            super(view);
            ButterKnife.bind(this, view);
            mListener = listener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());
        }
    }


    public CategoryItemAdapter(Context mContext, List<Categories> categoriesList,RecyclerViewClickListener listener) {
        this.mContext = mContext;
        this.categoriesList = categoriesList;
        this.mListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_items_card, parent, false);

        return new MyViewHolder(itemView,mListener);
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
        if (CategoryPageCurrentStatus.categoryPageStatus==1){
            holder.subCategoryCardItemLayout.setVisibility(View.GONE);
            holder.mainCategoryCardItemLayout.setVisibility(View.VISIBLE);
            holder.categoryName.setTypeface(mtypeFace);
            categories = categoriesList.get(position);
            holder.categoryName.setText(categories.getItem());
            Picasso.with(mContext).load(categories.getThumbnail()).into(holder.categoryThumbnail);


        }else {
            holder.mainCategoryCardItemLayout.setVisibility(View.GONE);
            holder.subCategoryCardItemLayout.setVisibility(View.VISIBLE);
            holder.subCategoryName.setTypeface(mtypeFace);
            holder.subCategoryDescription.setTypeface(mtypeFace);
            categories = categoriesList.get(position);
            holder.subCategoryName.setText(categories.getSubCategoryName());
            holder.subCategoryDescription.setText(categories.getSubCategoryDescription());
            Picasso.with(mContext).load(categories.getThumbnail()).into(holder.categoryThumbnail);
        }
        // loading album cover using Picasso library
        Picasso.with(mContext).load(categories.getThumbnail()).into(holder.categoryThumbnail);


    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }


}
