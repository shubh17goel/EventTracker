package com.eventtracker.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.eventtracker.R;

public class EventViewHolder extends RecyclerView.ViewHolder {

    TextView tvEventName;
    TextView tvLocationName;
    TextView tvViewMeeting;

    public EventViewHolder(View itemView) {
        super(itemView);
        tvEventName = itemView.findViewById(R.id.tvEventName);
        tvLocationName = itemView.findViewById(R.id.tvLocationName);
        tvViewMeeting = itemView.findViewById(R.id.tvViewMeeting);
    }
}
