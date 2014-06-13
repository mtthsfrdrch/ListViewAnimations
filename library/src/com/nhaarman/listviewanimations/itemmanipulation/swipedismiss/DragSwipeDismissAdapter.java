package com.nhaarman.listviewanimations.itemmanipulation.swipedismiss;

import android.widget.AbsListView;
import android.widget.BaseAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.OnDismissCallback;
import com.nhaarman.listviewanimations.widget.DynamicListView;

public class DragSwipeDismissAdapter extends SwipeDismissAdapter {

    private final int dragHandleId;

    public DragSwipeDismissAdapter(BaseAdapter baseAdapter,
            OnDismissCallback onDismissCallback, int dragHandleId) {
        super(baseAdapter, onDismissCallback);
        this.dragHandleId = dragHandleId;
    }

    @Override protected SwipeDismissListViewTouchListener createListViewTouchListener(
            AbsListView listView) {
        return new DragSwipeDismissListViewTouchListener(listView, mOnDismissCallback, dragHandleId);
    }
}
