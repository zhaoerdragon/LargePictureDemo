package com.xupt.willscorpio.largepicturedemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.io.InputStream;

public class LargePictureView extends View {

    private Context mContext;

    private int mImageWidth;
    private int mImageHeight;

    private BitmapRegionDecoder mDecoder;
    private volatile Rect mRect = new Rect();
    private MoveGestureDetector mDetector;

    private static final BitmapFactory.Options mOptions = new BitmapFactory.Options();

    static {
        //对图片的属性进行初始化
        mOptions.inPreferredConfig = Bitmap.Config.RGB_565;
    }

    public LargePictureView(Context context) {
        super(context);

        initView(context, null, 0);
    }

    public LargePictureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initView(context, attrs, 0);
    }

    public LargePictureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, @Nullable AttributeSet attrs, final int defStyleAttr) {
        mContext = context;
        mDetector = new MoveGestureDetector(mContext, new MoveGestureDetector
                .SimpleMoveGestureDetector(){
            @Override
            public boolean onMove(MoveGestureDetector detector) {
                //屏幕是没法移动的，改变的是下面的图片，所以值取相反数
                int moveX = (int) detector.getMoveX();
                int moveY = (int) detector.getMoveY();
                if (mImageWidth > getWidth()) {
                    mRect.offset(-moveX, 0);
                    checkWidth();
                    invalidate();
                }
                if (mImageHeight > getHeight()) {
                    mRect.offset(0, -moveY);
                    checkHeight();
                    invalidate();
                }
                return true;
            }
        });

    }

    private void checkWidth(){
        Rect rect = mRect;
        int imageWidth = mImageWidth;
        int imageHeight = mImageHeight;

        if (rect.right > imageWidth) {
            rect.right = imageWidth;
            rect.left = imageWidth - getWidth();
        }
        if (rect.left < 0) {
            rect.left = 0;
            rect.right = getWidth();
        }
    }

    private void checkHeight(){
        Rect rect = mRect;
        int imageWidth = mImageWidth;
        int imageHeight = mImageHeight;

        if (rect.bottom > imageHeight) {
            rect.bottom = imageHeight;
            rect.top = imageHeight - getHeight();
        }
        if (rect.top < 0) {
            rect.top = 0;
            rect.bottom = getHeight();
        }
    }

    /**
     * 每次重新图片流的时候，对整个控件进行重新绘制
     * @param inputStream
     */
    public void setImageInputStream(InputStream inputStream) {
        try {
            mDecoder = BitmapRegionDecoder.newInstance(inputStream, false);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            inputStream.reset();
            BitmapFactory.decodeStream(inputStream, null, options);
            mImageWidth = options.outWidth;
            mImageHeight = options.outHeight;

            requestLayout();
            invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //在最后，将文件流关闭
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Bitmap bitmap = mDecoder.decodeRegion(mRect, mOptions);
        canvas.drawBitmap(bitmap, 0, 0, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        int imageWidth = mImageWidth;
        int imageHeight = mImageHeight;

        mRect.left = imageWidth / 2 - width / 2;
        mRect.top = imageHeight / 2 - height / 2;
        mRect.right = mRect.right + width;
        mRect.bottom = mRect.top + height;
    }
}
