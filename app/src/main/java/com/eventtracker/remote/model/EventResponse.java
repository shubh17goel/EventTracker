package com.eventtracker.remote.model;

import java.util.List;

public class EventResponse {

    List<EventResponseModel> mEventResponseModels;

    public EventResponse(List<EventResponseModel> eventResponseModels) {
        mEventResponseModels = eventResponseModels;
    }

    public List<EventResponseModel> getEventResponseModels() {
        return mEventResponseModels;
    }

    public void setEventResponseModels(List<EventResponseModel> eventResponseModels) {
        mEventResponseModels = eventResponseModels;
    }
}
