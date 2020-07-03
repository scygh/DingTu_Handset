package com.scy.dingtu_handset.app.nfc;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.MotionEvent;

import com.aitangba.swipeback.SwipeBackHelper;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.mvp.IPresenter;
import com.scy.dingtu_handset.app.utils.AntiShake;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public abstract class NfcKikKatActivity<P extends IPresenter> extends BaseActivity<P> implements  SwipeBackHelper.SlideBackManager,NfcAdapter.ReaderCallback{

    private static final int READER_FLAGS = NfcAdapter.FLAG_READER_NFC_A
            | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK | NfcAdapter.FLAG_READER_NO_PLATFORM_SOUNDS;

    private SwipeBackHelper mSwipeBackHelper;
    protected AntiShake util = new AntiShake();

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mSwipeBackHelper == null) {
            mSwipeBackHelper = new SwipeBackHelper(this);
        }
        return mSwipeBackHelper.processTouchEvent(ev) || super.dispatchTouchEvent(ev);
    }

    @Override
    public Activity getSlideActivity() {
        return this;
    }

    @Override
    public boolean supportSlideBack() {
        return true;
    }

    @Override
    public boolean canBeSlideBack() {
        return true;
    }

    @Override
    public void finish() {
        if (mSwipeBackHelper != null) {
            mSwipeBackHelper.finishSwipeImmediately();
            mSwipeBackHelper = null;
        }
        super.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        enableReaderMode();
    }

    @Override
    protected void onPause() {
        super.onPause();
        disableReaderMode();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    private void enableReaderMode() {
        Log.i(TAG, "Enabling reader mode");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            NfcAdapter nfc = NfcAdapter.getDefaultAdapter(this);
            if (nfc != null) {
                nfc.enableReaderMode(this, this, READER_FLAGS, null);
            }
        }

    }

    private void disableReaderMode() {
        Log.i(TAG, "Disabling reader mode");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            NfcAdapter nfc = NfcAdapter.getDefaultAdapter(this);
            if (nfc != null) {
                nfc.disableReaderMode(this);
            }
        }
    }
}

