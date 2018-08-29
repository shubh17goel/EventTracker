package com.eventtracker.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class EventEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "uuid")
    public String uuid;

    @ColumnInfo(name = "event_system_name")
    public String eventSystemName;

    @ColumnInfo(name = "location")
    public String location;

    @ColumnInfo(name = "updatedAt")
    public String updatedAt;

    public EventEntity(@NonNull String uuid, String eventSystemName, String location, String updatedAt) {
        this.uuid = uuid;
        this.eventSystemName = eventSystemName;
        this.location = location;
        this.updatedAt = updatedAt;
    }

    @NonNull
    public String getUuid() {
        return uuid;
    }

    public void setUuid(@NonNull String uuid) {
        this.uuid = uuid;
    }

    public String getEventSystemName() {
        return eventSystemName;
    }

    public void setEventSystemName(String eventSystemName) {
        this.eventSystemName = eventSystemName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
