package ding.in.simple.data;

import java.util.List;
import java.util.Map;

import ding.in.fastdevlib.network.entity.TestResponseEntity;
import ding.in.simple.data.entity.DrugTestEntity;
import ding.in.simple.data.entity.TestEntity;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

/**
 * fuction：
 * Created by dingdegao on 2016/10/19.
 */
public interface Api {

    public static final String CACHE_TIME = "Cache-Control:max-age=3600";

    @Headers("Cache-Control:max-age=3600")
    @GET("drug/classify")
    Observable<TestResponseEntity<List<TestEntity>>> getTest();

    @Headers("Cache-Control:max-age=3600")
    @FormUrlEncoded
    @POST("drug/list")
    Observable<TestResponseEntity<List<DrugTestEntity>>> postTest(@Field("id") int id);


    @Headers("Cache-Control:max-age=3600")
    @GET("drug/list")
    Observable<TestResponseEntity<List<DrugTestEntity>>> getTest(@Query("id") int id);

    @Multipart
    @POST("upload")
    Observable<TestResponseEntity<List<TestEntity>>> uploadFile(@Part("description") RequestBody description,
                                                                @Part MultipartBody.Part file);


    @Multipart
    @POST("upload")
    Observable<TestResponseEntity<List<TestEntity>>> uploadMultipart(@Part("description") RequestBody description,
                                                                     @PartMap Map<String, MultipartBody.Part> map);


}
