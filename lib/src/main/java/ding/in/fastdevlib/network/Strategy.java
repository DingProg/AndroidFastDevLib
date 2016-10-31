package ding.in.fastdevlib.network;

/**
 * fuction：
 * Created by dingdegao on 2016/10/20.
 */
public interface Strategy {
    interface Loading {
        void openLoading();

        void closeLoading();
    }

    interface TimeMonitor {
        void start();

        void finish();

        long getTimeCount();
    }
}
