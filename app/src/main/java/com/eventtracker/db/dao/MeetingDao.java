package com.eventtracker.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.eventtracker.db.entity.MeetingEntity;

import java.util.List;

@Dao
public interface MeetingDao {
    @Query("SELECT * FROM MeetingEntity")
    List<MeetingEntity> getAll();

    @Insert
    void insert(MeetingEntity... meetingEntity);

    @Update
    void update(MeetingEntity... meetingEntity);

    @Delete
    void delete(MeetingEntity meetingEntity);

    @Query("SELECT * FROM MeetingEntity WHERE event_uuid IS (:eventUuid)")
    List<MeetingEntity> loadAllByEventId(String eventUuid);

    @Query("SELECT * FROM MeetingEntity WHERE uuid IS (:meetingUuid)")
    MeetingEntity loadByMeetingId(String meetingUuid);

}
