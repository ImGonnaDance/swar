package com.com2us.module.push;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.com2us.peppermint.PeppermintConstant;

public class ShowMsgActivity extends Activity {
    private static final int REQUEST_TURN_ON_SCREEN = 1;
    private Dialog pushDialog = null;

    private class pushDialog extends Dialog {
        Context dialogContext;
        Intent dialogIntent;
        boolean isUseButton = false;

        public pushDialog(Context context, Intent intent, boolean existButton) {
            super(context);
            requestWindowFeature(ShowMsgActivity.REQUEST_TURN_ON_SCREEN);
            getWindow().setBackgroundDrawable(new ColorDrawable(0));
            this.dialogContext = context;
            this.dialogIntent = intent;
            this.isUseButton = existButton;
        }

        public boolean onTouchEvent(MotionEvent ev) {
            if (!this.isUseButton) {
                super.onTouchEvent(ev);
                if (!ShowMsgActivity.this.isOutOfBounds(this.dialogContext, findViewById(PushConfig.ResourceID(this.dialogContext, "pushdialog_root", PeppermintConstant.JSON_KEY_ID, this.dialogIntent.getStringExtra("packageName"))), ev)) {
                    ShowMsgActivity.this.launchActive(this.dialogContext, this.dialogIntent);
                }
                dismiss();
                ShowMsgActivity.this.finish();
            }
            return true;
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(REQUEST_TURN_ON_SCREEN);
        getWindow().addFlags(6815744);
        getWindow().setFlags(131072, 131072);
        if (getIntent().getBooleanExtra("bScreenLock", false)) {
            PushWakeLock.acquireCpuWakeLock(this);
        }
        setDialog(this, getIntent());
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        PushConfig.LogI("ShowMsgActivity onNewIntent-------");
        setIntent(intent);
        setDialog(this, intent);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PushConfig.LogI("ShowMsgActivity onActivityResult-------");
        switch (requestCode) {
            case REQUEST_TURN_ON_SCREEN /*1*/:
                PushConfig.LogI("REQUEST_TURN_ON_SCREEN finish");
                return;
            default:
                return;
        }
    }

    protected void onDestroy() {
        PushConfig.LogI("ShowMsgActivity onDestroy-------");
        if (this.pushDialog != null) {
            try {
                this.pushDialog.dismiss();
                this.pushDialog = null;
            } catch (Exception e) {
                PushConfig.LogI("onDestroy : " + e.toString());
            }
        }
        super.onDestroy();
    }

    private void setDialog(Context context, Intent intent) {
        if (this.pushDialog != null) {
            try {
                if (this.pushDialog.isShowing()) {
                    this.pushDialog.dismiss();
                }
            } catch (Exception e) {
                PushConfig.LogI("setDialog : " + e.toString());
            }
        }
        this.pushDialog = createDialog(context, intent);
        runOnUiThread(new Runnable() {
            public void run() {
                ShowMsgActivity.this.pushDialog.show();
            }
        });
    }

    private Dialog createDialog(final Context context, final Intent intent) {
        final Dialog dialog;
        Bitmap iconBitmap;
        if (PushConfig.ResourceID(context, "push_dialog", "layout", intent.getStringExtra("packageName")) != 0) {
            if (PushConfig.ResourceID(context, "pushdialog_positivebutton", PeppermintConstant.JSON_KEY_ID, intent.getStringExtra("packageName")) != 0) {
                dialog = new pushDialog(context, intent, true);
                dialog.setContentView(PushConfig.ResourceID(context, "push_dialog", "layout", intent.getStringExtra("packageName")));
                Button positiveButton = (Button) dialog.findViewById(PushConfig.ResourceID(context, "pushdialog_positivebutton", PeppermintConstant.JSON_KEY_ID, intent.getStringExtra("packageName")));
                if (positiveButton != null) {
                    positiveButton.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            ShowMsgActivity.this.launchActive(context, intent);
                            dialog.dismiss();
                            ShowMsgActivity.this.finish();
                        }
                    });
                }
                Button neutralButton = (Button) dialog.findViewById(PushConfig.ResourceID(context, "pushdialog_neutralbutton", PeppermintConstant.JSON_KEY_ID, intent.getStringExtra("packageName")));
                if (neutralButton != null) {
                    neutralButton.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            dialog.dismiss();
                            ShowMsgActivity.this.finish();
                        }
                    });
                }
                Button cancelButton = (Button) dialog.findViewById(PushConfig.ResourceID(context, "pushdialog_cancelbutton", PeppermintConstant.JSON_KEY_ID, intent.getStringExtra("packageName")));
                if (cancelButton != null) {
                    cancelButton.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            dialog.dismiss();
                            ShowMsgActivity.this.finish();
                        }
                    });
                }
            } else {
                dialog = new pushDialog(context, intent, false);
                dialog.setContentView(PushConfig.ResourceID(context, "push_dialog", "layout", intent.getStringExtra("packageName")));
            }
            TextView title = (TextView) dialog.findViewById(PushConfig.ResourceID(context, "pushdialog_title", PeppermintConstant.JSON_KEY_ID, intent.getStringExtra("packageName")));
            if (title != null) {
                title.setText(Html.fromHtml(intent.getExtras().getString("title")));
                title.setSelected(true);
            }
            ImageView icon = (ImageView) dialog.findViewById(PushConfig.ResourceID(context, "pushdialog_icon", PeppermintConstant.JSON_KEY_ID, intent.getStringExtra("packageName")));
            if (icon != null) {
                iconBitmap = PushConfig.getBitmapFromByteArray(intent.getByteArrayExtra("iconData"));
                if (iconBitmap != null) {
                    icon.setImageBitmap(iconBitmap);
                } else {
                    icon.setImageResource(intent.getIntExtra("iconResID", context.getApplicationInfo().icon));
                }
            }
            TextView msg = (TextView) dialog.findViewById(PushConfig.ResourceID(context, "pushdialog_msg", PeppermintConstant.JSON_KEY_ID, intent.getStringExtra("packageName")));
            if (msg != null) {
                msg.setText(Html.fromHtml(intent.getExtras().getString("msg")));
                msg.setSelected(true);
            }
        } else {
            Builder builder = new Builder(context);
            iconBitmap = PushConfig.getBitmapFromByteArray(intent.getByteArrayExtra("iconData"));
            if (iconBitmap != null) {
                builder.setIcon(new BitmapDrawable(context.getResources(), iconBitmap));
            } else {
                builder.setIcon(intent.getIntExtra("iconResID", getApplicationInfo().icon));
            }
            builder.setTitle(Html.fromHtml(intent.getExtras().getString("title")));
            builder.setMessage(Html.fromHtml(intent.getExtras().getString("msg")));
            builder.setInverseBackgroundForced(true);
            builder.setCancelable(true);
            builder.setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    ShowMsgActivity.this.launchActive(context, intent);
                    dialog.dismiss();
                    ShowMsgActivity.this.finish();
                }
            });
            builder.setNeutralButton(17039369, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    ShowMsgActivity.this.finish();
                }
            });
            builder.setOnCancelListener(new OnCancelListener() {
                public void onCancel(DialogInterface dialog) {
                    dialog.dismiss();
                    ShowMsgActivity.this.finish();
                }
            });
            dialog = builder.create();
        }
        dialog.setCancelable(true);
        dialog.setOnCancelListener(new OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
                ShowMsgActivity.this.finish();
            }
        });
        return dialog;
    }

    private void launchActive(final Context context, final Intent intent) {
        ((NotificationManager) context.getSystemService("notification")).cancelAll();
        PushConfig.setBadge(context, 0);
        MsgsOfNoticeID.clearAllMsgs(context);
        if (intent.getExtras().getBoolean("isGCMPush", false)) {
            new Thread(new Runnable() {
                public void run() {
                    PushConfig.sendToServer(PushConfig.strPostDataBuilder(context, intent.getExtras()));
                }
            }).start();
        }
        PushConfig.saveReceivedPush(context, intent.getExtras());
        String active = intent.getExtras().getString("active");
        if (TextUtils.isEmpty(active) || active.length() <= 3 || !active.subSequence(0, 3).equals("web")) {
            ComponentName compName = new ComponentName(intent.getExtras().getString("packageName"), intent.getExtras().getString("className"));
            Intent appIntent = new Intent("android.intent.action.MAIN");
            appIntent.addCategory("android.intent.category.LAUNCHER");
            appIntent.setComponent(compName);
            appIntent.putExtras(intent.getExtras());
            startActivity(appIntent);
            return;
        }
        Intent marketIntent = new Intent("android.intent.action.VIEW", Uri.parse(active.substring(4)));
        marketIntent.setFlags(268435456);
        try {
            startActivity(marketIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isOutOfBounds(Context context, View v, MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int slop = ViewConfiguration.get(context).getScaledWindowTouchSlop();
        return x < (-slop) || y < (-slop) || x > v.getWidth() + slop || y > v.getHeight() + slop;
    }
}
