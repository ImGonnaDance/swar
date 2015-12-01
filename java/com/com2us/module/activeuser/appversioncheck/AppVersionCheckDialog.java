package com.com2us.module.activeuser.appversioncheck;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.net.Uri;
import android.view.KeyEvent;

@Deprecated
public class AppVersionCheckDialog extends AlertDialog {
    private OnClickListener listener;
    private String url;

    public AppVersionCheckDialog(final Context context) {
        super(context);
        this.listener = new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (AppVersionCheckDialog.this.url == null || AppVersionCheckDialog.this.url.length() <= 0) {
                    AppVersionCheckDialog.this.dismiss();
                    return;
                }
                try {
                    context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(AppVersionCheckDialog.this.url)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
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

    public void setUrl(String url) {
        this.url = url;
    }

    public void setMessage(String message) {
        super.setMessage(message);
    }

    public void setPositiveButton(String msg) {
        setButton(-1, msg, this.listener);
    }

    public void setNegativeButton(String msg) {
        setButton(-2, msg, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                AppVersionCheckDialog.this.dismiss();
            }
        });
    }
}
