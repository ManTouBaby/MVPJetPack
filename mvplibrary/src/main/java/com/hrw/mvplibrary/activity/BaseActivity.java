package com.hrw.mvplibrary.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


/**
 * @version 1.0.0
 * @author:hrw
 * @date:2019/08/14 10:28
 * @desc:
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(createContentView());
//        ButterKnife.bind(this);
        init();
    }

    protected abstract void init();

    protected abstract int createContentView();

}
