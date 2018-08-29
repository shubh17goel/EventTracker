package com.eventtracker.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eventtracker.R;
import com.eventtracker.db.entity.EventEntity;
import com.eventtracker.db.entity.MeetingEntity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class RecyclerViewAdapter<T> extends RecyclerView.Adapter<EventViewHolder> {

    private ArrayList<T> modelList;
    private WeakReference<Context> mContext;
    private AdapterInteraction mListener;

    public RecyclerViewAdapter(ArrayList<T> modelList, Context context) {
        this.modelList = modelList;
        mContext = new WeakReference<>(context);
        mListener = (AdapterInteraction) mContext.get();
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_event,parent,false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, final int position) {
        if(modelList.get(position) instanceof EventEntity){
            holder.tvEventName.setText(mContext.get().getString(R.string.event_label,((EventEntity) modelList.get(position)).getEventSystemName()));
            holder.tvLocationName.setText(mContext.get().getString(R.string.location_label,((EventEntity) modelList.get(position)).getLocation()));
            holder.tvViewMeeting.setVisibility(View.VISIBLE);
            holder.tvViewMeeting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.openMeetingActivity(((EventEntity) modelList.get(position)).eventSystemName,
                            ((EventEntity) modelList.get(position)).getUuid());
                }
            });

        }else if(modelList.get(position) instanceof MeetingEntity){
            holder.tvEventName.setText(mContext.get().getString(R.string.meeting_with_label,((MeetingEntity) modelList.get(position)).getMeetingWith()));
            holder.tvLocationName.setText(mContext.get().getString(R.string.location_label,((MeetingEntity) modelList.get(position)).getLocation()));
            holder.tvViewMeeting.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public void updateList(ArrayList<T> modelList){
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    public interface AdapterInteraction{
        void openMeetingActivity(String eventSystemName, String eventUuid);
    }
}
