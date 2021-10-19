package jfp.study.hook.instrumentation;

import android.app.Activity;
import android.app.Instrumentation;
import android.app.Instrumentation.ActivityResult;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Button;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import jfp.study.hook.HookObj;

public class HookInstrumentation extends Instrumentation {

    private Instrumentation instrumentation;

    public HookInstrumentation(Instrumentation instrumentation) {
        this.instrumentation = instrumentation;
    }

    public ActivityResult execStartActivity(
            Context who, IBinder contextThread, IBinder token, Activity target,
            Intent intent, int requestCode, Bundle options) {
        Log.i(HookObj.TAG, "target: " + target +",intent: " + intent.getComponent());
        Class<? extends Instrumentation> clazz = instrumentation.getClass();
        try {
            Method method = clazz.getDeclaredMethod("execStartActivity",
                    Context.class,
                    IBinder.class,
                    IBinder.class,
                    Activity.class,
                    Intent.class,
                    int.class,
                    Bundle.class);
            return (ActivityResult) method.invoke(
                    instrumentation,
                    who,
                    contextThread,
                    token,
                    target,
                    intent,
                    requestCode,
                    options);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }


}
