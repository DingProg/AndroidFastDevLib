package ding.in.simple;

import android.app.Application;

/**
 * fuction：
 * Created by dingdegao on 2016/10/24.
 */
public class App extends Application {

    private static App app;

    public static App getInstance(){
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
    }
}
