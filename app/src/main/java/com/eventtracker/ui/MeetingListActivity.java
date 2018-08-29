package com.eventtracker.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.eventtracker.R;
import com.eventtracker.db.entity.MeetingEntity;
import com.eventtracker.local.PreferenceHelper;
import com.eventtracker.remote.ApiClient;
import com.eventtracker.remote.model.MeetingErrorModel;
import com.eventtracker.remote.model.MeetingResponseModel;
import com.eventtracker.service.MeetingAsyncTask;
import com.eventtracker.utils.Constants;
import com.eventtracker.utils.DialogUtils;
import com.eventtracker.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

public class MeetingListActivity extends BaseActivity implements InteractionListener<MeetingEntity> {

    private List<MeetingEntity> mMeetingEntities = new ArrayList<>();
    private String mEventName;
    private String mEventUuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent() != null){
            if(getIntent().hasExtra(Constants.EVENT_NAME_KEY)) {
                mEventName = getIntent().getStringExtra(Constants.EVENT_NAME_KEY);
            }
            if(getIntent().hasExtra(Constants.EVENT_UUID_KEY)) {
                mEventUuid = getIntent().getStringExtra(Constants.EVENT_UUID_KEY);
            }
        }

        mViewAdapter = new RecyclerViewAdapter((ArrayList) mMeetingEntities, this);
        mRecyclerView.setAdapter(mViewAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        new MeetingAsyncTask(this, null, mEventUuid).execute(Constants.FETCH_IDENTIFIER);
        if (mMeetingEntities != null && !mMeetingEntities.isEmpty()) {
            mRecyclerView.setVisibility(View.VISIBLE);
            tvLastUpdatedAt.setText(getString(R.string.last_updated_at, PreferenceHelper.getInstance(this).getLastEventUpdatedAt()));
            tvEmptyMessage.setVisibility(View.GONE);
        } else {
            mRecyclerView.setVisibility(View.GONE);
            tvEmptyMessage.setVisibility(View.VISIBLE);
            tvEmptyMessage.setText(getString(R.string.no_meeting_found));
            fetchEventList();
        }

        setRecyclerViewData();

    }

    void fetchEventList() {
        if (NetworkUtils.isNetworkAvailable(this)) {
            if (mMeetingEntities.size() == 0)
                DialogUtils.displayProgressDialog(this, getString(R.string.loading_meeting_data), false);
            ApiClient.getInstance().getMeetingOfGivenEvent(mEventName);
        } else {
            Toast.makeText(this, R.string.no_network, Toast.LENGTH_SHORT).show();
        }
    }

    public void onEvent(ArrayList<MeetingResponseModel> model) {
        DialogUtils.cancelProgressDialog();
        PreferenceHelper.getInstance(this).setLastEventUpdatedAt();
        new MeetingAsyncTask(this, model, mEventUuid).execute(Constants.INSERT_IDENTIFIER);
    }

    public void onEvent(MeetingErrorModel errorModel) {
        DialogUtils.cancelProgressDialog();
        Toast.makeText(this, R.string.something_went_wrong_please_try_again, Toast.LENGTH_SHORT).show();
    }

    void setRecyclerViewData() {
        if (mMeetingEntities != null && mMeetingEntities.size() > 0) {
            mRecyclerView.setVisibility(View.VISIBLE);
            tvLastUpdatedAt.setText(getString(R.string.last_updated_at, PreferenceHelper.getInstance(this).getLastEventUpdatedAt()));
            tvEmptyMessage.setVisibility(View.GONE);
            mViewAdapter = new RecyclerViewAdapter((ArrayList) mMeetingEntities, this);
            mRecyclerView.setAdapter(mViewAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            mRecyclerView.setVisibility(View.GONE);
            tvEmptyMessage.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onTaskDone(List<MeetingEntity> eventEntities) {
        tvLastUpdatedAt.setText(getString(R.string.last_updated_at, PreferenceHelper.getInstance(this).getLastEventUpdatedAt()));
        if (eventEntities != null && !eventEntities.isEmpty()) {
            mRecyclerView.setVisibility(View.VISIBLE);
            tvEmptyMessage.setVisibility(View.GONE);
            mViewAdapter.updateList((ArrayList) eventEntities);
        } else {
            mRecyclerView.setVisibility(View.GONE);
            tvEmptyMessage.setVisibility(View.VISIBLE);
            tvEmptyMessage.setText(getString(R.string.no_meeting_found));
        }
    }

    @Override
    public void openMeetingActivity(String eventSystemName, String eventName) {
        //Do nothing
    }
}
