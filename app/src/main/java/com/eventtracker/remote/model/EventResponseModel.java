package com.eventtracker.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventResponseModel {

    @SerializedName("event_system_name")
    @Expose
    private String eventName;

    @SerializedName("location")
    @Expose
    private String location;

    @SerializedName("uuid")
    @Expose
    private String uuid;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
