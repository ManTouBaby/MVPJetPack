package com.hrw.mvplibrary.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.hrw.mvplibrary.presenter.BasePresenter;

/**
 * @version 1.0.0
 * @author:hrw
 * @date:2019/08/14 10:47
 * @desc:
 */
public abstract class BasePresenterActivity<P extends BasePresenter> extends BaseActivity {
   protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        getLifecycle().addObserver(mPresenter);
    }

    protected abstract @NonNull
    P createPresenter();
}
