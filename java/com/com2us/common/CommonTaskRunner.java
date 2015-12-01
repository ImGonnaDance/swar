package com.com2us.common;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import com.com2us.common.data.CommonProperties;
import com.com2us.common.network.NetworkInfo;
import java.util.Vector;

public class CommonTaskRunner {
    private static final int COMMON_VERSION_MAJOR = 1;
    private static final int COMMON_VERSION_MINOR1 = 0;
    private static final int COMMON_VERSION_MINOR2 = 3;
    private static ECommonState mState = ECommonState.IDLE;
    private static Vector<Runnable> mTaskList = new Vector();
    private static ICommonTaskRunnerListener mTaskRunnerListener;

    class AnonymousClass1 implements Runnable {
        private final /* synthetic */ Context val$context;

        AnonymousClass1(Context context) {
            this.val$context = context;
        }

        public void run() {
            NetworkInfo.store(this.val$context);
        }
    }

    class AnonymousClass2 extends Thread {
        private final /* synthetic */ int val$id;
        private final /* synthetic */ boolean val$isStart;

        AnonymousClass2(int i, boolean z) {
            this.val$id = i;
            this.val$isStart = z;
        }

        public void run() {
            ((Runnable) CommonTaskRunner.mTaskList.get(this.val$id)).run();
            synchronized (CommonTaskRunner.mTaskList) {
                CommonTaskRunner.mTaskList.remove(this.val$id);
                CommonTaskRunner.onTaskCompleted(this.val$isStart);
            }
        }
    }

    class AnonymousClass3 implements ICommonTaskRunnerListener {
        private final /* synthetic */ Activity val$activity;
        private final /* synthetic */ ViewGroup val$group;
        private final /* synthetic */ ICommonTaskRunnerListener val$listener;

        AnonymousClass3(Activity activity, ICommonTaskRunnerListener iCommonTaskRunnerListener, ViewGroup viewGroup) {
            this.val$activity = activity;
            this.val$listener = iCommonTaskRunnerListener;
            this.val$group = viewGroup;
        }

        public void onStartTaskCompleted() {
            Activity activity = this.val$activity;
            final ViewGroup viewGroup = this.val$group;
            final ICommonTaskRunnerListener iCommonTaskRunnerListener = this.val$listener;
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    viewGroup.setVisibility(8);
                    iCommonTaskRunnerListener.onStartTaskCompleted();
                }
            });
        }

        public void onStopTaskCompleted() {
            this.val$listener.onStopTaskCompleted();
        }
    }

    public enum ECommonState {
        IDLE,
        START_RUNNING,
        START_COMPLETED,
        STOP_RUNNING,
        STOP_COMPLETED
    }

    public static void start(Context context, ICommonTaskRunnerListener listener) {
        mState = ECommonState.START_RUNNING;
        mTaskRunnerListener = listener;
        CommonProperties.loadProperties(context);
        putTask(new AnonymousClass1(context));
        runTask(true);
    }

    public static void startWithUI(Activity activity, ICommonTaskRunnerListener listener) {
        startWithUI(activity, listener, createProgressBar(activity));
    }

    public static void stop() {
        mState = ECommonState.STOP_RUNNING;
        runTask(false);
    }

    public static ECommonState getCurrentState() {
        return mState;
    }

    public static boolean isStarted() {
        return mState == ECommonState.START_COMPLETED;
    }

    private static void runTask(boolean isStart) {
        for (int i = COMMON_VERSION_MINOR1; i < mTaskList.size(); i += COMMON_VERSION_MAJOR) {
            new AnonymousClass2(i, isStart).start();
        }
        onTaskCompleted(isStart);
    }

    private static void putTask(Runnable task) {
        mTaskList.add(task);
    }

    private static void onTaskCompleted(boolean isStart) {
        if (!mTaskList.isEmpty()) {
            return;
        }
        if (isStart) {
            mState = ECommonState.START_COMPLETED;
            mTaskRunnerListener.onStartTaskCompleted();
            return;
        }
        mState = ECommonState.STOP_COMPLETED;
        mTaskRunnerListener.onStopTaskCompleted();
    }

    private static void startWithUI(Activity activity, ICommonTaskRunnerListener listener, ViewGroup group) {
        start(activity, new AnonymousClass3(activity, listener, group));
    }

    private static ViewGroup createProgressBar(Activity activity) {
        LinearLayout progressBarLayout = new LinearLayout(activity);
        progressBarLayout.setGravity(17);
        ProgressBar bar = new ProgressBar(activity, null, 16842874);
        LayoutParams params = new LayoutParams(-1, -1);
        progressBarLayout.addView(bar);
        activity.addContentView(progressBarLayout, params);
        return progressBarLayout;
    }

    public static String getVersion() {
        return "1.0.3";
    }
}
