package com.wellbia.xigncode.util;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;

public class WBAlertDialog {
    public static void show(Context context, String str, String str2, String str3, OnClickListener onClickListener, String str4, OnClickListener onClickListener2, OnCancelListener onCancelListener) {
        if (context != null && !((Activity) context).isFinishing()) {
            Builder builder = new Builder(context);
            builder.setTitle(str);
            builder.setMessage(str2);
            builder.setPositiveButton(str3, onClickListener);
            builder.setNegativeButton(str4, onClickListener2);
            builder.setOnCancelListener(onCancelListener);
            builder.show();
        }
    }

    public static void show(Context context, int i, int i2, int i3, OnClickListener onClickListener, int i4, OnClickListener onClickListener2, OnCancelListener onCancelListener) {
        show(context, context.getString(i), context.getString(i2), context.getString(i3), onClickListener, context.getString(i4), onClickListener2, onCancelListener);
    }

    public static void show(Context context, String str, String str2, String str3, OnClickListener onClickListener, String str4, OnClickListener onClickListener2) {
        show(context, str, str2, str3, onClickListener, str4, onClickListener2, null);
    }

    public static void show(Context context, int i, int i2, int i3, OnClickListener onClickListener, int i4, OnClickListener onClickListener2) {
        show(context, context.getString(i), context.getString(i2), context.getString(i3), onClickListener, context.getString(i4), onClickListener2, null);
    }

    public static void show(Context context, String str, String str2, String str3) {
        show(context, str, str2, str3, null, null, null, null);
    }

    public static void show(Context context, int i, int i2, int i3) {
        show(context, context.getString(i), context.getString(i2), context.getString(i3), null, null, null, null);
    }

    public static void show(Context context, String str, String str2, String str3, OnCancelListener onCancelListener) {
        show(context, str, str2, str3, null, null, null, onCancelListener);
    }

    public static void show(Context context, int i, int i2, int i3, OnCancelListener onCancelListener) {
        show(context, context.getString(i), context.getString(i2), context.getString(i3), null, null, null, onCancelListener);
    }

    public static void show(Context context, String str, String str2, String str3, OnClickListener onClickListener) {
        show(context, str, str2, str3, onClickListener, null, null, null);
    }

    public static void show(Context context, int i, int i2, int i3, OnClickListener onClickListener) {
        show(context, context.getString(i), context.getString(i2), context.getString(i3), onClickListener, null, null, null);
    }

    public static void show(Context context, String str, String str2, String str3, OnClickListener onClickListener, OnCancelListener onCancelListener) {
        show(context, str, str2, str3, onClickListener, null, null, onCancelListener);
    }

    public static void show(Context context, int i, int i2, int i3, OnClickListener onClickListener, OnCancelListener onCancelListener) {
        show(context, context.getString(i), context.getString(i2), context.getString(i3), onClickListener, null, null, onCancelListener);
    }
}
