package com.scy.dingtu_handset.app.utils.card;

import android.device.PrinterManager;

public class PrintUtils {

    private PrinterManager printerManager = new PrinterManager();

    public void init(){
        printerManager.open();
    }
}
