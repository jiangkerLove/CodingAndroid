package jfp.study.hook.callback;


import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import jfp.study.hook.HookObj;

public class HookCallback implements Handler.Callback {
    @Override
    public boolean handleMessage(@NonNull Message msg) {
        Log.i(HookObj.TAG, "handleMessage: " + msg.toString());
        return false;
    }
}
