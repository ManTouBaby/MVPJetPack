package com.hrw.mvplibrary.widget.smartContainer.base;

/**
 * @version 1.0.0
 * @author:hrw
 * @date:2019/08/22 16:05
 * @desc:
 */
public enum RefreshStatus {
    NotifyPullDown("下拉刷新"),
    NotifyLoosen("松开刷新"),
    Refreshing("正在刷新"),
    RefreshComplete("刷新完成");

    private String mStatusLabel;


    RefreshStatus(String statusLabel) {
        mStatusLabel = statusLabel;
    }

    public String getStatusLabel() {
        return mStatusLabel;
    }}
