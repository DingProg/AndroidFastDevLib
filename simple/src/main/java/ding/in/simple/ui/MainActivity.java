package ding.in.simple.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ding.in.fastdevlib.network.ApiServicesFactory;
import ding.in.fastdevlib.network.rx.RxHelper;
import ding.in.fastdevlib.network.rx.RxSubscriber;
import ding.in.simple.R;
import ding.in.simple.data.Api;
import ding.in.simple.data.entity.TestEntity;
import rx.Subscription;

public class MainActivity extends AppCompatActivity {

    List<Subscription> list=new ArrayList<>();//写在基类里

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = ((TextView) findViewById(R.id.tv));
    }

    public void test(View view) {
        Api baseApi = ApiServicesFactory.getInstance().produceApi(Api.class);
        RxSubscriber<List<TestEntity>> rxSubscriber = new RxSubscriber<List<TestEntity>>() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(List<TestEntity> listTestResponseEntity) {
                super.onNext(listTestResponseEntity);
                tv.setText(listTestResponseEntity.toString());
            }
        };
       // Subscription subscription = RxHelper.dealTest(baseApi.getTest(), rxSubscriber);
        List<TestEntity> testList=new ArrayList<>();
        Class<List<TestEntity>> aClass = (Class<List<TestEntity>>) testList.getClass();
        Subscription subscription = RxHelper.dealWithCache("key",aClass,getApplication(),baseApi.getTest(),rxSubscriber);
        list.add(subscription);
    }

    @Override
    protected void onDestroy() {
        for (Subscription subscription : list) {
            RxHelper.unsubscribe(subscription);
        }
        list.clear();
        super.onDestroy();
    }

}
