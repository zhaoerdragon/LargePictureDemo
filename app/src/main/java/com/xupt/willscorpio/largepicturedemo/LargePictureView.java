package com.xupt.willscorpio.largepicturedemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class LargePictureView extends View {

    private Context mContext;

    private Rect mRect;
    private int mImageWidth;
    private int mImageHeight;

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

    private void initView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        mContext = context;
    }


}
