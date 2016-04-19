package com.thahaseen.popularmovies.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thahaseen.popularmovies.R;
import com.thahaseen.popularmovies.common.AppConstants;
import com.thahaseen.popularmovies.data.entities.Trailer;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Thahaseen on 4/15/2016.
 */
public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerAdapter.TrailerViewHolder>{

    private ArrayList<Trailer> itemList;
    private Context context;
    private TrailerOnClickHandler trailerOnClickHandler;

    public MovieTrailerAdapter(Context context, ArrayList<Trailer> itemList, TrailerOnClickHandler clickHandler) {
        this.itemList = itemList;
        this.context = context;
        this.trailerOnClickHandler = clickHandler;
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item, null);
        TrailerViewHolder recycleView = new TrailerViewHolder(layoutView);
        return recycleView;
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int i) {
        Trailer rowItem = itemList.get(i);
        Picasso.with(context)
                .load(AppConstants.BASE_IMAGE_URL + rowItem.getTrailerURL())
                .into(holder.imgVideoThumbnail);
        holder.txtVideoName.setText(itemList.get(i).getTrailerName());
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public interface TrailerOnClickHandler {
        void onClick(TrailerViewHolder trailerViewHolder, Trailer trailer);
    }
    public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.imgVideoThumbnail) ImageView imgVideoThumbnail;
        @Bind(R.id.txtVideoName) TextView txtVideoName;

        public TrailerViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            Trailer trailer = itemList.get(getAdapterPosition());
            trailerOnClickHandler.onClick(this, trailer);
        }
    }
}
