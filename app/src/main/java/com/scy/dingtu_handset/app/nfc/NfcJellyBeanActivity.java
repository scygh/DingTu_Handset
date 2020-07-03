package com.scy.dingtu_handset.app.nfc;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;

import com.aitangba.swipeback.SwipeBackHelper;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.mvp.IPresenter;
import com.scy.dingtu_handset.app.utils.AntiShake;

public abstract class NfcJellyBeanActivity<P extends IPresenter> extends BaseActivity<P> implements  SwipeBackHelper.SlideBackManager {
    private NfcAdapter mNfcAdapter;
    private PendingIntent pendingIntent;

    private SwipeBackHelper mSwipeBackHelper;
    protected AntiShake util = new AntiShake();

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        pendingIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
    }

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
        Log.d(TAG, "onResume: ");
        super.onResume();
        String[][] techListsArray = new String[][] { new String[] { NfcF.class.getName() },
                new String[] {NfcA.class.getName()} };
        if (mNfcAdapter != null)
        mNfcAdapter.enableForegroundDispatch(this, pendingIntent, null, techListsArray);
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: ");
        super.onPause();
        if (mNfcAdapter != null)
        mNfcAdapter.disableForegroundDispatch(this);
    }

}
