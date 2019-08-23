package com.hrw.mvplibrary.widget.smartContainer.header;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hrw.mvplibrary.R;
import com.hrw.mvplibrary.widget.smartContainer.SmartViewUtil;
import com.hrw.mvplibrary.widget.smartContainer.base.BaseRefreshView;
import com.hrw.mvplibrary.widget.smartContainer.base.RefreshStatus;

/**
 * @version 1.0.0
 * @author:hrw
 * @date:2019/08/22 15:02
 * @desc:
 */
public class DefaultHeaderView extends RelativeLayout implements BaseRefreshView {
    TextView mRefreshViewContent;


    float mDensity;

    public DefaultHeaderView(Context context) {
        this(context, null);
    }

    public DefaultHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDensity = SmartViewUtil.getDensity(context);
        addView(LayoutInflater.from(context).inflate(R.layout.smart_refresh_view_layout, null));
        mRefreshViewContent = findViewById(R.id.smart_refresh_view_content);
    }

    @Override
    public int getRefreshShowHeight() {
        return (int) (mDensity * 56);
    }

    @Override
    public View getContentView() {
        return this;
    }

    @Override
    public void onRefreshStatusChange(RefreshStatus refreshStatus) {
        mRefreshViewContent.setText(refreshStatus.getStatusLabel());
        switch (refreshStatus) {
            case NotifyPullDown:
                break;
            case NotifyLoosen:
                break;
            case Refreshing:
                break;
            case RefreshComplete:
                break;
        }
    }


}
