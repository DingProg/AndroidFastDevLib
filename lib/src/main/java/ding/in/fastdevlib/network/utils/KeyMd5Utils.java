package ding.in.fastdevlib.network.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * fuction：对key进行md5变换
 * Created by dingdegao on 2016/10/28.
 */
public class KeyMd5Utils {
    /**
     * 传入缓存的key值，以得到相应的MD5值
     *
     * @param key
     * @return
     */
    public static String hashKey(String key) {
        String mKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            mKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            mKey = String.valueOf(key.hashCode());
        }
        return mKey;
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

}
