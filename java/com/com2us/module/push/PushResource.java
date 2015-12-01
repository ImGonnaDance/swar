package com.com2us.module.push;

import java.io.Serializable;

/* compiled from: PushResourceHandler */
class PushResource implements Serializable {
    private static final long serialVersionUID = 656707021576896773L;
    String active;
    String bigmsg;
    String bigpicture;
    String broadcastAction;
    int bucketsize;
    int buckettype;
    long elapsedRealtime;
    String icon;
    String icon_color;
    String msg;
    String noticeID;
    String packageName;
    String pushID;
    long savedCurrentTime;
    String sound;
    String ticker;
    String title;
    long triggerAtTime;
    String type;

    public PushResource(String pushID, String title, String msg, String bigmsg, String ticker, String noticeID, String type, String icon, String sound, String active, String packageName, long elapsedRealtime, long savedCurrentTime, long triggerAtTime, String broadcastAction, int buckettype, int bucketsize, String bigpicture, String icon_color) {
        PushConfig.LogI("[PushResource]" + pushID);
        this.pushID = pushID;
        this.title = title;
        this.msg = msg;
        this.bigmsg = bigmsg;
        this.ticker = ticker;
        this.noticeID = noticeID;
        this.type = type;
        this.icon = icon;
        this.sound = sound;
        this.active = active;
        this.packageName = packageName;
        this.elapsedRealtime = elapsedRealtime;
        this.savedCurrentTime = savedCurrentTime;
        this.triggerAtTime = triggerAtTime;
        this.broadcastAction = broadcastAction;
        this.buckettype = buckettype;
        this.bucketsize = bucketsize;
        this.bigpicture = bigpicture;
        this.icon_color = icon_color;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[pushID=").append(this.pushID).append(", title=").append(this.title).append(", msg=").append(this.msg).append(", bigmsg=").append(this.bigmsg).append(", ticker=").append(this.ticker).append(", noticeID=").append(this.noticeID).append(", type=").append(this.type).append(", icon=").append(this.icon).append(", sound=").append(this.sound).append(", active=").append(this.active).append(", packageName=").append(this.packageName).append(", elapsedRealtime=").append(this.elapsedRealtime).append(", savedCurrentTime=").append(this.savedCurrentTime).append(", triggerAtTime=").append(this.triggerAtTime).append(", broadcastAction=").append(this.broadcastAction).append(", buckettype=").append(this.buckettype).append(", bucketsize=").append(this.bucketsize).append(", bigpicture=").append(this.bigpicture).append(", icon_color=").append(this.icon_color).append("]");
        return sb.toString();
    }
}
