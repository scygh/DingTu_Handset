package com.scy.dingtu_handset.mvp.ui.activity;

import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.scy.dingtu_handset.R;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class ZXingScanActivity extends AppCompatActivity {

    private QRCodeView mQRCodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxing_scan);
        mQRCodeView = (ZXingView) findViewById(R.id.zxingview);
        mQRCodeView.changeToScanQRCodeStyle(); //扫二维码
        mQRCodeView.setDelegate(new QRCodeView.Delegate() {

            @Override
            public void onScanQRCodeSuccess(String result) {
                Log.d("二维码扫描结果", "result:" + result);
                //扫描得到结果震动一下表示
                vibrate();
                Intent intent = new Intent();
                intent.putExtra("qrcode", result);
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void onScanQRCodeOpenCameraError() {
                Toast.makeText(ZXingScanActivity.this, "打开相机错误！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCameraAmbientBrightnessChanged(boolean isDark) {

            }
        });
        mQRCodeView.startSpot();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
        //强制手机摄像头镜头朝向前边
        //mQRCodeView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);
        mQRCodeView.showScanRect(); //显示扫描方框
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    //震动
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }
}
