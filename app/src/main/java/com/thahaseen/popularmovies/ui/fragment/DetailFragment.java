package com.thahaseen.popularmovies.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thahaseen.popularmovies.R;
import com.thahaseen.popularmovies.common.AppConstants;
import com.thahaseen.popularmovies.common.ConnectionDetector;
import com.thahaseen.popularmovies.data.entities.Movie;
import com.thahaseen.popularmovies.data.entities.Trailer;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Thahaseen on 3/2/2016.
 */
public class DetailFragment extends Fragment{

    @Bind(R.id.imgMovieImage) ImageView imgMovieBackDrop;
    @Bind(R.id.txtMovieTitle) TextView txtMovieTitle;
    @Bind(R.id.DetailViewPager) ViewPager detailViewPager;
    @Bind(R.id.detail_tab_layout) TabLayout tabLayout;
    ConnectionDetector connectionDetector;
    private Movie movie;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /*if (!twoPane)
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(model.getOriginalTitle());*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, view);
        connectionDetector = new ConnectionDetector(getActivity());
        movie = getActivity().getIntent().getParcelableExtra(AppConstants.MOVIE_DATA);
        Picasso.with(getActivity())
                .load(AppConstants.BASE_IMAGE_BACK_DROP_URL + movie.getBackdrop_path())
                .into(imgMovieBackDrop);
        txtMovieTitle.setText(movie.getTitle());
        tabLayout.addTab(tabLayout.newTab().setText(AppConstants.TAB_SUMMARY));
        tabLayout.addTab(tabLayout.newTab().setText(AppConstants.TAB_TRAILER));
        tabLayout.addTab(tabLayout.newTab().setText(AppConstants.TAB_REVIEWS));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        tabLayout.setTabTextColors(getResources().getColor(android.R.color.white), getResources().getColor(android.R.color.white));
        PagerAdapter adapter = new PagerAdapter(getChildFragmentManager(), tabLayout.getTabCount());
        detailViewPager.setAdapter(adapter);
        detailViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                detailViewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            getActivity().finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public class PagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;

        public PagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    SummaryFragment tab1 = new SummaryFragment();
                    return tab1;
                case 1:
                    TrailerFragment tab2 = new TrailerFragment();
                    return tab2;
                case 2:
                    ReviewsFragment tab3 = new ReviewsFragment();
                    return tab3;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }
    }
}
