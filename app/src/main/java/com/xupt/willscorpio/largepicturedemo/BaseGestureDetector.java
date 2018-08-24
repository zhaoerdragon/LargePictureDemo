package com.xupt.willscorpio.largepicturedemo;

import android.content.Context;
import android.view.MotionEvent;

public abstract class BaseGestureDetector {

    protected Context mContext;

    protected boolean mGestureInProgress;

    protected MotionEvent mPreMotionEvent;
    protected MotionEvent mCurrentMotionEvent;

    public BaseGestureDetector(Context context) {
        mContext = context;
    }

    protected abstract void handleInProgressEvent(MotionEvent event);

    protected abstract void handleStartProgressEvent(MotionEvent event);

    protected abstract void updateStateByEvent(MotionEvent event);

    public boolean onTouchEvent(MotionEvent event) {
        if (!mGestureInProgress) {
            handleStartProgressEvent(event);
        }else {
            handleInProgressEvent(event);
        }

        return true;
    }

    protected void resetState() {
        if (mPreMotionEvent != null) {
            mPreMotionEvent.recycle();
            mPreMotionEvent = null;
        }
        if (mCurrentMotionEvent != null) {
            mCurrentMotionEvent.recycle();
            mCurrentMotionEvent = null;
        }

        mGestureInProgress = false;
    }
}
