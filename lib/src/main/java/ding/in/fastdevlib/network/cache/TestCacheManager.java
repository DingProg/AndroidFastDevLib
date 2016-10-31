package ding.in.fastdevlib.network.cache;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



/**
 * 简单缓存工具类
 */
public class TestCacheManager {

    // wifi缓存时间为5分钟
    private static long WIFI_CACHE_TIME = 5 * 60 * 1000;
    // 其他网络环境为1小时
    private static long OTHER_CACHE_TIME = 60 * 60 * 1000;

    private int APPVERSION = 100;

    private static int VALUECOUNT = 1;// 同一个key可以对应多少个缓存文件

    private static int MAXSIZE = 10 * 1024 * 1024;// 一个缓存文件最大可以缓存10M

    public static final String CACHE_DIR = "ecej/cache";// 对象缓存目录

    private static DiskLruCacheHelper mDiskLruCache;
    private static TestCacheManager cacheManager;

    public TestCacheManager(Context context) {
        APPVERSION=getVersionCode(context);
        try {
            mDiskLruCache = DiskLruCacheHelper.open(new File(CACHE_DIR), APPVERSION, VALUECOUNT, MAXSIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存对象
     *
     * @param ser
     * @throws IOException
     */
    public static boolean saveObject(Context context,Serializable ser,
                              String key) {
        ObjectOutputStream oos = null;
        try {
            DiskLruCacheHelper.Editor editor = getDiskLruCacheOutputStream(context,
                    CACHE_DIR, key);
            if (editor != null) {
                oos = new ObjectOutputStream(editor.newOutputStream(0));
                oos.writeObject(ser);
                oos.flush();
                editor.commit();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 读取对象
     *
     * @param key
     * @return
     * @throws IOException
     */
    public static Serializable readObject(Context context,String key) {
        ObjectInputStream ois = null;
        try {
            DiskLruCacheHelper.Editor editor = getDiskLruCacheOutputStream(context,
                    CACHE_DIR, key);
            ois = new ObjectInputStream(editor.newInputStream(0));
            return (Serializable) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 清除缓存
     *
     * @return
     * @throws IOException
     */
    public  static boolean clear() throws IOException {
        if (mDiskLruCache != null) {
            mDiskLruCache.delete();
            return true;
        }
        return false;
    }

    /**
     * 判断缓存是否已经失效
     */
    private static boolean isCacheDataFailure(Context context,String cachefile) {
        File data = context.getFileStreamPath(cachefile);
        if (!data.exists()) {
            return false;
        }
        long existTime = System.currentTimeMillis() - data.lastModified();
        boolean failure = false;
        if (NetWorkType.getNetworkType(context) == NetWorkType.NETTYPE_WIFI) {
            failure = existTime > WIFI_CACHE_TIME ? true : false;
        } else {
            failure = existTime > OTHER_CACHE_TIME ? true : false;
        }
        return failure;
    }

    /**
     * 获取DiskLruCache的editor
     *
     * @param context
     * @param key
     * @return
     * @throws IOException
     */
    private static DiskLruCacheHelper.Editor getDiskLruCacheOutputStream(
            Context context, String uniqueName, String key) throws IOException {
        DiskLruCacheHelper.Editor editor = mDiskLruCache.edit(hashKeyForDisk(key));
        return editor;
    }

    /**
     * 传入缓存的key值，以得到相应的MD5值
     *
     * @param key
     * @return
     */
    private static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 获取相应的缓存目录
     *
     * @param context
     * @param uniqueName
     * @return
     */
    private static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    private static int getVersionCode(Context context) {
        return getVersionCode(context,context.getPackageName());
    }

    private static int getVersionCode(Context context,String packageName) {
        try {
            return context
                    .getPackageManager()
                    .getPackageInfo(packageName, 0)
                    .versionCode;
        } catch (PackageManager.NameNotFoundException ex) {
            return 0;
        }
    }


}
