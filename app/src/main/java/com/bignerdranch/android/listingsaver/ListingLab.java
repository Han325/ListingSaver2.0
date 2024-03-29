// Name: Eu Yu Han
// Student ID: 18123547

package com.bignerdranch.android.listingsaver;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bignerdranch.android.listingsaver.database.ListingBaseHelper;
import com.bignerdranch.android.listingsaver.database.ListingCursorWrapper;
import com.bignerdranch.android.listingsaver.database.ListingDbSchema;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ListingLab {
    private static ListingLab sListingLab;
    private Context sContext;
    private SQLiteDatabase sDatabase;


    public static ListingLab get(Context context) {
        if (sListingLab == null) {
            sListingLab = new ListingLab(context);
        }
        return sListingLab;
    }

    private ListingLab(Context context) {
        sContext = context.getApplicationContext();
        sDatabase = new ListingBaseHelper(sContext)
                .getWritableDatabase();
    }

    public void addListing(Listing l) {
        ContentValues values = getContentValues(l);

        sDatabase.insert(ListingDbSchema.ListingTable.NAME, null, values);
    }

    public void deleteListing(String[] values) {

        sDatabase.delete(ListingDbSchema.ListingTable.NAME, "uuid=?", values);
    }

    public List<Listing> getListings() {

        List<Listing> listings = new ArrayList<>();

        ListingCursorWrapper cursor = queryListings(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                listings.add(cursor.getListing());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return listings;
    }

    public Listing getListing(UUID id) {

        ListingCursorWrapper cursor = queryListings(
                ListingDbSchema.ListingTable.Cols.UUID + " =? ",
                new String[]{id.toString()}
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getListing();
        } finally {
            cursor.close();
        }

    }


    public File getPhotoFile(Listing listing) {
        File filesDir = sContext.getFilesDir();
        return new File(filesDir, listing.getPhotoFileName());
    }


    public void updateListing(Listing listing) {
        String uuidString = listing.getListID().toString();
        ContentValues values = getContentValues(listing);

        sDatabase.update(ListingDbSchema.ListingTable.NAME, values,
                ListingDbSchema.ListingTable.Cols.UUID + " = ? ",
                new String[]{uuidString});

    }

    private ListingCursorWrapper queryListings(String whereClause, String[] whereArgs) {
        Cursor cursor = sDatabase.query(
                ListingDbSchema.ListingTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new ListingCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Listing listing) {
        ContentValues values = new ContentValues();
        values.put(ListingDbSchema.ListingTable.Cols.UUID, listing.getListID().toString());
        values.put(ListingDbSchema.ListingTable.Cols.TITLE, listing.getListTitle());
        values.put(ListingDbSchema.ListingTable.Cols.DATE, listing.getListDate().getTime());
        values.put(ListingDbSchema.ListingTable.Cols.TIME, listing.getListTime().getTime());
        values.put(ListingDbSchema.ListingTable.Cols.LOCATION, listing.getListLocation());
        values.put(ListingDbSchema.ListingTable.Cols.COMPANY, listing.getListCompany());
        values.put(ListingDbSchema.ListingTable.Cols.SALARY, listing.getListSalary());
        values.put(ListingDbSchema.ListingTable.Cols.LINK, listing.getListLink());
        values.put(ListingDbSchema.ListingTable.Cols.FULL_TIME, listing.isFullTime());
        values.put(ListingDbSchema.ListingTable.Cols.RECRUITER, listing.getListRecruiter());

        return values;
    }

}
