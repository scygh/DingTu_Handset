package com.scy.dingtu_handset.app.listening;

public interface RFCardListening {
    void findRFCardListening(String s);

    void readRFCardListening(String data);

    void noFindRFCardListening();

    void failReadRFCardListening();
}
