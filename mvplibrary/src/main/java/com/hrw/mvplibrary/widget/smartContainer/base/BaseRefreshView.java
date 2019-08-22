package com.hrw.mvplibrary.widget.smartContainer.base;

import android.view.View;

/**
 * @version 1.0.0
 * @author:hrw
 * @date:2019/08/22 15:03
 * @desc:
 */
public interface BaseRefreshView {
    /*获取页面回弹后，显示的高度*/
    int getRefreshShowHeight();

    /*获取内容控件*/
    View getContentView();

    void onRefreshStatusChange(RefreshStatus refreshStatus);
}
