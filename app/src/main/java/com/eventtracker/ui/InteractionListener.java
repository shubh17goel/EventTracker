package com.eventtracker.ui;

import com.eventtracker.db.entity.EventEntity;

import java.util.List;

public interface InteractionListener<T> {
    void onTaskDone(List<T> eventEntities);
}
