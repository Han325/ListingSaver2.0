package com.bignerdranch.android.listingsaver;

import android.support.v4.app.Fragment;
import android.content.Intent;
import java.util.UUID;
import android.content.Context;

public class ListingActivity extends SingleFragmentActivity{

    private static final String EXTRA_LISTING_ID =
        "com.bignerdranch.android.listingsaver.listing_id";

    public static Intent newIntent(Context packageContext, UUID listingID){
        Intent intent = new Intent(packageContext, ListingActivity.class);
        intent.putExtra(EXTRA_LISTING_ID, listingID);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID listingID = (UUID) getIntent()
                .getSerializableExtra(EXTRA_LISTING_ID);
        System.out.println(listingID);
        return ListingFragment.newInstance(listingID);

    }

}
