package com.hrw.mvplibrary.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.hrw.mvplibrary.presenter.BasePresenter;

/**
 * @version 1.0.0
 * @author:hrw
 * @date:2019/08/15 9:42
 * @desc:
 */
public abstract class BasePresenterFragment extends BaseFragment {
    private BasePresenter mPresenter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = createPresenter();
        getLifecycle().addObserver(mPresenter);
    }

    protected abstract @NonNull
    BasePresenter createPresenter();
}
