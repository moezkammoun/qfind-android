package qfind.com.qfindappandroid.categoryfragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import qfind.com.qfindappandroid.R;
import qfind.com.qfindappandroid.Util;
import qfind.com.qfindappandroid.categorycontaineractivity.MainCategoryItemList;

/**
 * Created by dilee on 07-01-2018.
 */

public class CategoryItemAdapter extends RecyclerView.Adapter<CategoryItemAdapter.MyViewHolder> {

    private Context mContext;
    ArrayList<MainCategoryItemList> mainCategoryItemList;
    private RecyclerViewClickListener mListener;
    ArrayList<SubCategoryItemList> subCategoryItemList;
    ArrayList<ServiceProviderListDetails> serviceProviderListDetails;
    byte categoryPageNumber;
    Picasso picasso;
    Typeface mtypeFaceLight;


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


        public MyViewHolder(View view, RecyclerViewClickListener listener) {
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


    public CategoryItemAdapter(Context mContext, ArrayList<MainCategoryItemList> mainCategoryItemList,
                               RecyclerViewClickListener listener) {
        this.mContext = mContext;
        setUpPicassoBuilderToReduceLoadingTime();
        this.mainCategoryItemList = mainCategoryItemList;
        this.mListener = listener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_items_card, parent, false);

        return new MyViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        setFontTypeForText();
        if (Util.categoryPageStatus == 1) {
            holder.subCategoryCardItemLayout.setVisibility(View.GONE);
            holder.mainCategoryCardItemLayout.setVisibility(View.VISIBLE);
            holder.categoryName.setTypeface(mtypeFaceLight);
            if (mainCategoryItemList != null) {
                holder.categoryName.setText(mainCategoryItemList.get(position).getCategoryName());
                picasso.load(mainCategoryItemList.get(position).getCategoryImage()).placeholder(R.drawable.placeholder).fit().into(holder.categoryThumbnail);
            }
        } else if (Util.categoryPageStatus == 2) {
            holder.mainCategoryCardItemLayout.setVisibility(View.VISIBLE);
            holder.subCategoryCardItemLayout.setVisibility(View.GONE);
            holder.subCategoryName.setTypeface(mtypeFaceLight);
            holder.subCategoryDescription.setTypeface(mtypeFaceLight);
            if (subCategoryItemList != null) {
                holder.categoryName.setText(subCategoryItemList.get(position).getSubCategoryName());
                picasso.load(subCategoryItemList.get(position).getSubCategoryImage()).placeholder(R.drawable.placeholder).fit().into(holder.categoryThumbnail);
            }
        } else if (Util.categoryPageStatus == 3) {
            holder.mainCategoryCardItemLayout.setVisibility(View.GONE);
            holder.subCategoryCardItemLayout.setVisibility(View.VISIBLE);
            holder.subCategoryName.setTypeface(mtypeFaceLight);
            holder.subCategoryDescription.setTypeface(mtypeFaceLight);
            if (serviceProviderListDetails != null) {
                if (mContext.getResources().getConfiguration().locale.getLanguage().equals("en")) {
                    holder.subCategoryName.setText(serviceProviderListDetails.get(position).getServiceProviderName());
                    holder.subCategoryDescription.setText(serviceProviderListDetails.get(position).getServiceProviderLocation());

                }else {
                    holder.subCategoryName.setText(serviceProviderListDetails.get(position).getServiceProviderNameArabic());
                    holder.subCategoryDescription.setText(serviceProviderListDetails.get(position).getServiceProviderLocationArabic());
                }
                  picasso.load(serviceProviderListDetails.get(position).getServiceProviderLogo()).placeholder(R.drawable.placeholder).fit().into(holder.categoryThumbnail);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (categoryPageNumber == 1) {
            if (mainCategoryItemList != null)
                return mainCategoryItemList.size();
            else return 0;
        } else if (categoryPageNumber == 2) {
            if (subCategoryItemList != null)
                return subCategoryItemList.size();
            else return 0;
        } else if (categoryPageNumber == 3) {
            if (serviceProviderListDetails != null)
                return serviceProviderListDetails.size();
            else return 0;
        } else {
            return 0;
        }
    }

    public void addCategoryListValues(ArrayList<MainCategoryItemList> mainCategoryItemList, byte categoryPageNumber) {
        this.mainCategoryItemList = mainCategoryItemList;
        this.categoryPageNumber = categoryPageNumber;
    }

    public void addSubCategoryListValues(ArrayList<SubCategoryItemList> subCategoryItemList, byte categoryPageNumber) {
        this.subCategoryItemList = subCategoryItemList;
        this.categoryPageNumber = categoryPageNumber;
    }

    public void setUpPicassoBuilderToReduceLoadingTime() {
        Picasso.Builder builder = new Picasso.Builder(mContext);
        picasso = builder.build();
    }

    public void addServiceProviderList(ArrayList<ServiceProviderListDetails> serviceProviderListDetails, byte categoryPageNumber) {
        this.serviceProviderListDetails = serviceProviderListDetails;
        this.categoryPageNumber = categoryPageNumber;
    }

    public void setFontTypeForText() {
        if (mContext.getResources().getConfiguration().locale.getLanguage().equals("en")) {

            mtypeFaceLight= Typeface.createFromAsset(mContext.getAssets(),
                    "fonts/Lato-Light.ttf");
        } else {

            mtypeFaceLight=Typeface.createFromAsset(mContext.getAssets(),
                    "fonts/GE_SS_Unique_Light.otf");
        }
    }

}
