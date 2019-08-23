package com.hrw.mvplibrary.widget.smartContainer.footer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hrw.mvplibrary.R;
import com.hrw.mvplibrary.widget.smartContainer.SmartViewUtil;
import com.hrw.mvplibrary.widget.smartContainer.base.BaseLoadMoreView;
import com.hrw.mvplibrary.widget.smartContainer.base.LoadMoreStatus;

/**
 * @version 1.0.0
 * @author:hrw
 * @date:2019/08/22 17:29
 * @desc:
 */
public class DefaultFooterView extends RelativeLayout implements BaseLoadMoreView {
    private final float mDensity;
    TextView mLoadMoreContent;

    public DefaultFooterView(Context context) {
        this(context, null);
    }

    public DefaultFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDensity = SmartViewUtil.getDensity(context);
        addView(LayoutInflater.from(context).inflate(R.layout.smart_loadmore_view_layout, null));
        mLoadMoreContent = findViewById(R.id.smart_load_more_label);
    }

    @Override
    public int getLoadMoreShowHeight() {
        return (int) (56 * mDensity);
    }

    @Override
    public View getContentView() {
        return this;
    }

    @Override
    public void onLoadStatusChange(LoadMoreStatus loadMoreStatus) {
        mLoadMoreContent.setText(loadMoreStatus.getLabel());
        switch (loadMoreStatus) {
            case NotifyPullUp:
                break;
            case Loading:
                break;
            case LoadComplete:
                break;
            case LoadNoMore:
                break;
        }
    }
}
