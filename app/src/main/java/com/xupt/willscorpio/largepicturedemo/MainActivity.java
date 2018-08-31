package com.xupt.willscorpio.largepicturedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private LargePictureView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = findViewById(R.id.imageView);

        try {
            InputStream inputStream = getAssets().open("ok123.jpg");

            mImageView.setImageInputStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
