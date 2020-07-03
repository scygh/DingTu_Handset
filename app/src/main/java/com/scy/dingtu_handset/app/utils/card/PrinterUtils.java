package com.scy.dingtu_handset.app.utils.card;

import android.app.Activity;
import android.content.Context;
import android.device.PrinterManager;

import com.scy.dingtu_handset.app.api.AppConstant;
import com.scy.dingtu_handset.app.utils.SpUtils;

public class PrinterUtils {
    private Context activity;
    private PrinterManager printerManager;

    private static volatile PrinterUtils instance;
    private PrinterUtils(Activity activity) {
        super();
        this.activity = activity.getApplicationContext();
        init();
    }

    private void init() {
        printerManager = new PrinterManager();
    }

    public static PrinterUtils getInstance(Activity activity) {
        if (instance == null) {
            synchronized (PrinterUtils.class) {
                if (instance == null) {
                    instance = new PrinterUtils(activity);
                }
            }
        }
        return instance;
    }

    public void printLianxi(StringBuilder printer) {
        String Spname = (String) SpUtils.get(activity, AppConstant.Receipt.NAME,"杭州鼎图信息");
        String Spaddress = (String) SpUtils.get(activity, AppConstant.Receipt.ADDRESS,"");
        String Spphone = (String) SpUtils.get(activity, AppConstant.Receipt.PHONE,"");

        printerManager.clearPage();
        printerManager.setupPage(-1,600);
        printerManager.drawText(Spname,38,0,"arial",50,false,false,0);
        printerManager.drawText("",0,50,"simsun",24,false,false,0);
        printerManager.drawText("------------------------------",0,55,"simsun",24,true,false,0);
        printerManager.drawText(printer.toString(),0,60,"simsun",24,false,false,0);
        printerManager.drawText("------------------------------",0,400,"simsun",24,true,false,0);
        printerManager.drawText("地址："+Spaddress,0,425,"simsun",24,false,false,0);
        printerManager.drawText("电话："+Spphone,0,450,"simsun",24,false,false,0);
        printerManager.printPage(0);
    }

    public void close() {
        printerManager.close();
    }
}
