package com.hrw.mvplibrary.widget.smartContainer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.widget.ListViewCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Scroller;

import com.hrw.mvplibrary.R;
import com.hrw.mvplibrary.widget.smartContainer.base.BaseLoadMoreView;
import com.hrw.mvplibrary.widget.smartContainer.base.BaseRefreshView;
import com.hrw.mvplibrary.widget.smartContainer.base.LoadMoreStatus;
import com.hrw.mvplibrary.widget.smartContainer.base.RefreshStatus;
import com.hrw.mvplibrary.widget.smartContainer.base.SmartContainerStatus;
import com.hrw.mvplibrary.widget.smartContainer.footer.DefaultFooterView;
import com.hrw.mvplibrary.widget.smartContainer.header.DefaultHeaderView;
import com.hrw.mvplibrary.widget.smartContainer.listener.OnLoadMoreListener;
import com.hrw.mvplibrary.widget.smartContainer.listener.OnRefreshListener;

/**
 * @version 1.0.0
 * @author:hrw
 * @date:2019/08/19 17:09
 * @desc:加载刷新容器，可用于下拉刷新，上拉加载，数据初始化动画，数据加载失败显示，数据网络异常显示
 */
public class SmartContainer extends ViewGroup {
    private final int mTouchSlop;//滑动距离建议值
    OnLoadMoreListener mOnLoadMoreListener;
    OnRefreshListener mOnRefreshListener;

    private BaseRefreshView mRefreshViewContainer;
    private View mRefreshView;//下拉刷新View
    private BaseLoadMoreView mLoadMoreViewContainer;
    private View mLoadMoreView;//上拉加载View

    private View mDataLoadingView;//加载动画View
    private View mDataErrorView;//数据加载失败View
    private View mDataNetErrorView;//数据网络异常View
    private View mDataNulView;//数据加载为空View
    private View mContentView;//主内容View

    private Scroller mScroller;
    private SmartContainerStatus mStatus = SmartContainerStatus.NORMAL;//记录控制当前所处状态--默认为正常状态
    private int mActionType;//操作类型 1：表示下拉刷新  2:表示上拉加载

    public SmartContainer(Context context) {
        this(context, null);
    }

    public SmartContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mScroller = new Scroller(context);

        mRefreshViewContainer = new DefaultHeaderView(context);
        mRefreshView = mRefreshViewContainer.getContentView();
        mLoadMoreViewContainer = new DefaultFooterView(context);
        mLoadMoreView = mLoadMoreViewContainer.getContentView();

        mDataLoadingView = LayoutInflater.from(context).inflate(R.layout.smart_data_loading_view_layout, null);
        mDataErrorView = LayoutInflater.from(context).inflate(R.layout.smart_data_error_view_layout, null);
        mDataNetErrorView = LayoutInflater.from(context).inflate(R.layout.smart_data_net_error_view_layout, null);
        mDataNulView = LayoutInflater.from(context).inflate(R.layout.smart_data_null_view_layout, null);

        addView(mRefreshView, 0);
        addView(mLoadMoreView, 1);
        addView(mDataLoadingView, 2);
        addView(mDataErrorView, 3);
        addView(mDataNetErrorView, 4);
        addView(mDataNulView, 5);

        mDataLoadingView.setVisibility(GONE);
        mDataErrorView.setVisibility(GONE);
        mDataNetErrorView.setVisibility(GONE);
        mDataNulView.setVisibility(GONE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        if (count > 7) {
            throw new SecurityException("the smartContainerView can hold only one childView");
        } else if (count < 7) {
            throw new SecurityException("the smartContainerView must add one childView and could holder only one childView");
        }
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        mContentView = getChildAt(6);
        int childHeight = mContentView.getMeasuredHeight();
        int childWidth = mContentView.getMeasuredWidth();
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(childWidth, childHeight);
        }
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.EXACTLY) {
            setMeasuredDimension(childWidth, heightSize);
        }
        if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {
            setMeasuredDimension(widthSize, heightSize);
        }
    }

    private int lastX;
    private int lastY;
    private int lastInterceptX;
    private int lastInterceptY;

    int mSlideDistance;//滑动距离

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isIntercept = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        System.out.println("滑动监听:" + isIntercept);

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - lastInterceptX;
                int deltaY = y - lastInterceptY;
                if (Math.abs(deltaY) > Math.abs(deltaX)) {//判断滑动方向进行拦截,水平--垂直
                    isIntercept = deltaY < 0 && canChildScroll(-1) || deltaY > 0 && canChildScroll(1);
                } else {
                    isIntercept = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        lastX = x;
        lastY = y;
        lastInterceptX = x;
        lastInterceptY = y;
        return isIntercept;

    }


    /**
     * @param direction 检查向上滚动为负，检查为正向下滚动
     * @return 判断内容控件是否可以进行滑动操作
     */
    public boolean canChildScroll(int direction) {
        if (mContentView instanceof ListView) {
            return ListViewCompat.canScrollList((ListView) mContentView, direction);
        }
        return mContentView.canScrollVertically(direction);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("按下测试");
                mSlideDistance = 0;
                mActionType = 0;
                if (!mScroller.isFinished()) {//如果回弹动画没有结束，再次下拉则停止上一次的回弹动画
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int distance = y - lastY;
                mSlideDistance += distance;
                scrollBy(0, -distance);
                if (distance > 0) {//进行下拉刷
                    mActionType = 1;
                    if (mSlideDistance > mRefreshViewContainer.getRefreshShowHeight()) {
                        mRefreshViewContainer.onRefreshStatusChange(RefreshStatus.NotifyLoosen);
                    } else {
                        mRefreshViewContainer.onRefreshStatusChange(RefreshStatus.NotifyPullDown);
                    }
                } else if (distance < 0) {
                    mActionType = 2;
                    if (mSlideDistance > mLoadMoreView.getMeasuredHeight()) {
                        mLoadMoreViewContainer.onLoadStatusChange(LoadMoreStatus.NotifyLoosen);
                    } else {
                        mLoadMoreViewContainer.onLoadStatusChange(LoadMoreStatus.NotifyPullUp);
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                if (mActionType == 1) {
                    smoothScrollerTo(0, -mRefreshViewContainer.getRefreshShowHeight());
                    mRefreshViewContainer.onRefreshStatusChange(RefreshStatus.Refreshing);
                    mStatus = SmartContainerStatus.REFRESHING;
                    if (mOnRefreshListener != null) {
                        mOnRefreshListener.onRefreshing();
                    }
                }
                if (mActionType == 2) {
                    smoothScrollerTo(0, mLoadMoreView.getMeasuredHeight());
                    mLoadMoreViewContainer.onLoadStatusChange(LoadMoreStatus.Loading);
                    mStatus = SmartContainerStatus.LOADING;
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoading();
                    }
                }

                break;
        }
        lastY = y;
        lastX = x;
        return true;
    }


    public void setRefreshComplete() {
        smoothScrollerTo(0, 0);
        mRefreshViewContainer.onRefreshStatusChange(RefreshStatus.RefreshComplete);
    }

    public void setLoadMoreComplete() {
        mLoadMoreViewContainer.onLoadStatusChange(LoadMoreStatus.LoadComplete);
    }

    public void setLoadNoMore() {
        mLoadMoreViewContainer.onLoadStatusChange(LoadMoreStatus.LoadNoMore);
    }

    public void setOnRefreshListener(OnRefreshListener mOnRefreshListener) {
        this.mOnRefreshListener = mOnRefreshListener;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mContentView.layout(l, 0, r, mContentView.getMeasuredHeight());
        mRefreshView.layout(l, -mRefreshView.getMeasuredHeight(), r, 0);
        mLoadMoreView.layout(l, mContentView.getMeasuredHeight(), r, mContentView.getMeasuredHeight() + mLoadMoreView.getMeasuredHeight());
        for (int i = 2; i < getChildCount() - 1; i++) {
            View view = getChildAt(i);
            if (view.getVisibility() != GONE) view.layout(l, 0, r, b - t);
        }

    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    private void smoothScrollerTo(int destX, int destY) {
        mScroller.startScroll(getScrollX(), getScrollY(), destX - getScrollX(), destY - getScrollY(), 1000);
        invalidate();
    }
}
