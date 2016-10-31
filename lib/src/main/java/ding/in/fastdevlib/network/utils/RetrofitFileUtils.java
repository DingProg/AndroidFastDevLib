package ding.in.fastdevlib.network.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * fuction：retrofit 文件上传工具类
 * Created by dingdegao on 2016/10/21.
 */
public class RetrofitFileUtils {

    /**
     * 创建上传文件体
     *
     * @param upFileKey 上传文件key
     * @param file      file
     * @return 返回上传multipart
     */
    public static MultipartBody.Part createFileMultipartBody(String upFileKey, File file) {
        //构建要上传的文件
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("application/otcet-stream"), file);

        MultipartBody.Part body =
                MultipartBody.Part.createFormData(upFileKey, file.getName(), requestFile);

        return body;
    }


    /**
     * 多文件上传
     *
     * @param mapFile 文件的map
     * @return 返回文件上传体
     */
    public static Map<String, MultipartBody.Part> createManyFileMultipartBody(Map<String, File> mapFile) {
        Map<String, MultipartBody.Part> map = new HashMap<>();

        Iterator<Map.Entry<String, File>> iterator = mapFile.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, File> next = iterator.next();

            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("application/otcet-stream"), next.getValue());

            MultipartBody.Part body =
                    MultipartBody.Part.createFormData(next.getKey(), next.getValue().getName(), requestFile);
            map.put(next.getKey(), body);

        }
        return map;
    }


    /**
     * 上传文件描述
     *
     * @param descriptionString 描述文字
     * @return 返回请求体
     */
    public static RequestBody createFileDescription(String descriptionString) {
        RequestBody description =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), descriptionString);
        return description;
    }

}
