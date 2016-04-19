package com.thahaseen.popularmovies.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thahaseen.popularmovies.R;
import com.thahaseen.popularmovies.common.AppConstants;
import com.thahaseen.popularmovies.data.entities.Movie;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Thahaseen on 4/19/2016.
 */
public class SummaryFragment extends Fragment {

    @Bind(R.id.txtMovieOverview) TextView txtMovieOverview;
    @Bind(R.id.txtMovieReleaseDate) TextView txtMovieReleaseDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_summary, container, false);
        ButterKnife.bind(this, view);
        Movie  movie = getActivity().getIntent().getParcelableExtra(AppConstants.MOVIE_DATA);
        txtMovieOverview.setText(movie.getOverview());
        txtMovieReleaseDate.setText("Release Date: " + movie.getRelease_date());
        return view;
    }
}
