package com.example.madassignment;

import android.content.Context;
import android.service.autofill.UserData;

import androidx.room.Room;

public class UserDbInstance {

    private static UserDatabase database;

    public static UserDatabase getDatabase(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context,
                            UserDatabase.class, "app_database")
                    .allowMainThreadQueries()
                    .build();
        }
        return database;
    }
}