package com.com2us.module.push;

import android.app.Notification;
import android.app.Notification.BigPictureStyle;
import android.app.Notification.BigTextStyle;
import android.app.Notification.Builder;
import android.app.Notification.InboxStyle;
import android.app.Notification.Style;
import android.app.Notification.WearableExtender;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;
import com.com2us.peppermint.PeppermintConstant;
import com.google.android.gcm.GCMConstants;
import java.text.DecimalFormat;
import java.util.Calendar;
import jp.co.cyberz.fox.a.a.i;
import jp.co.dimage.android.o;

public class NotificationRegister {

    class AnonymousClass1 implements Runnable {
        private final /* synthetic */ Context val$context;
        private final /* synthetic */ Intent val$intent;

        AnonymousClass1(Context context, Intent intent) {
            this.val$context = context;
            this.val$intent = intent;
        }

        public void run() {
            Toast toast = Toast.makeText(this.val$context, Html.fromHtml(this.val$intent.getExtras().getString("msg")), 1);
            if (PushConfig.ResourceID(this.val$context, "push_toast", "layout", this.val$context.getPackageName()) != 0) {
                View view = View.inflate(this.val$context, PushConfig.ResourceID(this.val$context, "push_toast", "layout", this.val$context.getPackageName()), null);
                TextView title = (TextView) view.findViewById(PushConfig.ResourceID(this.val$context, "pushtoast_title", PeppermintConstant.JSON_KEY_ID, this.val$context.getPackageName()));
                if (title != null) {
                    title.setText(Html.fromHtml(this.val$intent.getExtras().getString("title")));
                }
                ImageView icon = (ImageView) view.findViewById(PushConfig.ResourceID(this.val$context, "pushtoast_icon", PeppermintConstant.JSON_KEY_ID, this.val$context.getPackageName()));
                if (icon != null) {
                    Bitmap iconBitmap = PushConfig.getBitmapFromByteArray(this.val$intent.getByteArrayExtra("iconData"));
                    if (iconBitmap != null) {
                        icon.setImageBitmap(iconBitmap);
                    } else {
                        icon.setImageResource(this.val$intent.getIntExtra("iconResID", this.val$context.getApplicationInfo().icon));
                    }
                }
                TextView msg = (TextView) view.findViewById(PushConfig.ResourceID(this.val$context, "pushtoast_msg", PeppermintConstant.JSON_KEY_ID, this.val$context.getPackageName()));
                if (msg != null) {
                    msg.setText(Html.fromHtml(this.val$intent.getExtras().getString("msg")));
                }
                toast.setView(view);
            }
            toast.show();
        }
    }

    protected static synchronized void setNotification(Context context, Intent intent) {
        Exception e;
        Notification notification;
        TaskStackBuilder stackBuilder;
        boolean isBigPictureStyle;
        String newMsg;
        int length;
        int i;
        Style inboxStyle;
        int z;
        synchronized (NotificationRegister.class) {
            String str;
            String[] newSeqArray;
            NotificationManager nm = (NotificationManager) context.getSystemService("notification");
            Intent intent2 = new Intent(context, NotificationMessage.class);
            intent2.putExtras(intent);
            String str2 = "active";
            if (TextUtils.isEmpty(intent.getExtras().getString("active"))) {
                str = GCMConstants.EXTRA_APPLICATION_PENDING_INTENT;
            } else {
                str = intent.getExtras().getString("active");
            }
            intent2.putExtra(str2, str);
            intent2.putExtra("packageName", context.getPackageName());
            intent2.putExtra("className", intent.getExtras().getString("className"));
            if (!intent2.getExtras().getString("active").equals(GCMConstants.EXTRA_APPLICATION_PENDING_INTENT)) {
                intent2.setFlags(276824064);
            }
            Bitmap iconBitmap = PushConfig.getBitmapFromByteArray(intent.getByteArrayExtra("iconData"));
            int iconResID = intent.getIntExtra("iconResID", context.getApplicationInfo().icon);
            if (iconBitmap == null) {
                iconBitmap = BitmapFactory.decodeResource(context.getResources(), iconResID);
            }
            PendingIntent pendingIntent;
            if (VERSION.SDK_INT >= 11) {
                byte[] bigpictureData;
                int bucketsize;
                CharSequence[] msgsArray;
                int y;
                int i2;
                StringBuffer buf;
                BigTextStyle bigTextStyle;
                PushConfig.LogI("Notification in Android 3.0.X and later version");
                if (!intent2.getExtras().getString("active").equals(GCMConstants.EXTRA_APPLICATION_PENDING_INTENT)) {
                    intent2.setFlags(276856832);
                }
                pendingIntent = PendingIntent.getActivity(context, Integer.parseInt(intent.getExtras().getString("noticeID")), intent2, 134217728);
                Builder builder = new Builder(context);
                builder.setContentTitle(Html.fromHtml(intent.getExtras().getString("title")));
                builder.setContentText(Html.fromHtml(intent.getExtras().getString("msg")));
                builder.setTicker(Html.fromHtml(intent.getExtras().getString("ticker")));
                builder.setSmallIcon(intent.getExtras().getInt("smallIconResID"));
                builder.setAutoCancel(true);
                builder.setContentIntent(pendingIntent);
                if (PushConfig.ResourceID(context, "push_notification", "layout", intent.getStringExtra("packageName")) != 0) {
                    try {
                        PushConfig.LogI("Custom WidgetStyle");
                        Calendar calendar = Calendar.getInstance();
                        int month = calendar.get(2);
                        int day = calendar.get(5);
                        int hr24 = calendar.get(11);
                        int min = calendar.get(12);
                        DecimalFormat decimalFormat = new DecimalFormat("00");
                        StringBuffer buffer = new StringBuffer();
                        buffer.append(decimalFormat.format((long) (month + 1))).append(". ").append(decimalFormat.format((long) day)).append(". ").append(decimalFormat.format((long) hr24)).append(":").append(decimalFormat.format((long) min));
                        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), PushConfig.ResourceID(context, "push_notification", "layout", intent.getStringExtra("packageName")));
                        RemoteViews remoteViews2;
                        try {
                            remoteViews.setImageViewBitmap(PushConfig.ResourceID(context, "pushnotification_icon", PeppermintConstant.JSON_KEY_ID, intent.getStringExtra("packageName")), iconBitmap);
                            remoteViews.setImageViewBitmap(PushConfig.ResourceID(context, "pushnotification_smallicon", PeppermintConstant.JSON_KEY_ID, intent.getStringExtra("packageName")), BitmapFactory.decodeResource(context.getResources(), intent.getExtras().getInt("smallIconResID")));
                            remoteViews.setTextViewText(PushConfig.ResourceID(context, "pushnotification_title", PeppermintConstant.JSON_KEY_ID, intent.getStringExtra("packageName")), Html.fromHtml(intent.getExtras().getString("title")));
                            remoteViews.setTextViewText(PushConfig.ResourceID(context, "pushnotification_msg", PeppermintConstant.JSON_KEY_ID, intent.getStringExtra("packageName")), Html.fromHtml(intent.getExtras().getString("msg")));
                            remoteViews.setTextViewText(PushConfig.ResourceID(context, "pushnotification_bigmsg", PeppermintConstant.JSON_KEY_ID, intent.getStringExtra("packageName")), Html.fromHtml(intent.getExtras().getString("bigmsg")));
                            remoteViews.setTextViewText(PushConfig.ResourceID(context, "pushnotification_timestamp", PeppermintConstant.JSON_KEY_ID, intent.getStringExtra("packageName")), buffer.toString());
                            if (VERSION.SDK_INT < 21) {
                                builder.setContent(remoteViews);
                                remoteViews2 = remoteViews;
                            } else {
                                remoteViews2 = remoteViews;
                            }
                        } catch (Exception e2) {
                            e = e2;
                            remoteViews2 = remoteViews;
                            PushConfig.LogI("Custom WidgetStyle Exception : " + e.toString());
                            if (VERSION.SDK_INT >= 16) {
                                notification = builder.getNotification();
                            } else {
                                PushConfig.LogI("Notification in Android 4.1 and later version");
                                stackBuilder = TaskStackBuilder.create(context);
                                stackBuilder.addParentStack(NotificationMessage.class);
                                stackBuilder.addNextIntent(intent2);
                                builder.setContentIntent(stackBuilder.getPendingIntent(0, 134217728));
                                if (intent.getBooleanExtra("isSetLargeIcon", true)) {
                                    builder.setLargeIcon(iconBitmap);
                                }
                                isBigPictureStyle = false;
                                bigpictureData = intent.getByteArrayExtra("bigpictureData");
                                if (bigpictureData != null) {
                                    try {
                                        PushConfig.LogI("BigPictureStyle");
                                        bigPictureStyle = new BigPictureStyle();
                                        bigPictureStyle.setBigContentTitle(Html.fromHtml(intent.getExtras().getString("title")));
                                        bigPictureStyle.setSummaryText(Html.fromHtml(intent.getExtras().getString("msg")));
                                        bigPictureStyle.bigPicture(BitmapFactory.decodeByteArray(bigpictureData, 0, bigpictureData.length));
                                        builder.setStyle(bigPictureStyle);
                                        isBigPictureStyle = true;
                                    } catch (Exception e3) {
                                        PushConfig.LogI("BigPictureStyle Exception : " + e3.toString());
                                        isBigPictureStyle = false;
                                    }
                                }
                                if (!isBigPictureStyle) {
                                    PushConfig.LogI("buckettype : " + intent.getExtras().getInt("buckettype", 0));
                                    switch (intent.getExtras().getInt("buckettype", 0)) {
                                        case o.a /*1*/:
                                        case o.b /*2*/:
                                            newMsg = null;
                                            if (TextUtils.isEmpty(intent.getExtras().getString("htmlbigmsg"))) {
                                                newMsg = intent.getExtras().getString("htmlbigmsg");
                                            } else {
                                                if (TextUtils.isEmpty(intent.getExtras().getString("htmlmsg"))) {
                                                    newMsg = intent.getExtras().getString("htmlmsg");
                                                } else {
                                                    if (TextUtils.isEmpty(intent.getExtras().getString("bigmsg"))) {
                                                        newMsg = intent.getExtras().getString("bigmsg");
                                                    } else {
                                                        if (!TextUtils.isEmpty(intent.getExtras().getString("msg"))) {
                                                            newMsg = intent.getExtras().getString("msg");
                                                        }
                                                    }
                                                }
                                            }
                                            if (TextUtils.isEmpty(newMsg)) {
                                                newMsg = i.a;
                                            }
                                            newSeqArray = MsgsOfNoticeID.addMsg(context, intent.getExtras().getString("noticeID"), newMsg);
                                            bucketsize = intent.getExtras().getInt("bucketsize", 1);
                                            length = newSeqArray.length;
                                            if (bucketsize > r0) {
                                                bucketsize = newSeqArray.length;
                                            }
                                            builder.setContentInfo(String.valueOf(bucketsize));
                                            PushConfig.LogI("final bucketsize : " + bucketsize + ", msgsArray.length : " + newSeqArray.length);
                                            switch (intent.getExtras().getInt("buckettype", 0)) {
                                                case o.a /*1*/:
                                                    PushConfig.LogI("buckettype : InboxStyle");
                                                    msgsArray = new CharSequence[newSeqArray.length];
                                                    y = 0;
                                                    for (String string : newSeqArray) {
                                                        msgsArray[y] = Html.fromHtml(string);
                                                        PushConfig.LogI("msgsArray[" + y + "] : " + msgsArray[y]);
                                                        y++;
                                                    }
                                                    inboxStyle = new InboxStyle();
                                                    for (i2 = 0; i2 < bucketsize; i2++) {
                                                        try {
                                                            inboxStyle.addLine(msgsArray[(msgsArray.length - i2) - 1]);
                                                            PushConfig.LogI("Inbox text : " + msgsArray[(msgsArray.length - i2) - 1].toString());
                                                        } catch (Exception e32) {
                                                            PushConfig.LogI("InboxStyle Exception : " + e32.toString());
                                                        }
                                                    }
                                                    inboxStyle.setSummaryText(Html.fromHtml(intent.getExtras().getString("ticker")));
                                                    builder.setStyle(inboxStyle);
                                                    break;
                                                case o.b /*2*/:
                                                    PushConfig.LogI("buckettype : BigTextStyle");
                                                    buf = new StringBuffer(newSeqArray[newSeqArray.length - 1]);
                                                    for (z = 1; z < bucketsize; z++) {
                                                        buf.append("<br />").append(newSeqArray[(newSeqArray.length - z) - 1]);
                                                    }
                                                    PushConfig.LogI("buf.toString() : " + buf.toString());
                                                    bigTextStyle = new BigTextStyle();
                                                    PushConfig.LogI("buckket bigText : " + Html.fromHtml(buf.toString()));
                                                    bigTextStyle.bigText(Html.fromHtml(buf.toString()));
                                                    bigTextStyle.setSummaryText(Html.fromHtml(intent.getExtras().getString("ticker")));
                                                    builder.setStyle(bigTextStyle);
                                                    break;
                                                default:
                                                    break;
                                            }
                                        default:
                                            MsgsOfNoticeID.clearMsgs(context, intent.getExtras().getString("noticeID"));
                                            PushConfig.LogI("BigTextStyle");
                                            bigTextStyle = new BigTextStyle();
                                            bigTextStyle.bigText(Html.fromHtml(intent.getExtras().getString("bigmsg")));
                                            bigTextStyle.setSummaryText(Html.fromHtml(intent.getExtras().getString("ticker")));
                                            builder.setStyle(bigTextStyle);
                                            break;
                                    }
                                }
                                if (VERSION.SDK_INT >= 21) {
                                    PushConfig.LogI("Notification in Android 5.0 and later version and not used custom widget style.");
                                    builder.setVisibility(1);
                                    builder.setCategory("msg");
                                    builder.setPriority(1);
                                    builder.setColor(intent.getExtras().getInt("icon_color", 0));
                                    builder.extend(new WearableExtender().setContentIcon(intent.getExtras().getInt("smallIconResID")));
                                }
                                notification = builder.build();
                                notification.headsUpContentView = notification.bigContentView.clone();
                            }
                            if (intent.getIntExtra("isSound", 1) != 2) {
                                if (intent.getExtras().getString("sound") != null) {
                                    if (!i.a.equalsIgnoreCase(intent.getExtras().getString("sound"))) {
                                        if (VERSION.SDK_INT < 21) {
                                            notification.audioAttributes = Notification.AUDIO_ATTRIBUTES_DEFAULT;
                                        } else {
                                            notification.audioStreamType = 5;
                                        }
                                        notification.sound = Uri.parse("android.resource://" + intent.getExtras().getString("packageName") + "/" + findSoundId(intent.getExtras().getString("packageName"), intent.getExtras().getString("sound")));
                                    }
                                }
                                notification.defaults |= 1;
                            }
                            if (intent.getIntExtra("isVib", 1) != 2) {
                                if (intent.getExtras().getString("vib") != null) {
                                    notification.defaults |= 2;
                                } else {
                                    try {
                                        i = notification.defaults;
                                        if (Integer.parseInt(intent.getExtras().getString("vib")) == 1) {
                                            length = 0;
                                        } else {
                                            length = 2;
                                        }
                                        notification.defaults = length | i;
                                    } catch (Exception e4) {
                                    }
                                }
                            }
                            nm.notify(null, Integer.parseInt(intent.getExtras().getString("noticeID")), notification);
                        }
                    } catch (Exception e5) {
                        e32 = e5;
                        PushConfig.LogI("Custom WidgetStyle Exception : " + e32.toString());
                        if (VERSION.SDK_INT >= 16) {
                            PushConfig.LogI("Notification in Android 4.1 and later version");
                            stackBuilder = TaskStackBuilder.create(context);
                            stackBuilder.addParentStack(NotificationMessage.class);
                            stackBuilder.addNextIntent(intent2);
                            builder.setContentIntent(stackBuilder.getPendingIntent(0, 134217728));
                            if (intent.getBooleanExtra("isSetLargeIcon", true)) {
                                builder.setLargeIcon(iconBitmap);
                            }
                            isBigPictureStyle = false;
                            bigpictureData = intent.getByteArrayExtra("bigpictureData");
                            if (bigpictureData != null) {
                                BigPictureStyle bigPictureStyle;
                                PushConfig.LogI("BigPictureStyle");
                                bigPictureStyle = new BigPictureStyle();
                                bigPictureStyle.setBigContentTitle(Html.fromHtml(intent.getExtras().getString("title")));
                                bigPictureStyle.setSummaryText(Html.fromHtml(intent.getExtras().getString("msg")));
                                bigPictureStyle.bigPicture(BitmapFactory.decodeByteArray(bigpictureData, 0, bigpictureData.length));
                                builder.setStyle(bigPictureStyle);
                                isBigPictureStyle = true;
                            }
                            if (isBigPictureStyle) {
                                PushConfig.LogI("buckettype : " + intent.getExtras().getInt("buckettype", 0));
                                switch (intent.getExtras().getInt("buckettype", 0)) {
                                    case o.a /*1*/:
                                    case o.b /*2*/:
                                        newMsg = null;
                                        if (TextUtils.isEmpty(intent.getExtras().getString("htmlbigmsg"))) {
                                            newMsg = intent.getExtras().getString("htmlbigmsg");
                                        } else {
                                            if (TextUtils.isEmpty(intent.getExtras().getString("htmlmsg"))) {
                                                newMsg = intent.getExtras().getString("htmlmsg");
                                            } else {
                                                if (TextUtils.isEmpty(intent.getExtras().getString("bigmsg"))) {
                                                    newMsg = intent.getExtras().getString("bigmsg");
                                                } else {
                                                    if (TextUtils.isEmpty(intent.getExtras().getString("msg"))) {
                                                        newMsg = intent.getExtras().getString("msg");
                                                    }
                                                }
                                            }
                                        }
                                        if (TextUtils.isEmpty(newMsg)) {
                                            newMsg = i.a;
                                        }
                                        newSeqArray = MsgsOfNoticeID.addMsg(context, intent.getExtras().getString("noticeID"), newMsg);
                                        bucketsize = intent.getExtras().getInt("bucketsize", 1);
                                        length = newSeqArray.length;
                                        if (bucketsize > r0) {
                                            bucketsize = newSeqArray.length;
                                        }
                                        builder.setContentInfo(String.valueOf(bucketsize));
                                        PushConfig.LogI("final bucketsize : " + bucketsize + ", msgsArray.length : " + newSeqArray.length);
                                        switch (intent.getExtras().getInt("buckettype", 0)) {
                                            case o.a /*1*/:
                                                PushConfig.LogI("buckettype : InboxStyle");
                                                msgsArray = new CharSequence[newSeqArray.length];
                                                y = 0;
                                                while (length < i) {
                                                    msgsArray[y] = Html.fromHtml(string);
                                                    PushConfig.LogI("msgsArray[" + y + "] : " + msgsArray[y]);
                                                    y++;
                                                }
                                                inboxStyle = new InboxStyle();
                                                for (i2 = 0; i2 < bucketsize; i2++) {
                                                    inboxStyle.addLine(msgsArray[(msgsArray.length - i2) - 1]);
                                                    PushConfig.LogI("Inbox text : " + msgsArray[(msgsArray.length - i2) - 1].toString());
                                                }
                                                inboxStyle.setSummaryText(Html.fromHtml(intent.getExtras().getString("ticker")));
                                                builder.setStyle(inboxStyle);
                                                break;
                                            case o.b /*2*/:
                                                PushConfig.LogI("buckettype : BigTextStyle");
                                                buf = new StringBuffer(newSeqArray[newSeqArray.length - 1]);
                                                for (z = 1; z < bucketsize; z++) {
                                                    buf.append("<br />").append(newSeqArray[(newSeqArray.length - z) - 1]);
                                                }
                                                PushConfig.LogI("buf.toString() : " + buf.toString());
                                                bigTextStyle = new BigTextStyle();
                                                PushConfig.LogI("buckket bigText : " + Html.fromHtml(buf.toString()));
                                                bigTextStyle.bigText(Html.fromHtml(buf.toString()));
                                                bigTextStyle.setSummaryText(Html.fromHtml(intent.getExtras().getString("ticker")));
                                                builder.setStyle(bigTextStyle);
                                                break;
                                            default:
                                                break;
                                        }
                                    default:
                                        MsgsOfNoticeID.clearMsgs(context, intent.getExtras().getString("noticeID"));
                                        PushConfig.LogI("BigTextStyle");
                                        bigTextStyle = new BigTextStyle();
                                        bigTextStyle.bigText(Html.fromHtml(intent.getExtras().getString("bigmsg")));
                                        bigTextStyle.setSummaryText(Html.fromHtml(intent.getExtras().getString("ticker")));
                                        builder.setStyle(bigTextStyle);
                                        break;
                                }
                            }
                            if (VERSION.SDK_INT >= 21) {
                                PushConfig.LogI("Notification in Android 5.0 and later version and not used custom widget style.");
                                builder.setVisibility(1);
                                builder.setCategory("msg");
                                builder.setPriority(1);
                                builder.setColor(intent.getExtras().getInt("icon_color", 0));
                                builder.extend(new WearableExtender().setContentIcon(intent.getExtras().getInt("smallIconResID")));
                            }
                            notification = builder.build();
                            notification.headsUpContentView = notification.bigContentView.clone();
                        } else {
                            notification = builder.getNotification();
                        }
                        if (intent.getIntExtra("isSound", 1) != 2) {
                            if (intent.getExtras().getString("sound") != null) {
                                if (i.a.equalsIgnoreCase(intent.getExtras().getString("sound"))) {
                                    if (VERSION.SDK_INT < 21) {
                                        notification.audioStreamType = 5;
                                    } else {
                                        notification.audioAttributes = Notification.AUDIO_ATTRIBUTES_DEFAULT;
                                    }
                                    notification.sound = Uri.parse("android.resource://" + intent.getExtras().getString("packageName") + "/" + findSoundId(intent.getExtras().getString("packageName"), intent.getExtras().getString("sound")));
                                }
                            }
                            notification.defaults |= 1;
                        }
                        if (intent.getIntExtra("isVib", 1) != 2) {
                            if (intent.getExtras().getString("vib") != null) {
                                i = notification.defaults;
                                if (Integer.parseInt(intent.getExtras().getString("vib")) == 1) {
                                    length = 2;
                                } else {
                                    length = 0;
                                }
                                notification.defaults = length | i;
                            } else {
                                notification.defaults |= 2;
                            }
                        }
                        nm.notify(null, Integer.parseInt(intent.getExtras().getString("noticeID")), notification);
                    }
                }
                if (intent.getBooleanExtra("isSetLargeIcon", true)) {
                    builder.setLargeIcon(iconBitmap);
                }
                if (VERSION.SDK_INT >= 16) {
                    PushConfig.LogI("Notification in Android 4.1 and later version");
                    stackBuilder = TaskStackBuilder.create(context);
                    stackBuilder.addParentStack(NotificationMessage.class);
                    stackBuilder.addNextIntent(intent2);
                    builder.setContentIntent(stackBuilder.getPendingIntent(0, 134217728));
                    if (intent.getBooleanExtra("isSetLargeIcon", true)) {
                        builder.setLargeIcon(iconBitmap);
                    }
                    isBigPictureStyle = false;
                    bigpictureData = intent.getByteArrayExtra("bigpictureData");
                    if (bigpictureData != null) {
                        PushConfig.LogI("BigPictureStyle");
                        bigPictureStyle = new BigPictureStyle();
                        bigPictureStyle.setBigContentTitle(Html.fromHtml(intent.getExtras().getString("title")));
                        bigPictureStyle.setSummaryText(Html.fromHtml(intent.getExtras().getString("msg")));
                        bigPictureStyle.bigPicture(BitmapFactory.decodeByteArray(bigpictureData, 0, bigpictureData.length));
                        builder.setStyle(bigPictureStyle);
                        isBigPictureStyle = true;
                    }
                    if (isBigPictureStyle) {
                        PushConfig.LogI("buckettype : " + intent.getExtras().getInt("buckettype", 0));
                        switch (intent.getExtras().getInt("buckettype", 0)) {
                            case o.a /*1*/:
                            case o.b /*2*/:
                                newMsg = null;
                                if (TextUtils.isEmpty(intent.getExtras().getString("htmlbigmsg"))) {
                                    newMsg = intent.getExtras().getString("htmlbigmsg");
                                } else {
                                    if (TextUtils.isEmpty(intent.getExtras().getString("htmlmsg"))) {
                                        newMsg = intent.getExtras().getString("htmlmsg");
                                    } else {
                                        if (TextUtils.isEmpty(intent.getExtras().getString("bigmsg"))) {
                                            newMsg = intent.getExtras().getString("bigmsg");
                                        } else {
                                            if (TextUtils.isEmpty(intent.getExtras().getString("msg"))) {
                                                newMsg = intent.getExtras().getString("msg");
                                            }
                                        }
                                    }
                                }
                                if (TextUtils.isEmpty(newMsg)) {
                                    newMsg = i.a;
                                }
                                newSeqArray = MsgsOfNoticeID.addMsg(context, intent.getExtras().getString("noticeID"), newMsg);
                                bucketsize = intent.getExtras().getInt("bucketsize", 1);
                                length = newSeqArray.length;
                                if (bucketsize > r0) {
                                    bucketsize = newSeqArray.length;
                                }
                                builder.setContentInfo(String.valueOf(bucketsize));
                                PushConfig.LogI("final bucketsize : " + bucketsize + ", msgsArray.length : " + newSeqArray.length);
                                switch (intent.getExtras().getInt("buckettype", 0)) {
                                    case o.a /*1*/:
                                        PushConfig.LogI("buckettype : InboxStyle");
                                        msgsArray = new CharSequence[newSeqArray.length];
                                        y = 0;
                                        while (length < i) {
                                            msgsArray[y] = Html.fromHtml(string);
                                            PushConfig.LogI("msgsArray[" + y + "] : " + msgsArray[y]);
                                            y++;
                                        }
                                        inboxStyle = new InboxStyle();
                                        for (i2 = 0; i2 < bucketsize; i2++) {
                                            inboxStyle.addLine(msgsArray[(msgsArray.length - i2) - 1]);
                                            PushConfig.LogI("Inbox text : " + msgsArray[(msgsArray.length - i2) - 1].toString());
                                        }
                                        inboxStyle.setSummaryText(Html.fromHtml(intent.getExtras().getString("ticker")));
                                        builder.setStyle(inboxStyle);
                                        break;
                                    case o.b /*2*/:
                                        PushConfig.LogI("buckettype : BigTextStyle");
                                        buf = new StringBuffer(newSeqArray[newSeqArray.length - 1]);
                                        for (z = 1; z < bucketsize; z++) {
                                            buf.append("<br />").append(newSeqArray[(newSeqArray.length - z) - 1]);
                                        }
                                        PushConfig.LogI("buf.toString() : " + buf.toString());
                                        bigTextStyle = new BigTextStyle();
                                        PushConfig.LogI("buckket bigText : " + Html.fromHtml(buf.toString()));
                                        bigTextStyle.bigText(Html.fromHtml(buf.toString()));
                                        bigTextStyle.setSummaryText(Html.fromHtml(intent.getExtras().getString("ticker")));
                                        builder.setStyle(bigTextStyle);
                                        break;
                                    default:
                                        break;
                                }
                            default:
                                MsgsOfNoticeID.clearMsgs(context, intent.getExtras().getString("noticeID"));
                                if (!(TextUtils.isEmpty(intent.getExtras().getString("bigmsg")) || isBigPictureStyle)) {
                                    PushConfig.LogI("BigTextStyle");
                                    bigTextStyle = new BigTextStyle();
                                    bigTextStyle.bigText(Html.fromHtml(intent.getExtras().getString("bigmsg")));
                                    bigTextStyle.setSummaryText(Html.fromHtml(intent.getExtras().getString("ticker")));
                                    builder.setStyle(bigTextStyle);
                                    break;
                                }
                        }
                    }
                    if (VERSION.SDK_INT >= 21) {
                        PushConfig.LogI("Notification in Android 5.0 and later version and not used custom widget style.");
                        builder.setVisibility(1);
                        builder.setCategory("msg");
                        builder.setPriority(1);
                        builder.setColor(intent.getExtras().getInt("icon_color", 0));
                        builder.extend(new WearableExtender().setContentIcon(intent.getExtras().getInt("smallIconResID")));
                    }
                    notification = builder.build();
                    if (notification.bigContentView != null && VERSION.SDK_INT >= 21) {
                        notification.headsUpContentView = notification.bigContentView.clone();
                    }
                } else {
                    notification = builder.getNotification();
                }
            } else {
                PushConfig.LogI("Notification in Android 2.3.4 and before version");
                pendingIntent = PendingIntent.getActivity(context, Integer.parseInt(intent.getExtras().getString("noticeID")), intent2, 134217728);
                notification = new Notification();
                notification.icon = iconResID;
                notification.tickerText = Html.fromHtml(intent.getExtras().getString("ticker"));
                notification.when = System.currentTimeMillis();
                notification.flags |= 16;
                notification.setLatestEventInfo(context, Html.fromHtml(intent.getExtras().getString("title")), Html.fromHtml(intent.getExtras().getString("msg")), pendingIntent);
            }
            if (intent.getIntExtra("isSound", 1) != 2) {
                if (intent.getExtras().getString("sound") != null) {
                    if (i.a.equalsIgnoreCase(intent.getExtras().getString("sound"))) {
                        if (VERSION.SDK_INT < 21) {
                            notification.audioStreamType = 5;
                        } else {
                            notification.audioAttributes = Notification.AUDIO_ATTRIBUTES_DEFAULT;
                        }
                        notification.sound = Uri.parse("android.resource://" + intent.getExtras().getString("packageName") + "/" + findSoundId(intent.getExtras().getString("packageName"), intent.getExtras().getString("sound")));
                    }
                }
                notification.defaults |= 1;
            }
            if (intent.getIntExtra("isVib", 1) != 2) {
                if (intent.getExtras().getString("vib") != null) {
                    i = notification.defaults;
                    if (Integer.parseInt(intent.getExtras().getString("vib")) == 1) {
                        length = 2;
                    } else {
                        length = 0;
                    }
                    notification.defaults = length | i;
                } else {
                    notification.defaults |= 2;
                }
            }
            nm.notify(null, Integer.parseInt(intent.getExtras().getString("noticeID")), notification);
        }
    }

    protected static void setToast(Context context, Intent intent) {
        new Handler(context.getMainLooper()).post(new AnonymousClass1(context, intent));
    }

    protected static int findSoundId(String packageName, String variable) {
        try {
            Class clazz = Class.forName(new StringBuilder(String.valueOf(packageName)).append(".R$raw").toString());
            return clazz.getField(variable).getInt(clazz);
        } catch (Exception e) {
            return -1;
        }
    }
}
