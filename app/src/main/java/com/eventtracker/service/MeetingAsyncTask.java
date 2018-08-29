package com.eventtracker.service;

import android.content.Context;
import android.os.AsyncTask;

import com.eventtracker.db.AppDatabase;
import com.eventtracker.db.dao.MeetingDao;
import com.eventtracker.db.entity.MeetingEntity;
import com.eventtracker.remote.model.MeetingResponseModel;
import com.eventtracker.ui.InteractionListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static com.eventtracker.utils.Constants.FETCH_IDENTIFIER;
import static com.eventtracker.utils.Constants.INSERT_IDENTIFIER;

public class MeetingAsyncTask extends AsyncTask<String, Void, Void> {
    private WeakReference<Context> mContextWeakReference;
    private List<MeetingResponseModel> mResponseModels;
    private InteractionListener<MeetingEntity> mListener;
    private List<MeetingEntity> mMeetingEntities = new ArrayList<>();
    private String mEventUuid;

    public MeetingAsyncTask(Context context, List<MeetingResponseModel> model, String eventUuid) {
        mContextWeakReference = new WeakReference<>(context);
        mResponseModels = model;
        mListener = (InteractionListener) mContextWeakReference.get();
        this.mEventUuid = eventUuid;
    }

    @Override
    protected Void doInBackground(String... strs) {
        if (strs != null) {
            MeetingDao meetingDao = AppDatabase.getInstance(mContextWeakReference.get()).getMeetingDao();
            switch (strs[0]) {
                case INSERT_IDENTIFIER:
                    for (MeetingResponseModel model : mResponseModels) {
                        if (meetingDao.loadByMeetingId(model.getUuid()) != null) {
                            meetingDao.update(new MeetingEntity(model.getUuid(), model.getMeetingWith(), model.getLocation(), model.getEventUuid()));
                        } else {
                            meetingDao.insert(new MeetingEntity(model.getUuid(), model.getMeetingWith(), model.getLocation(), model.getEventUuid()));
                        }
                    }
                    mMeetingEntities = AppDatabase.getInstance(mContextWeakReference.get()).getMeetingDao().loadAllByEventId(mEventUuid);
                    break;
                case FETCH_IDENTIFIER:
                    mMeetingEntities = AppDatabase.getInstance(mContextWeakReference.get()).getMeetingDao().loadAllByEventId(mEventUuid);
                    break;
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (mListener != null) mListener.onTaskDone(mMeetingEntities);
    }
}
