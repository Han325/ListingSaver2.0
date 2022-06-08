package com.bignerdranch.android.listingsaver;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ListingLab {
    private static ListingLab sListingLab;
    private List<Listing> sListings;



    public static ListingLab get(Context context) {
       if (sListingLab == null) {
           sListingLab = new ListingLab(context);
       }
       return sListingLab;
    }

    private ListingLab(Context context){
        sListings = new ArrayList<>();
//        for (int i = 0; i < 100; i++){
//            Listing listing = new Listing();
//            listing.setListTitle("Listing #" + i);
//            listing.setListCompany("Company #" + i);
//            listing.setFullTime(i % 2 == 0);
//            sListings.add(listing);
//        }
    }

    public void addListing(Listing l){
        sListings.add(l);
    }

    public List<Listing> getListings(){

        return sListings;
    }

    public Listing getListing(UUID id){
        for (Listing listing : sListings){
            if(listing.getListID().equals(id)){
                return listing;

            }
        }

        return null;
    }

}
