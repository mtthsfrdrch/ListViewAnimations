package com.nhaarman.listviewanimations.itemmanipulation.swipedismiss;

import android.view.View;

public interface SwipeProgressListener {

    void onSwipeProgress(View swipedView);

    void onSwipeEnded(View swipedView);
}
