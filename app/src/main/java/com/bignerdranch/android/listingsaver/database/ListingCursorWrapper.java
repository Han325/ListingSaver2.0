package com.bignerdranch.android.listingsaver.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.bignerdranch.android.listingsaver.Listing;

import java.util.Date;
import java.util.UUID;

public class ListingCursorWrapper extends CursorWrapper {
    public ListingCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Listing getListing(){
        String uuidString = getString(getColumnIndex(ListingDbSchema.ListingTable.Cols.UUID));
        String title = getString(getColumnIndex(ListingDbSchema.ListingTable.Cols.TITLE));
        long date = getLong(getColumnIndex(ListingDbSchema.ListingTable.Cols.DATE));
        long time = getLong(getColumnIndex(ListingDbSchema.ListingTable.Cols.TIME));
        String location = getString(getColumnIndex(ListingDbSchema.ListingTable.Cols.LOCATION));
        String company = getString(getColumnIndex(ListingDbSchema.ListingTable.Cols.COMPANY));
        String link = getString(getColumnIndex(ListingDbSchema.ListingTable.Cols.LINK));
        String salary = getString(getColumnIndex(ListingDbSchema.ListingTable.Cols.SALARY));
        int fullTime = getInt(getColumnIndex(ListingDbSchema.ListingTable.Cols.FULL_TIME));


        Listing listing = new Listing(UUID.fromString(uuidString));
        listing.setListTitle(title);
        listing.setListDate(new Date(date));
        listing.setListTime(new Date(time));
        listing.setListLocation(location);
        listing.setListCompany(company);
        listing.setListLink(link);
        listing.setListSalary(salary);
        listing.setFullTime(fullTime != 0);

        return listing;
    }
}
