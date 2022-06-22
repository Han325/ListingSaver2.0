package com.bignerdranch.android.listingsaver;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

public class ListingPagerActivity extends AppCompatActivity {

    private static final String EXTRA_LISTING_ID =
            "com.bignerdranch.android.listingsaver.listing_id";

    private ViewPager sViewPager;
    private List<Listing> sListings;

    public static Intent newIntent(Context packageContext, UUID listingID) {
        Intent intent = new Intent(packageContext, ListingPagerActivity.class);
        intent.putExtra(EXTRA_LISTING_ID, listingID);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_pager);

        UUID listingID = (UUID) getIntent()
                .getSerializableExtra(EXTRA_LISTING_ID);

        sViewPager = (ViewPager) findViewById(R.id.listing_view_pager);

        sListings = ListingLab.get(this).getListings();
        FragmentManager fragmentManager = getSupportFragmentManager();
        sViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Listing listing = sListings.get(position);
                return ListingFragment.newInstance(listing.getListID());
            }

            @Override
            public int getCount() {
                return sListings.size();
            }
        });

        for (int i = 0; i < sListings.size(); i++) {
            if (sListings.get(i).getListID().equals(listingID)) {
                sViewPager.setCurrentItem(i);
                break;
            }
        }
    }

}
