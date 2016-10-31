package ding.in.fastdevlib.network.utils;

import android.text.TextUtils;
import android.util.Log;


/**
 * fuction：打印log
 * Created by dingdegao on 2016/10/27.
 */
public class NLog {
    public static boolean ISDEBUG = true;
    public static String TAG_LOG = "NETWORK";

    public static void init(boolean isDebug,String tag){
        isDebug=isDebug;
        TAG_LOG=tag;
    }

    public static void i(String msg) {
        if (ISDEBUG && !TextUtils.isEmpty(msg)) {
            Log.i(TAG_LOG, msg);
        }
    }

    public static void e(String msg) {
        if (ISDEBUG && !TextUtils.isEmpty(msg)) {
            Log.e(TAG_LOG, msg);
        }
    }

}
