package com.hrw.mvplibrary.widget.smartContainer.base;

/**
 * @version 1.0.0
 * @author:hrw
 * @date:2019/08/22 17:09
 * @desc:
 */
public enum LoadMoreStatus {
    NotifyPullUp("上拉加载"),
    NotifyLoosen("松开加载"),
    Loading("正在加载中"),
    LoadComplete("加载完成"),
    LoadNoMore("没有更多数据可供加载");

    private String mLabel;

    LoadMoreStatus(String label) {
        mLabel = label;
    }

    public String getLabel() {
        return mLabel;
    }}
