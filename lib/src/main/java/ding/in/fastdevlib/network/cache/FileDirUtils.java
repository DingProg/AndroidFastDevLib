package ding.in.fastdevlib.network.cache;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * fuction：得到缓存路径
 * Created by dingdegao on 2016/10/28.
 */
public class FileDirUtils {

    public static File getCacheDir(Context context, String dir){
        File file=null;
       if(isSdCardExist()){
           String cacheDir=context.getExternalCacheDir()+File.separator+dir;
           file=new File(cacheDir);

       }else{
           file=new File(context.getCacheDir().getPath()+file.separator+dir);
       }
        if(file !=null && !file.exists()){
            file.mkdirs();
        }
        return file;
    }

    private static boolean isSdCardExist() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED) || !Environment.isExternalStorageRemovable()) {//判断是否已经挂载
            return true;
        }
        return false;
    }

}
