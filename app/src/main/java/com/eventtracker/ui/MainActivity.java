package com.eventtracker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.eventtracker.R;
import com.eventtracker.db.entity.EventEntity;
import com.eventtracker.local.PreferenceHelper;
import com.eventtracker.remote.ApiClient;
import com.eventtracker.remote.model.EventErrorModel;
import com.eventtracker.remote.model.EventResponse;
import com.eventtracker.service.EventAsyncTask;
import com.eventtracker.utils.Constants;
import com.eventtracker.utils.DialogUtils;
import com.eventtracker.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements InteractionListener<EventEntity> {

    private List<EventEntity> mEventEntities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewAdapter = new RecyclerViewAdapter((ArrayList) mEventEntities, this);
        mRecyclerView.setAdapter(mViewAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        new EventAsyncTask(this, null).execute(Constants.FETCH_IDENTIFIER);
        if (mEventEntities != null && !mEventEntities.isEmpty()) {
            mRecyclerView.setVisibility(View.VISIBLE);
            tvLastUpdatedAt.setText(getString(R.string.last_updated_at, PreferenceHelper.getInstance(this).getLastEventUpdatedAt()));
            tvEmptyMessage.setVisibility(View.GONE);
        } else {
            mRecyclerView.setVisibility(View.GONE);
            tvEmptyMessage.setVisibility(View.VISIBLE);
            fetchEventList();
        }

        setRecyclerViewData();

    }

    void fetchEventList() {
        if (NetworkUtils.isNetworkAvailable(this)) {
            if(mEventEntities.size()==0)
                DialogUtils.displayProgressDialog(this, getString(R.string.loading_event_data), false);
            ApiClient.getInstance().getEvents();
        } else {
            Toast.makeText(this, R.string.no_network, Toast.LENGTH_SHORT).show();
        }
    }

    public void onEvent(EventResponse model) {
        DialogUtils.cancelProgressDialog();
        PreferenceHelper.getInstance(this).setLastEventUpdatedAt();
        new EventAsyncTask(this, model.getEventResponseModels()).execute(Constants.INSERT_IDENTIFIER);
    }

    void setRecyclerViewData() {
        if (mEventEntities != null && mEventEntities.size() > 0) {
            mRecyclerView.setVisibility(View.VISIBLE);
            tvLastUpdatedAt.setText(getString(R.string.last_updated_at, PreferenceHelper.getInstance(this).getLastEventUpdatedAt()));
            tvEmptyMessage.setVisibility(View.GONE);
            mViewAdapter = new RecyclerViewAdapter((ArrayList) mEventEntities, this);
            mRecyclerView.setAdapter(mViewAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            mRecyclerView.setVisibility(View.GONE);
            tvEmptyMessage.setVisibility(View.VISIBLE);
        }
    }

    public void onEvent(EventErrorModel errorModel) {
        DialogUtils.cancelProgressDialog();
        Toast.makeText(this, R.string.something_went_wrong_please_try_again, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onTaskDone(List<EventEntity> eventEntities) {
        tvLastUpdatedAt.setText(getString(R.string.last_updated_at, PreferenceHelper.getInstance(this).getLastEventUpdatedAt()));
        if (eventEntities != null && !eventEntities.isEmpty()) {
            mRecyclerView.setVisibility(View.VISIBLE);
            tvEmptyMessage.setVisibility(View.GONE);
            mViewAdapter.updateList((ArrayList) eventEntities);
        }else{
            mRecyclerView.setVisibility(View.GONE);
            tvEmptyMessage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void openMeetingActivity(String eventSystemName, String eventUuid) {
        Intent intent =  new Intent(this, MeetingListActivity.class);
        intent.putExtra(Constants.EVENT_NAME_KEY,eventSystemName);
        intent.putExtra(Constants.EVENT_UUID_KEY,eventUuid);
        startActivity(intent);
    }
}
