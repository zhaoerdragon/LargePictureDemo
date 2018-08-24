package com.xupt.willscorpio.largepicturedemo;

import android.content.Context;
import android.graphics.PointF;
import android.view.MotionEvent;

public class MoveGestureDetector extends BaseGestureDetector {

    private OnMoveGestureListener mListener;

    private PointF mCurrentPointer;
    private PointF mPrePointer;

    private PointF mDeltaPointer = new PointF();
    private PointF mExtenalPointer = new PointF();

    public MoveGestureDetector(Context context, OnMoveGestureListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    protected void handleInProgressEvent(MotionEvent event) {
        int actionCode = event.getAction() & MotionEvent.ACTION_MASK;
        switch (actionCode) {
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP: {
                mListener.onMoveEnd(this);
                resetState();
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                updateStateByEvent(event);
                boolean update = mListener.onMove(this);
                if (update) {
                    mPreMotionEvent.recycle();
                    mPreMotionEvent = MotionEvent.obtain(event);
                }
                break;
            }
        }
    }

    @Override
    protected void handleStartProgressEvent(MotionEvent event) {
        int actionCode = event.getAction() & MotionEvent.ACTION_MASK;
        switch (actionCode){
            case MotionEvent.ACTION_DOWN:{
                resetState();
                mPreMotionEvent = MotionEvent.obtain(event);
                updateStateByEvent(event);
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                mGestureInProgress = mListener.onMoveBegin(this);
                break;
            }
        }
    }

    @Override
    protected void updateStateByEvent(MotionEvent event) {
        final MotionEvent prev = mPreMotionEvent;

        mPrePointer = caculateFocalPointer(event);
        mCurrentPointer = caculateFocalPointer(event);

        boolean mSkipThisMoveEvent = prev.getPointerCount() != event.getPointerCount();

        mExtenalPointer.x = mSkipThisMoveEvent ? 0 : mCurrentPointer.x - mPrePointer.x;
        mExtenalPointer.y = mSkipThisMoveEvent ? 0 : mCurrentPointer.y - mPrePointer.y;
    }

    private PointF caculateFocalPointer(MotionEvent event) {
        final int count = event.getPointerCount();
        float x = 0, y = 0;
        for (int i = 0; i < count; i++) {
            x = x + event.getX(i);
            y = y + event.getY(i);
        }
        x = x / count;
        y = y / count;
        return new PointF(x, y);
    }

    public float getMoveX(){
        return mExtenalPointer.x;
    }

    public float getMoveY(){
        return mExtenalPointer.y;
    }

    /**
     * 滑动事件的接口
     */
    public interface OnMoveGestureListener {

        public boolean onMoveBegin(MoveGestureDetector detector);

        public boolean onMove(MoveGestureDetector detector);

        public void onMoveEnd(MoveGestureDetector detector);
    }

    /**
     * 简单实现类，复写时可以不用重写所有接口方法，只重写一部分
     */
    public static class SimpleMoveGestureDetector implements OnMoveGestureListener{

        @Override
        public boolean onMoveBegin(MoveGestureDetector detector) {
            return true;
        }

        @Override
        public boolean onMove(MoveGestureDetector detector) {
            return false;
        }

        @Override
        public void onMoveEnd(MoveGestureDetector detector) {

        }
    }
}
