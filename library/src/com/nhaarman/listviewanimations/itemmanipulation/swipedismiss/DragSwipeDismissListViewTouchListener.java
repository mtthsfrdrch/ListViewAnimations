package com.nhaarman.listviewanimations.itemmanipulation.swipedismiss;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import com.nhaarman.listviewanimations.itemmanipulation.OnDismissCallback;
import com.nhaarman.listviewanimations.widget.DynamicListView;

public class DragSwipeDismissListViewTouchListener extends SwipeDismissListViewTouchListener
        implements GestureDetector.OnGestureListener {


    private int[] mTempLoc = new int[2];
    private int mDragHandleId;
    public static final int MISS = -1;
    private int mItemX;
    private int mItemY;
    private boolean mDragging = false;
    private boolean mCanDrag;
    private int mHitPos = MISS;
    private boolean dragOnHandle = true;
    private int mTouchSlop;
    private int mFlingHitPos = MISS;
    private GestureDetector mDetector;


    /**
     * Constructs a new swipe-to-dismiss touch listener for the given list view.
     *
     * @param listView The list view whose items should be dismissable.
     * @param callback The callback to trigger when the user has indicated that she
     */
    public DragSwipeDismissListViewTouchListener(DynamicListView listView,
            OnDismissCallback callback, int dragHandleId) {
        super(listView, callback, null);
        mDragHandleId = dragHandleId;
        mDetector = new GestureDetector(listView.getContext(), this);
    }


    /**
     * Checks for the touch of an item's drag handle (specified by
     * {@link #setDragHandleId(int)}), and returns that item's position
     * if a drag handle touch was detected.
     *
     * @param ev The ACTION_DOWN MotionEvent.
     * @return The list position of the item whose drag handle was
     * touched; MISS if unsuccessful.
     */
    public int dragHandleHitPosition(MotionEvent ev) {
        return viewIdHitPosition(ev, mDragHandleId);
    }



    public int viewIdHitPosition(MotionEvent ev, int id) {
        final int x = (int) ev.getX();
        final int y = (int) ev.getY();

        int touchPos = mListView.pointToPosition(x, y); // includes headers/footers

        final int numHeaders = ((ListView) mListView).getHeaderViewsCount();
        final int numFooters = ((ListView) mListView).getFooterViewsCount();
        final int count = mListView.getCount();

        // Log.d("mobeta", "touch down on position " + itemnum);
        // We're only interested if the touch was on an
        // item that's not a header or footer.
        if (touchPos != AdapterView.INVALID_POSITION && touchPos >= numHeaders
                && touchPos < (count - numFooters)) {
            final View item = mListView.getChildAt(touchPos - mListView.getFirstVisiblePosition());
            final int rawX = (int) ev.getRawX();
            final int rawY = (int) ev.getRawY();

            View dragBox = id == 0 ? item : (View) item.findViewById(id);
            if (dragBox != null) {
                dragBox.getLocationOnScreen(mTempLoc);

                if (rawX > mTempLoc[0] && rawY > mTempLoc[1] &&
                        rawX < mTempLoc[0] + dragBox.getWidth() &&
                        rawY < mTempLoc[1] + dragBox.getHeight()) {

                    mItemX = item.getLeft();
                    mItemY = item.getTop();

                    return touchPos;
                }
            }
        }

        return MISS;
    }

    /**
     * Set the resource id for the View that represents the drag
     * handle in a list item.
     *
     * @param id An android resource id.
     */
    public void setDragHandleId(int id) {
        mDragHandleId = id;
    }

    @Override public boolean onDown(MotionEvent e) {
        System.out.println("DragTouchListener, onDown");

//        if (mRemoveEnabled && mRemoveMode == CLICK_REMOVE) {
//            mClickRemoveHitPos = viewIdHitPosition(ev, mClickRemoveId);
//        }

        mHitPos = dragHandleHitPosition(e);
        if (mHitPos != MISS) {
//            startDrag(mHitPos, (int) ev.getX() - mItemX, (int) ev.getY() - mItemY);
            ((DynamicListView)mListView).startDragging();
            mCanDrag = true;
            return true;
        }

//        mIsRemoving = false;

//        mPositionX = 0;
//        mFlingHitPos = startFlingPosition(ev);

        return false;
    }

    @Override public void onShowPress(MotionEvent e) {

    }

    @Override public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
            float distanceY) {
        System.out.println("DragTouchListener, onScroll");
        final int x1 = (int) e1.getX();
        final int y1 = (int) e1.getY();
        final int x2 = (int) e2.getX();
        final int y2 = (int) e2.getY();
        final int deltaX = x2 - mItemX;
        final int deltaY = y2 - mItemY;

        if (mCanDrag && !mDragging && (mHitPos != MISS || mFlingHitPos != MISS)) {
            if (mHitPos != MISS) {
                if (dragOnHandle && Math.abs(y2 - y1) > mTouchSlop) {
//                    startDrag(mHitPos, deltaX, deltaY);
                    ((DynamicListView)mListView).startDragging();

                }
//                else if (mDragInitMode != ON_DOWN && Math.abs(x2 - x1) > mTouchSlop
//                        && mRemoveEnabled) {
//                    mIsRemoving = true;
//                    startDrag(mFlingHitPos, deltaX, deltaY);
//                }
            }
//            else if (mFlingHitPos != MISS) {
//                if (Math.abs(x2 - x1) > mTouchSlop && mRemoveEnabled) {
//                    mIsRemoving = true;
//                    startDrag(mFlingHitPos, deltaX, deltaY);
//                } else if (Math.abs(y2 - y1) > mTouchSlop) {
//                    mCanDrag = false; // if started to scroll the list then
//                    // don't allow sorting nor fling-removing
//                }
//            }
        }
        // return whatever
        return false;
    }

    @Override public void onLongPress(MotionEvent e) {

    }

    @Override public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
            float velocityY) {
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent ev) {
//        if (!mDslv.isDragEnabled() || mDslv.listViewIntercepted()) {
//            return false;
//        }
        System.out.println("DragTouchListener, onTouch");

//        if (mRemoveEnabled && mDragging && mRemoveMode == FLING_REMOVE) {
//            mFlingRemoveDetector.onTouchEvent(ev);
//        }
//
//        int action = ev.getAction() & MotionEvent.ACTION_MASK;
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                mCurrX = (int) ev.getX();
//                mCurrY = (int) ev.getY();
//                break;
//            case MotionEvent.ACTION_UP:
//                if (mRemoveEnabled && mIsRemoving) {
//                    int x = mPositionX >= 0 ? mPositionX : -mPositionX;
//                    int removePoint = mDslv.getWidth() / 2;
//                    if (x > removePoint) {
//                        mDslv.stopDragWithVelocity(true, 0);
//                    }
//                }
//            case MotionEvent.ACTION_CANCEL:
//                mIsRemoving = false;
//                mDragging = false;
//                break;
//        }
        boolean consumed = mDetector.onTouchEvent(ev);
        System.out.println("DragTouchListener, detector consumed event: " + mDetector.onTouchEvent(ev));

        return consumed || super.onTouch(v, ev);
    }
}


