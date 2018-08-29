package com.eventtracker.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MeetingResponseModel {

    @SerializedName("meeting_with")
    @Expose
    private String meetingWith;

    @SerializedName("location")
    @Expose
    private String location;

    @SerializedName("uuid")
    @Expose
    private String uuid;

    @SerializedName("event_uuid")
    @Expose
    private String eventUuid;

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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEventUuid() {
        return eventUuid;
    }

    public void setEventUuid(String eventUuid) {
        this.eventUuid = eventUuid;
    }
}
