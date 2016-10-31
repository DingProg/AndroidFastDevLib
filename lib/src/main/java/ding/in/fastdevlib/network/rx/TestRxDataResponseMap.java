package ding.in.fastdevlib.network.rx;

import ding.in.fastdevlib.network.ApiExection;
import ding.in.fastdevlib.network.ExceptionCode;
import ding.in.fastdevlib.network.entity.TestResponseEntity;

import rx.functions.Func1;

/**
 * fuction：
 * Created by dingdegao on 2016/10/20.
 */
public class TestRxDataResponseMap<T> implements Func1<TestResponseEntity<T>, T> {
    @Override
    public T call(TestResponseEntity<T> tResponseEntity) {
        if (tResponseEntity != null) {
            if (tResponseEntity.isStatus()) {
                return tResponseEntity.getTngou();
            } else {
                throw new ApiExection(ExceptionCode.FAILED);
            }
        } else {
            throw new ApiExection(ExceptionCode.RETROFIT);
        }
    }
}
