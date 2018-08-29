package com.eventtracker.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.eventtracker.db.entity.EventEntity;
import com.eventtracker.remote.model.EventResponseModel;

import java.util.List;

@Dao
public interface EventDao {

    @Query("SELECT * FROM EventEntity")
    List<EventEntity> getAll();

    @Insert
    void insert(EventEntity... events);

    @Update
    void update(EventEntity... events);

    @Delete
    void delete(EventEntity event);

    @Query("SELECT * FROM EventEntity WHERE uuid IS (:eventUuid)")
    EventEntity loadByEventId(String eventUuid);

}
