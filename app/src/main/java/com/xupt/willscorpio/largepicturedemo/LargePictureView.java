package com.xupt.willscorpio.largepicturedemo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class LargePictureView extends View {

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

    }
}
