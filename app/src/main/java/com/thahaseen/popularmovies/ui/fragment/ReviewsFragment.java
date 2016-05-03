package com.thahaseen.popularmovies.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thahaseen.popularmovies.AppController;
import com.thahaseen.popularmovies.R;
import com.thahaseen.popularmovies.common.AppConstants;
import com.thahaseen.popularmovies.common.ConnectionDetector;
import com.thahaseen.popularmovies.common.VerticalSpaceItemDecoration;
import com.thahaseen.popularmovies.data.entities.Movie;
import com.thahaseen.popularmovies.data.entities.Review;
import com.thahaseen.popularmovies.data.entities.Trailer;
import com.thahaseen.popularmovies.ui.adapter.MovieReviewAdapter;
import com.thahaseen.popularmovies.ui.adapter.MovieTrailerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Thahaseen on 4/19/2016.
 */
public class ReviewsFragment extends Fragment {

    @Bind(R.id.recycler_view_reviews) RecyclerView recycler_view_reviews;
    @Bind(R.id.lblNoReviews) TextView lblNoReviews;
    ConnectionDetector connectionDetector;
    ArrayList<Review> lstReviews = new ArrayList<>();
    MovieReviewAdapter movieReviewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);
        ButterKnife.bind(this, view);
        connectionDetector = new ConnectionDetector(getActivity());
        final Movie movie = getArguments().getParcelable(AppConstants.MOVIE_DATA);
        if(connectionDetector.isConnectingToInternet()){
            getMovieReviews(movie.getId());
        }
        return view;
    }

    public void getMovieReviews(String movieID){
        String URL_FEED =  AppConstants.API_BASE_URL + movieID + "/reviews?api_key="+AppConstants.API_KEY;
        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET, URL_FEED,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            try {
                                JSONArray results = response.getJSONArray(AppConstants.TAG_RESULTS);
                                if(results.length() > 0) {
                                    Gson gson = new Gson();
                                    Type listType = new TypeToken<List<Review>>() {
                                    }.getType();
                                    lstReviews = gson.fromJson(results.toString(), listType);
                                    populateRecyclerView();
                                }else {
                                    lblNoReviews.setVisibility(View.VISIBLE);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();}
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("GET_REVIEWS", "Error: " + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(jsonReq);
    }

    public void populateRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recycler_view_reviews.setLayoutManager(linearLayoutManager);
        recycler_view_reviews.addItemDecoration(new VerticalSpaceItemDecoration(AppConstants.VERTICAL_ITEM_SPACE));
        movieReviewAdapter = new MovieReviewAdapter(getActivity(), lstReviews);
        recycler_view_reviews.setAdapter(movieReviewAdapter);
    }
}
