package me.yokeyword.sample;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.helper.ExceptionHandler;

/**
 * Created by YoKey on 16/11/23.
 */
public class App extends Application {
    public static List<Activity> activityList = null;
    private static App mInstance;
    public static Activity mActivity = null;
    public static Context RUNNINGContext = null;// 当前运行的activity的Context上下文
    private static Resources mResources = null;//资源文件
    public static Application getInstance() {
        return mInstance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        RUNNINGContext = getApplicationContext();
        mResources = getResources();
        Fragmentation.builder()
                // 设置 栈视图 模式为 （默认）悬浮球模式   SHAKE: 摇一摇唤出  NONE：隐藏， 仅在Debug环境生效
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(true) // 实际场景建议.debug(BuildConfig.DEBUG)
                /**
                 * 可以获取到{@link me.yokeyword.fragmentation.exception.AfterSaveStateTransactionWarning}
                 * 在遇到After onSaveInstanceState时，不会抛出异常，会回调到下面的ExceptionHandler
                 */
                .handleException(new ExceptionHandler() {
                    @Override
                    public void onException(Exception e) {
                        // 以Bugtags为例子: 把捕获到的 Exception 传到 Bugtags 后台。
                        // Bugtags.sendException(e);
                    }
                })
                .install();
    }

    /**
     * 添加activity
     *
     * @param a
     */
    public static void addActivity(Activity a) {
        if (activityList == null) {
            activityList = new ArrayList<Activity>();
        }
        activityList.add(a);
    }

    /**
     * 移除activity
     *
     * @param a
     */
    public static void removeActivity(Activity a) {
        synchronized (activityList) {
            if (activityList != null && activityList.size() > 0) {
                try {
                    activityList.remove(a);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * finish所有list中的activity
     */
    public static void exit() {
        synchronized (activityList) {
            int size = activityList.size();
            for (int i = 0; i < size; i++) {
                if (activityList.get(0) != null) {
                    activityList.get(0).finish();
                }
            }
            System.gc();// 垃圾回收
            android.os.Process.killProcess(android.os.Process.myPid());
            System.runFinalization();
            System.exit(0);
        }
    }
}
