package com.bignerdranch.android.listingsaver.database;

public class ListingDbSchema {
    public static final class ListingTable {

        public static final String NAME = "listings";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String TIME = "time";
            public static final String LOCATION = "location";
            public static final String COMPANY = "company";
            public static final String LINK = "link";
            public static final String SALARY = "salary";
            public static final String FULL_TIME = "full_time";

        }
    }

}
