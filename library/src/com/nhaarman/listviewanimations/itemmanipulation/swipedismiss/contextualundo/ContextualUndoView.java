/*
 * Copyright 2013 Frankie Sardo
 * Copyright 2013 Niek Haarman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.contextualundo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.view.ViewHelper;

@SuppressLint("ViewConstructor")
public class ContextualUndoView extends FrameLayout {

    private View mUndoView;
    private View mContentView;
    private int mUndoColor;
    private TextView mCountDownTV;

    private long mItemId;

    public ContextualUndoView(final Context context, final int undoLayoutResId, int undoColorId, final int countDownTextViewResId) {
        super(context);
        initUndo(undoLayoutResId, countDownTextViewResId, undoColorId);
    }

    private void initUndo(final int undoLayoutResId, final int countDownTextViewResId, int undoColorId) {
        mUndoView = View.inflate(getContext(), undoLayoutResId, null);
        addView(mUndoView);

        if (countDownTextViewResId != -1) {
            mCountDownTV = (TextView) mUndoView.findViewById(countDownTextViewResId);
        }
        mUndoColor = getResources().getColor(undoColorId);
    }

    public void updateCountDownTimer(final String timerText) {
        if (mCountDownTV != null) {
            mCountDownTV.setText(timerText);
        }
    }

    public void updateContentView(final View contentView) {
        if (mContentView == null) {
            addView(contentView);
        }
        mContentView = contentView;
    }

    public View getContentView() {
        return mContentView;
    }

    public void setItemId(final long itemId) {
        this.mItemId = itemId;
    }

    public long getItemId() {
        return mItemId;
    }

    public boolean isContentDisplayed() {
        return mContentView.getVisibility() == View.VISIBLE;
    }

    public void displayUndo() {
        updateCountDownTimer("");
        mContentView.setVisibility(View.INVISIBLE);
        setBackgroundColor(mUndoColor);
        mUndoView.setVisibility(View.VISIBLE);
        ViewHelper.setAlpha(mUndoView, 0.0f);
        com.nineoldandroids.view.ViewPropertyAnimator.animate(mUndoView).alpha(1.0f).setListener(new AnimatorListenerAdapter() {

            @Override public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                setBackgroundColor(getResources().getColor(android.R.color.transparent));
            }
        });
    }

    public void displayContentView() {
        mContentView.setVisibility(View.VISIBLE);
        mUndoView.setVisibility(View.INVISIBLE);
    }
}