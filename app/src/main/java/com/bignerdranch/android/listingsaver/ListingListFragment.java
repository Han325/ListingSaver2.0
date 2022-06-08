package com.bignerdranch.android.listingsaver;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;


import java.util.List;

public class ListingListFragment extends Fragment {

    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    private RecyclerView listRecyclerView;
    private ListingAdapter sAdapter;
    private boolean sSubtitleVisible;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listing_list, container, false);


        listRecyclerView = view.findViewById(R.id.listing_recycler_view);
        listRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (savedInstanceState != null) {
            sSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        updateUI();


        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, sSubtitleVisible);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_listing_list, menu);

        MenuItem subtitleItem = menu.findItem(R.id.total_listing);
        if (sSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.total_listing:
                sSubtitleVisible = !sSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            case R.id.add_listing:
                Listing listing = new Listing();
                ListingLab.get(getActivity()).addListing(listing);
                Intent intent = ListingPagerActivity
                        .newIntent(getActivity(), listing.getListID());
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateSubtitle() {
        ListingLab listingLab = ListingLab.get(getActivity());
        int listingCount = listingLab.getListings().size();
        String subtitle = getString(R.string.subtitle, listingCount);

        if (!sSubtitleVisible) {
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    private void updateUI() {
        ListingLab listingLab = ListingLab.get(getActivity());
        List<Listing> listings = listingLab.getListings();

        if (sAdapter == null){
            sAdapter = new ListingAdapter(listings);
            listRecyclerView.setAdapter(sAdapter);
        } else {
            sAdapter.notifyDataSetChanged();
        }

        updateSubtitle();

    }


    private class ListingHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView sListingTitleView;
        private TextView sListingCompanyView;
        private ImageView sFullTimeImageView;
        private Listing sListing;


        public ListingHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_listing, parent, false));
            itemView.setOnClickListener(this);

            sListingTitleView = itemView.findViewById(R.id.listing_title);
            sListingCompanyView = itemView.findViewById(R.id.listing_company);
            sFullTimeImageView = itemView.findViewById(R.id.full_time);

        }

        @Override
        public void onClick(View view){
            Intent intent = ListingPagerActivity.newIntent(getActivity(), sListing.getListID());
            startActivity(intent);
        }

        public void bind(Listing listing){
            sListing = listing;
            sListingTitleView.setText(sListing.getListTitle());
            sListingCompanyView.setText(sListing.getListCompany());
            sFullTimeImageView.setVisibility(listing.isFullTime() ? View.VISIBLE : View.GONE);
        }


    }

    private class ListingAdapter extends RecyclerView.Adapter<ListingHolder> {
        private List<Listing> sListings;

        public ListingAdapter(List<Listing> listings) {
            sListings = listings;
        }

        @Override
        public ListingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ListingHolder(layoutInflater, parent);

        }


        @Override
        public void onBindViewHolder(ListingHolder holder, int position) {
            Listing listing = sListings.get(position);
            holder.bind(listing);
        }

        @Override
        public int getItemCount() {
            return sListings.size();
        }

        public void setListings(List<Listing> listings){
            sListings = listings;
        }

    }

}
