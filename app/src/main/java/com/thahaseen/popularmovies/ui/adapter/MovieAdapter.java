package com.thahaseen.popularmovies.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.thahaseen.popularmovies.R;
import com.thahaseen.popularmovies.common.AppConstants;
import com.thahaseen.popularmovies.data.entities.Movie;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Thahaseen on 3/5/2016.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private ArrayList<Movie> itemList;
    private Context context;
    private MovieOnClickHandler movieOnClickHandler;

    public MovieAdapter(Context context, ArrayList<Movie> itemList, MovieOnClickHandler clickHandler) {
        this.itemList = itemList;
        this.context = context;
        this.movieOnClickHandler = clickHandler;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, null);
        MovieViewHolder recycleView = new MovieViewHolder(layoutView);
        return recycleView;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int i) {
        Movie rowItem = itemList.get(i);
        Picasso.with(context)
                    .load(AppConstants.BASE_IMAGE_URL + rowItem.getPoster_path())
                    .placeholder(R.drawable.movie_placeholder)
                    .error(R.drawable.movie_placeholder)
                    .into(holder.imgPoster);
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public interface MovieOnClickHandler {
        void onClick(MovieViewHolder moviesViewHolder, Movie movie);
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.imgMoviePoster) ImageView imgPoster;

        public MovieViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
            this.imgPoster = (ImageView) view.findViewById(R.id.imgMoviePoster);
        }

        @Override
        public void onClick(View v){
            Movie movie = itemList.get(getAdapterPosition());
            movieOnClickHandler.onClick(this, movie);
        }
    }
}

