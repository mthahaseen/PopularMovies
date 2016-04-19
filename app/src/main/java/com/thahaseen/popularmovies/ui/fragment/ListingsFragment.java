package com.thahaseen.popularmovies.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.thahaseen.popularmovies.data.db.DatabaseHandler;
import com.thahaseen.popularmovies.data.entities.Movie;
import com.thahaseen.popularmovies.ui.activity.DetailActivity;
import com.thahaseen.popularmovies.ui.adapter.MovieAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Thahaseen on 3/2/2016.
 */
public class ListingsFragment extends Fragment {

    @Bind(R.id.recycler_view_listings) RecyclerView recyclerView;

    private static final String TAG = ListingsFragment.class.getSimpleName();
    private ArrayList<Movie> movieList;
    private DatabaseHandler databaseHandler;
    private ConnectionDetector connectionDetector;
    private GridLayoutManager gridLayoutManager;
    private MovieAdapter movieAdapter;
    private MovieAdapter.MovieOnClickHandler movieOnClickHandler;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listings, container, false);
        ButterKnife.bind(this, view);
        databaseHandler = new DatabaseHandler(getActivity());
        connectionDetector = new ConnectionDetector(getActivity());
        movieOnClickHandler = new MovieAdapter.MovieOnClickHandler() {
            @Override
            public void onClick(MovieAdapter.MovieViewHolder moviesViewHolder, Movie movie) {
                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra(AppConstants.MOVIE_DATA, movie);
                startActivity(intent);
            }
        };
        movieList = new ArrayList<Movie>();
        if (savedInstanceState != null && savedInstanceState.containsKey(AppConstants.MOVIE_DATA)) {
            movieList = savedInstanceState.getParcelableArrayList(AppConstants.MOVIE_DATA);
            populateRecyclerView();
        }else {
            if (connectionDetector.isConnectingToInternet()) {
                getMovieListings(1);
            } else {
                Toast.makeText(getActivity(), "Trouble connecting to network", Toast.LENGTH_SHORT).show();
                if (databaseHandler.getMovieRowCount() > 0) {
                    movieList = databaseHandler.getOfflineMovieList();
                    populateRecyclerView();
                }
            }
        }

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_home, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_sort_popular:
                if(connectionDetector.isConnectingToInternet()) {
                    getMovieListings(1);
                }else {
                    Toast.makeText(getActivity(),"Trouble connecting to network",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.action_sort_rated:
                if(connectionDetector.isConnectingToInternet()) {
                    getMovieListings(2);
                }else {
                    Toast.makeText(getActivity(),"Trouble connecting to network",Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getMovieListings(int sort_order){
        String URL_FEED = "";
        if(sort_order == 1){
            URL_FEED = AppConstants.API_POPULAR_MOVIES+"?sort_by="+AppConstants.POPULARITY_DESC+"&api_key="+AppConstants.API_KEY;
        }else if(sort_order == 2){
            URL_FEED = AppConstants.API_POPULAR_MOVIES+"?sort_by="+AppConstants.RATING_DESC+"&api_key="+AppConstants.API_KEY;
        }
        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET, URL_FEED,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null) {
                        try {
                            JSONArray results = response.getJSONArray(AppConstants.TAG_RESULTS);
                            if(results.length() > 0) {
                                Gson gson = new Gson();
                                Type listType = new TypeToken<List<Movie>>() {
                                }.getType();
                                movieList = gson.fromJson(results.toString(), listType);
                                addMoviesToDB();
                                populateRecyclerView();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();}
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(jsonReq);
    }

    /*
    Method to add movie list to offline db, so this list can be retrieved and shown to user if there is no data connection.
     */
    public void addMoviesToDB(){
        if(movieList.size()!=0){
            databaseHandler.resetMovieTable();
            for(Movie movie : movieList){
                databaseHandler.addMovie(movie.getId(),movie.getTitle(),movie.getPoster_path(),movie.getOverview(),
                        movie.getBackdrop_path(),movie.getOriginal_title(),movie.getOriginal_language(),movie.getVote_average(),movie.getRelease_date());
            }
        }
    }

    public void populateRecyclerView(){
        recyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        movieAdapter = new MovieAdapter(getActivity(), movieList, movieOnClickHandler);
        recyclerView.setAdapter(movieAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putParcelableArrayList(AppConstants.MOVIE_DATA, movieList);
    }
}
