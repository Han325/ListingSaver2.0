package com.bignerdranch.android.listingsaver.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static com.bignerdranch.android.listingsaver.database.ListingDbSchema.ListingTable.NAME;



public class ListingBaseHelper extends SQLiteOpenHelper{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "listingBase.db";

    public ListingBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table " + NAME + "(" +
                " _id integer primary key autoincrement, " +
                ListingDbSchema.ListingTable.Cols.UUID + ", " +
                ListingDbSchema.ListingTable.Cols.TITLE + ", " +
                ListingDbSchema.ListingTable.Cols.DATE + ", " +
                ListingDbSchema.ListingTable.Cols.TIME + ", " +
                ListingDbSchema.ListingTable.Cols.LOCATION + ", " + ListingDbSchema.ListingTable.Cols.COMPANY + ", " +
                ListingDbSchema.ListingTable.Cols.LINK + ", " +
                ListingDbSchema.ListingTable.Cols.SALARY + ", " +
                ListingDbSchema.ListingTable.Cols.FULL_TIME + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
