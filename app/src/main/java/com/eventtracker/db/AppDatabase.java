package com.eventtracker.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.eventtracker.db.dao.EventDao;
import com.eventtracker.db.dao.MeetingDao;
import com.eventtracker.db.entity.EventEntity;
import com.eventtracker.db.entity.MeetingEntity;

@Database(entities = {EventEntity.class, MeetingEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    private static AppDatabase create(final Context context) {
        return Room.databaseBuilder(context,
                AppDatabase.class, "database-name").build();
    }

    public static AppDatabase getInstance(Context context) {
        if(instance == null){
            instance = create(context);
        }
        return instance;
    }
    public abstract EventDao getEventDao();
    public abstract MeetingDao getMeetingDao();
}

