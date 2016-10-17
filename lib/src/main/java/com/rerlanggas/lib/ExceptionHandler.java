package com.rerlanggas.lib;

import android.content.Context;
import android.content.Intent;
import android.os.Process;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by Rian Erlangga on 29/09/2016. with awesomeness
 */

public class ExceptionHandler implements Thread.UncaughtExceptionHandler {
    private final Context myContext;
    Class<?> intentClass;

    /**
     * Constructor need context and class destination when force close
     *
     * @param context
     * @param intentClass
     */
    public ExceptionHandler(Context context, Class<?> intentClass) {
        myContext = context;
        this.intentClass = intentClass;
    }

    public static void init(Context context, Class<?> intentClass) {

        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(context, intentClass));
    }

    public void uncaughtException(Thread thread, Throwable exception) {
        StringWriter stackTrace = new StringWriter();
        exception.printStackTrace(new PrintWriter(stackTrace));
        System.err.println(stackTrace);

        Intent intent = new Intent(myContext, intentClass);
        intent.putExtra("exception", stackTrace.toString());
        myContext.startActivity(intent);

        Process.killProcess(Process.myPid());
        System.exit(10);
    }
}