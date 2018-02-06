package qfind.com.qfindappandroid.informationFragment;

import android.content.Context;
import android.os.Build;
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
import qfind.com.qfindappandroid.categoryfragment.RecyclerViewClickListener;

/**
 * Created by MoongedePC on 08-Jan-18.
 */

public class InformationFragmentAdapter extends RecyclerView.Adapter<InformationFragmentAdapter.InformationPageViewHolder> {

    private Context mcontext;
    private ArrayList<InformationFragmentModel> informationPages;
    private RecyclerViewClickListener mListener;

    public InformationFragmentAdapter(Context mcontext, ArrayList<InformationFragmentModel> informationPages,
                                      RecyclerViewClickListener mListener) {
        this.mcontext = mcontext;
        this.informationPages = informationPages;
        this.mListener = mListener;
    }

    class InformationPageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Nullable
        @BindView(R.id.info_icon)
        ImageView info_icon;
        @Nullable
        @BindView(R.id.info_point)
        ImageView info_point;
        @Nullable
        @BindView(R.id.info_content)
        TextView info_content;
        @Nullable
        @BindView(R.id.info_back_button)
        ImageView info_back_button;

        public InformationPageViewHolder(View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());
        }
    }

    @Override
    public InformationPageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View viewHolder = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.info_row, null, false);
        viewHolder.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new InformationPageViewHolder(viewHolder, mListener);
    }

    @Override
    public void onBindViewHolder(InformationPageViewHolder holder, int position) {

        InformationFragmentModel informationPage = informationPages.get(position);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.info_icon.setImageDrawable(mcontext.getResources().getDrawable(informationPages.get(position).getInfo_icon(), mcontext.getTheme()));
            holder.info_point.setImageDrawable(mcontext.getResources().getDrawable(informationPages.get(position).getInfo_point(), mcontext.getTheme()));
            holder.info_content.setText(informationPages.get(position).getInfo_content());
            holder.info_back_button.setImageDrawable(mcontext.getResources().getDrawable(informationPages.get(position).getInfo_back_button(), mcontext.getTheme()));
        } else {
            holder.info_icon.setImageDrawable(mcontext.getResources().getDrawable(informationPages.get(position).getInfo_icon()));
            holder.info_point.setImageDrawable(mcontext.getResources().getDrawable(informationPages.get(position).getInfo_point()));
            holder.info_content.setText(informationPages.get(position).getInfo_content());
            holder.info_back_button.setImageDrawable(mcontext.getResources().getDrawable(informationPages.get(position).getInfo_back_button()));
        }
    }

    @Override
    public int getItemCount() {
        return informationPages.size();
    }


}

