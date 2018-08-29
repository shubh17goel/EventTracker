package com.eventtracker.remote;

import com.eventtracker.remote.model.EventErrorModel;
import com.eventtracker.remote.model.EventResponse;
import com.eventtracker.remote.model.EventResponseModel;
import com.eventtracker.remote.model.MeetingErrorModel;
import com.eventtracker.remote.model.MeetingResponseModel;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient{

    private static final String BASE_URL = "https://my-json-server.typicode.com/ishwarverma39/demo/";

    private static OkHttpClient sOkHttpClient = new OkHttpClient.Builder().build();

    private static Retrofit sRetrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build();

    private ApiService mApiService;

    private static ApiClient instance;

    private ApiClient(){
        mApiService = sRetrofit.create(ApiService.class);
    }

    public static ApiClient getInstance(){
        if(instance ==  null){
            instance = new ApiClient();
        }
        return instance;
    }

    public void getEvents(){
        Call<ArrayList<EventResponseModel>> call = mApiService.getEvents();
        call.enqueue(new Callback<ArrayList<EventResponseModel>>() {
            @Override
            public void onResponse(Call<ArrayList<EventResponseModel>> call, Response<ArrayList<EventResponseModel>> response) {
                if(response.isSuccessful()){
                    EventBus.getDefault().post(new EventResponse(response.body()));
                }else{
                    EventBus.getDefault().post(new EventErrorModel());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<EventResponseModel>> call, Throwable t) {
                EventBus.getDefault().post(new EventErrorModel());
            }
        });
    }

    public void getMeetingOfGivenEvent(String eventName){
        Call<ArrayList<MeetingResponseModel>> call = mApiService.getMeetingsOfGivenEvent(eventName);
        call.enqueue(new Callback<ArrayList<MeetingResponseModel>>() {
            @Override
            public void onResponse(Call<ArrayList<MeetingResponseModel>> call, Response<ArrayList<MeetingResponseModel>> response) {
                if(response.isSuccessful()){
                    EventBus.getDefault().post(response.body());
                }else{
                    EventBus.getDefault().post(new MeetingErrorModel());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<MeetingResponseModel>> call, Throwable t) {
                EventBus.getDefault().post(new MeetingErrorModel());
            }
        });
    }


}