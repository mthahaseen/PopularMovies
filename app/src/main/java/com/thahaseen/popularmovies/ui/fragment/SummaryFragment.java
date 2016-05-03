package com.thahaseen.popularmovies.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thahaseen.popularmovies.R;
import com.thahaseen.popularmovies.common.AppConstants;
import com.thahaseen.popularmovies.data.db.DatabaseHandler;
import com.thahaseen.popularmovies.data.entities.Movie;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Thahaseen on 4/19/2016.
 */
public class SummaryFragment extends Fragment {

    @Bind(R.id.txtMovieOverview) TextView txtMovieOverview;
    @Bind(R.id.txtMovieReleaseDate) TextView txtMovieReleaseDate;
    @Bind(R.id.txtMovieRating) TextView txtMovieRating;
    @Bind(R.id.imgFav) ImageView imgFav;
    private DatabaseHandler databaseHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_summary, container, false);
        ButterKnife.bind(this, view);
        databaseHandler = new DatabaseHandler(getActivity());
        final Movie movie = getArguments().getParcelable(AppConstants.MOVIE_DATA);
        txtMovieOverview.setText(movie.getOverview());
        txtMovieReleaseDate.setText("Release Date: " + movie.getRelease_date());
        txtMovieRating.setText(Double.toString(movie.getVote_average()));
        if(databaseHandler.isMovieFavorite(movie.getId())){
            imgFav.setImageResource(R.drawable.ic_star_rate_yellow_36dp);
        }else{
            imgFav.setImageResource(R.drawable.ic_star_rate_white_36dp);
        }
        imgFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(databaseHandler.isMovieFavorite(movie.getId())){
                    databaseHandler.deleteMovieFavorite(movie.getId());
                    imgFav.setImageResource(R.drawable.ic_star_rate_white_36dp);
                }else{
                    databaseHandler.addMovieFavorite(movie);
                    imgFav.setImageResource(R.drawable.ic_star_rate_yellow_36dp);
                }
            }
        });
        return view;
    }
}
