package com.eventtracker.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.eventtracker.R;

import de.greenrobot.event.EventBus;

public abstract class BaseActivity extends Activity implements RecyclerViewAdapter.AdapterInteraction {

    protected RecyclerView mRecyclerView;
    protected RecyclerViewAdapter mViewAdapter;
    protected TextView tvEmptyMessage;
    protected TextView tvRefresh;
    protected TextView tvLastUpdatedAt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);

        mRecyclerView = findViewById(R.id.recyclerView);
        tvEmptyMessage = findViewById(R.id.tvEmptyMessage);
        tvRefresh = findViewById(R.id.tvRefresh);
        tvLastUpdatedAt = findViewById(R.id.tvLastUpdatedAt);

        setClickListener();

    }

    protected void setClickListener() {
        tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchEventList();
            }
        });
    }

    abstract void fetchEventList();

    abstract void setRecyclerViewData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }
}
