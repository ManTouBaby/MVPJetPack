package com.hrw.mvpjetpack;

import com.hrw.mvplibrary.activity.BaseActivity;
import com.hrw.mvplibrary.widget.smartContainer.SmartContainer;
import com.hrw.mvplibrary.widget.smartContainer.listener.OnRefreshListener;

/**
 * @version 1.0.0
 * @author:hrw
 * @date:2019/08/20 15:58
 * @desc:
 */
public class SmartContainerActivity extends BaseActivity {
    SmartContainer mSmartContainer;


    @Override
    protected void init() {

        mSmartContainer = findViewById(R.id.sc_container);
        mSmartContainer.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefreshing() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mSmartContainer.setRefreshComplete();
                            }
                        });
                    }
                }).start();

            }
        });
    }

    @Override
    protected int createContentView() {
        return R.layout.activity_smart_container_view;
    }
}
