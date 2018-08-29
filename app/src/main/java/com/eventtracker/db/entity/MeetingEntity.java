package com.eventtracker.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class MeetingEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "uuid")
    public String uuid;

    @ColumnInfo(name = "meeting_with")
    public String meetingWith;

    @ColumnInfo(name = "location")
    public String location;

    @ColumnInfo(name = "event_uuid")
    public String eventUuid;

    public MeetingEntity(String uuid, String meetingWith, String location, String eventUuid) {
        this.uuid = uuid;
        this.meetingWith = meetingWith;
        this.location = location;
        this.eventUuid = eventUuid;
    }

    @NonNull
    public String getUuid() {
        return uuid;
    }

    public void setUuid(@NonNull String uuid) {
        this.uuid = uuid;
    }

    public String getMeetingWith() {
        return meetingWith;
    }

    public void setMeetingWith(String meetingWith) {
        this.meetingWith = meetingWith;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEventUuid() {
        return eventUuid;
    }

    public void setEventUuid(String eventUuid) {
        this.eventUuid = eventUuid;
    }
}
