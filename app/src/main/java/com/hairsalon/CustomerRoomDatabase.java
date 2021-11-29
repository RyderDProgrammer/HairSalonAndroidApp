package com.hairsalon;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Customer.class}, version = 1)
public abstract class CustomerRoomDatabase extends RoomDatabase
{
    public abstract CustomerDao customerDao();
    private static CustomerRoomDatabase INSTANCE;

    static CustomerRoomDatabase getDatabase(final Context context)
    {
        if(INSTANCE == null)
        {
            synchronized (CustomerRoomDatabase.class)
            {
                if(INSTANCE == null)
                {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),CustomerRoomDatabase.class,"customer_database").build();
                }
            }
        }
        return INSTANCE;
    }
}
