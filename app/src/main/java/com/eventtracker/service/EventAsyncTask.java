package com.eventtracker.service;

import android.content.Context;
import android.os.AsyncTask;

import com.eventtracker.db.AppDatabase;
import com.eventtracker.db.dao.EventDao;
import com.eventtracker.db.entity.EventEntity;
import com.eventtracker.remote.model.EventResponseModel;
import com.eventtracker.ui.InteractionListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static com.eventtracker.utils.Constants.FETCH_IDENTIFIER;
import static com.eventtracker.utils.Constants.INSERT_IDENTIFIER;

public class EventAsyncTask extends AsyncTask<String, Void, Void> {

    private WeakReference<Context> mContextWeakReference;
    private List<EventResponseModel> mResponseModels;
    private InteractionListener<EventEntity> mListener;
    private List<EventEntity> mEventEntities = new ArrayList<>();

    public EventAsyncTask(Context context, List<EventResponseModel> model) {
        mContextWeakReference = new WeakReference<>(context);
        mResponseModels = model;
        mListener = (InteractionListener) mContextWeakReference.get();
    }

    @Override
    protected Void doInBackground(String... strs) {
        if (strs != null) {
            EventDao eventDao = AppDatabase.getInstance(mContextWeakReference.get()).getEventDao();
            switch (strs[0]) {
                case INSERT_IDENTIFIER:
                    for (EventResponseModel model : mResponseModels) {
                        if (eventDao.loadByEventId(model.getUuid()) != null) {
                            eventDao.update(new EventEntity(model.getUuid(), model.getEventName(), model.getLocation(), ""));
                        } else {
                            eventDao.insert(new EventEntity(model.getUuid(), model.getEventName(), model.getLocation(), ""));
                        }
                    }
                    mEventEntities = AppDatabase.getInstance(mContextWeakReference.get()).getEventDao().getAll();
                    break;
                case FETCH_IDENTIFIER:
                    mEventEntities = AppDatabase.getInstance(mContextWeakReference.get()).getEventDao().getAll();
                    break;
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (mListener != null) mListener.onTaskDone(mEventEntities);
    }
}
