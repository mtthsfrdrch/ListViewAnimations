package com.nhaarman.listviewanimations.itemmanipulation.swipedismiss;

import android.view.View;

public interface SwipeAnimationListener {

    void onSwipeProgress(View swipedView);

    void onSwipeEnded();
}
