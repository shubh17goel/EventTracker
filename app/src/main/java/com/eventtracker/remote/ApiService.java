package com.eventtracker.remote;

import com.eventtracker.remote.model.EventResponse;
import com.eventtracker.remote.model.EventResponseModel;
import com.eventtracker.remote.model.MeetingResponseModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService{

    @GET("events/")
    Call<ArrayList<EventResponseModel>> getEvents();

    @GET("{eventName}")
    Call<ArrayList<MeetingResponseModel>> getMeetingsOfGivenEvent(@Path("eventName") String eventName);

}
