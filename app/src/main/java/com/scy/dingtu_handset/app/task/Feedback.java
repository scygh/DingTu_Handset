package com.scy.dingtu_handset.app.task;


public interface Feedback {
    public boolean isAvailable();

    public void start(CharSequence text);

    public void cancel(CharSequence text);

    public void success(CharSequence text);

    public void failed(CharSequence text);

    public void update(Object arg0);

    public void setIndeterminate(boolean indeterminate);
}
