package com.hrw.mvplibrary.widget.smartContainer.base;

import android.view.View;

/**
 * @version 1.0.0
 * @author:hrw
 * @date:2019/08/22 15:28
 * @desc:
 */
public interface BaseLoadMoreView {
    int getLoadMoreShowHeight();

    View getContentView();

    void onLoadStatusChange(LoadMoreStatus loadMoreStatus);
}
