package edu.montclair.mobilecomputing.m_alrajab.assignment_1.model;

import android.provider.BaseColumns;

/**
 * Created by denis on 3/4/2017.
 */

public class RegistrationContracts {

    private RegistrationContracts() {}

    /* Inner class that defines the table contents */
    public static class StudentEntry implements BaseColumns {

        public static final String _ID = "id";
        public static final String COL_USERNAME = "username";
        public static final String COL_NAME = "name";
        public static final String COL_EMAIL = "email";
        public static final String COL_PASS = "password";
        public static final String COL_AGE = "DOB";
        public static final String COL_MAJOR = "Major";
    }
    public static final String TABLE_NAME = "Registration_table";


    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + "( " +
                    StudentEntry._ID + " INTEGER PRIMARY KEY, " +
                    StudentEntry.COL_USERNAME + " TEXT, " +
                    StudentEntry.COL_NAME + " TEXT, " +
                    StudentEntry.COL_EMAIL + " TEXT, " +
                    StudentEntry.COL_PASS + " TEXT, " +
                    StudentEntry.COL_AGE + " TEXT, " +
                    StudentEntry.COL_MAJOR + " TEXT " + ")";


    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

}
