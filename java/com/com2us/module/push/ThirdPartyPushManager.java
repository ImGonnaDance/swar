package com.com2us.module.push;

import android.content.Context;
import com.com2us.module.amazonpush.AmazonPush;
import com.com2us.module.jpush.JPush;
import java.util.ArrayList;
import java.util.List;

public class ThirdPartyPushManager {
    private static ThirdPartyPushManager instance = null;
    public List<ThirdPartyPush> thirdPartyPushes = new ArrayList();

    private ThirdPartyPushManager(Context _context) {
        instance = this;
        this.thirdPartyPushes.add(AmazonPush.getInstance(_context));
        this.thirdPartyPushes.add(JPush.getInstance(_context));
    }

    public static ThirdPartyPushManager getInstance(Context context) {
        if (instance == null) {
            instance = new ThirdPartyPushManager(context);
        }
        return instance;
    }

    public void sendRegId(RegisterState registerState) {
        for (ThirdPartyPush tpp : this.thirdPartyPushes) {
            tpp.sendRegId(registerState);
        }
    }

    public void onPause() {
        for (ThirdPartyPush tpp : this.thirdPartyPushes) {
            tpp.onPause();
        }
    }

    public void onResume() {
        for (ThirdPartyPush tpp : this.thirdPartyPushes) {
            tpp.onResume();
        }
    }

    public void stopPush() {
        for (ThirdPartyPush tpp : this.thirdPartyPushes) {
            tpp.stopPush();
        }
    }

    public void resumePush() {
        for (ThirdPartyPush tpp : this.thirdPartyPushes) {
            tpp.resumePush();
        }
    }
}
