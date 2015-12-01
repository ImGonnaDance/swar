package com.com2us.module.newsbanner2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;

public class NewsBannerDialog extends AlertDialog {
    private String url;

    public NewsBannerDialog(Context context, String url) {
        super(context);
        this.url = url;
        setCancelable(false);
        setOnKeyListener(new OnKeyListener() {
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == 84) {
                    return true;
                }
                return false;
            }
        });
    }

    public void setText(String msg, String msg_ok, String msg_cancel, OnClickListener listener) {
        super.setMessage(msg);
        setButton(-1, msg_ok, listener);
        setButton(-2, msg_cancel, listener);
    }
}
