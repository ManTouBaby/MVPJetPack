package com.hrw.mvplibrary.presenter;

import com.hrw.mvplibrary.utils.LogUtil;

/**
 * @version 1.0.0
 * @author:hrw
 * @date:2019/08/14 10:25
 * @desc:
 */
public class BasePresenter implements IPresenter {
    @Override
    public void onCreate() {
        LogUtil.d("IPresenter onCreate");
    }

    @Override
    public void onResume() {
        LogUtil.d("IPresenter onResume");
    }

    @Override
    public void onPause() {
        LogUtil.d("IPresenter onPause");
    }

    @Override
    public void onDestroy() {
        LogUtil.d("IPresenter onDestroy");
    }
}
