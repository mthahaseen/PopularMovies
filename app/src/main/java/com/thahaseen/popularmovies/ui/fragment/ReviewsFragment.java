package com.thahaseen.popularmovies.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thahaseen.popularmovies.R;

import butterknife.ButterKnife;

/**
 * Created by Thahaseen on 4/19/2016.
 */
public class ReviewsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
