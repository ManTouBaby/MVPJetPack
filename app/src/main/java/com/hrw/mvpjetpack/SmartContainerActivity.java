package com.hrw.mvpjetpack;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.hrw.mvplibrary.activity.BaseActivity;
import com.hrw.mvplibrary.widget.smartContainer.SmartContainer;
import com.hrw.mvplibrary.widget.smartContainer.listener.OnLoadMoreListener;
import com.hrw.mvplibrary.widget.smartContainer.listener.OnRefreshListener;
import com.hrw.smartview.adapter.SmartAdapter;
import com.hrw.smartview.adapter.SmartItemDecoration;
import com.hrw.smartview.adapter.SmartVH;
import com.hrw.smartview.listener.OnSmartItemChildClickListener;
import com.hrw.smartview.listener.OnSmartItemClickListener;
import com.hrw.smartview.listener.OnSmartItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0.0
 * @author:hrw
 * @date:2019/08/20 15:58
 * @desc:
 */
public class SmartContainerActivity extends BaseActivity {
    SmartContainer mSmartContainer;
    RecyclerView rvShowContent;

    int loadMoreCount = 0;

    @Override
    protected void init() {
        rvShowContent = findViewById(R.id.rv_show_content);
        rvShowContent.setLayoutManager(new LinearLayoutManager(this));
        rvShowContent.setHasFixedSize(true);
        rvShowContent.addItemDecoration(new SmartItemDecoration());
        final List<TestBO> strings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TestBO testBO = new TestBO();
            testBO.setName("测试数据" + (i + 1));
            if (i == 3 || i == 7) {
                testBO.setItemType(1);
            } else {
                testBO.setItemType(0);
            }
            strings.add(testBO);
        }
        final SmartAdapter<TestBO> smartAdapter = new SmartAdapter<TestBO>(R.layout.item_list_mainactivity) {
            @Override
            protected void bindView(SmartVH holder, TestBO o, int position) {
                holder.getText(R.id.item_list_name).setText(o.getName());
                addChildClickListener(holder.getViewById(R.id.iv_img_01), o, position);
                addChildClickListener(holder.getViewById(R.id.iv_img_02), o, position);
                addChildClickListener(holder.getViewById(R.id.iv_img_03), o, position);
            }
        };
//        View view = LayoutInflater.from(this).inflate(R.layout.item_header, null);
//        View view1 = LayoutInflater.from(this).inflate(R.layout.item_header, null);
//        View view2 = LayoutInflater.from(this).inflate(R.layout.item_header, null);
//        View view3 = LayoutInflater.from(this).inflate(R.layout.item_footer, null);
//        View view4 = LayoutInflater.from(this).inflate(R.layout.item_footer, null);
//        View view5 = LayoutInflater.from(this).inflate(R.layout.item_footer, null);
//        smartAdapter.setHeaderView(view, view1, view2);
//        smartAdapter.setFooterView(view3, view4, view5);
        smartAdapter.setItemType(0, R.layout.item_list_mainactivity);
        smartAdapter.setItemType(1, R.layout.item_list_mainactivity_1pic);
        rvShowContent.setAdapter(smartAdapter);
        smartAdapter.setDate(strings);
        smartAdapter.setOnSmartItemClickListener(new OnSmartItemClickListener<TestBO>() {
            @Override
            public void onSmartItemClick(TestBO testBO, int position) {
                System.out.println("result-----" + testBO.getName());
            }
        });
        smartAdapter.setOnSmartItemLongClickListener(new OnSmartItemLongClickListener<TestBO>() {
            @Override
            public void onSmartItemLongClick(TestBO testBO, int position) {
                System.out.println("测试长按:" + testBO.getName());
                smartAdapter.addData(position, testBO);
            }
        });
        smartAdapter.setOnSmartItemChildClickListener(new OnSmartItemChildClickListener<TestBO>() {
            @Override
            public void onSmartItemClick(View view, TestBO testBO, int position) {
                switch (view.getId()) {
                    case R.id.iv_img_01:
                        Toast.makeText(SmartContainerActivity.this, "单击图片一", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.iv_img_02:
                        Toast.makeText(SmartContainerActivity.this, "单击图片二", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.iv_img_03:
                        Toast.makeText(SmartContainerActivity.this, "单击图片三", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });


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


        mSmartContainer.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoading() {
                loadMoreCount++;
                final List<TestBO> dates = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    TestBO testBO = new TestBO();
                    testBO.setName("测试数据" + (i + 1));
                    if (i == 3 || i == 7) {
                        testBO.setItemType(1);
                    } else {
                        testBO.setItemType(0);
                    }
                    dates.add(testBO);
                }

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
                                smartAdapter.addDates(dates);
                                if (loadMoreCount < 3) {
                                    mSmartContainer.setLoadMoreComplete();
                                } else {
                                    mSmartContainer.setLoadNoMore();
                                }
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
