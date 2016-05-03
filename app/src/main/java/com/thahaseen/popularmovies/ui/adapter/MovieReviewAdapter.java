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
import com.thahaseen.popularmovies.data.entities.Review;
import com.thahaseen.popularmovies.data.entities.Trailer;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Thahaseen on 5/2/2016.
 */
public class MovieReviewAdapter extends RecyclerView.Adapter<MovieReviewAdapter.ReviewViewHolder>{

    private ArrayList<Review> itemList;
    private Context context;

    public MovieReviewAdapter(Context context, ArrayList<Review> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, null);
        ReviewViewHolder recycleView = new ReviewViewHolder(layoutView);
        return recycleView;
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int i) {
        holder.txtAuthor.setText(itemList.get(i).getAuthor());
        holder.txtReview.setText(itemList.get(i).getContent());
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.txtAuthor) TextView txtAuthor;
        @Bind(R.id.txtReview) TextView txtReview;

        public ReviewViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
