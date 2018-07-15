package com.tuanlvt.flashlight;

import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.button_power)
    private FrameLayout mButtonPower;
    private boolean mIsFlash;
    private Camera mCamera;
    private Camera.Parameters mParameter;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mButtonPower.setOnClickListener(this);
        initFlash();
    }

    private void initFlash() {
        mHandler = new Handler();
        mCamera = Camera.open();
        mParameter = mCamera.getParameters();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_power:
                onCheckedFlash();
        }
    }

    private void onCheckedFlash() {
        if (!mIsFlash) {
            mParameter.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
            mCamera.setParameters(mParameter);
            mCamera.startPreview();
            onCheckBackgroundButton();
        } else {
            mParameter.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            mCamera.setParameters(mParameter);
            mCamera.stopPreview();
            mCamera.release();
        }
        mIsFlash = !mIsFlash;
    }

    private void onCheckBackgroundButton() {

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!mIsFlash) {
                    mButtonPower.setBackgroundResource(R.drawable.ic_button_green);
                } else {
                    mButtonPower.setBackgroundResource(R.drawable.ic_button_grey);
                }
            }
        }, 1000);
    }
}
