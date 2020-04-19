package org.jun1or.widget.pulltorefresh;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.istrong.widget.R;

import java.util.ArrayList;
import java.util.List;


public class PullToRefreshLayout extends ViewGroup {

    private List<OnRefreshListener> mOnRefreshListenerList = new ArrayList<>();

    private Scroller mScroller;

    private int mLastY;//手指最后的Y坐标
    private int mDownY;
    private int mRefreshHeaderHeight;//到达刷新的阀值

    private static final int STATUS_IDLE = 0;
    private static final int STATUS_REFRESHING = 1;
    private static final int STATUS_RELEASE_REFRESH = 2;
    private static final int STATUS_REFRESH_COMPLETE = 3;
    private int mStatus = STATUS_IDLE;
    private int mCompleteDelay = 1000;
    private int mTopViewOffset;

    public PullToRefreshLayout(Context context) {
        this(context, null);
    }

    public PullToRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullToRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mScroller = new Scroller(context);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.widget_ptr);
        mCompleteDelay = ta.getInt(R.styleable.widget_ptr_widget_complete_delay, 1000);
        ta.recycle();
    }

    public void setTopViewOffset(int topViewOffset) {
        if (topViewOffset < 0)
            return;
        mTopViewOffset = topViewOffset;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //刷新控件View
        View topView = getChildAt(0);
        View mainView = getChildAt(1);
        int measuredWidth = mainView.getMeasuredWidth();
        int measuredHeight = mainView.getMeasuredHeight();
        mainView.layout(0 + getPaddingLeft(), 0 + getPaddingTop(), measuredWidth - getPaddingRight(), getPaddingTop() + measuredHeight - getPaddingBottom());
        measuredHeight = topView.getMeasuredHeight();
        mRefreshHeaderHeight = measuredHeight;
        topView.layout(mainView.getLeft(), mainView.getTop() - measuredHeight + mTopViewOffset, mainView.getRight(), mainView.getTop() + mTopViewOffset);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取孩子个数
        int childCount = getChildCount();
        if (childCount != 2) {
            throw new RuntimeException("the child count must be 2");
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (canChildScrollUp(getChildAt(1))) {
            return false;
        }
//        if (mStatus == STATUS_REFRESH_COMPLETE) {
//            return true;
//        }
        int action = MotionEventCompat.getActionMasked(ev);
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            return false;
        }
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastY = (int) ev.getY();
                mDownY = new Integer(mLastY);
                break;
            case MotionEvent.ACTION_MOVE:
                if (mStatus == STATUS_REFRESHING || mStatus == STATUS_REFRESH_COMPLETE)
                    return true;
                int yOffset = (int) ev.getY() - mLastY;
                if (yOffset > 0) {
                    return true;
                }
                break;

        }
        return false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        int y = (int) event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                mDownY = new Integer(y);
                break;
            case MotionEvent.ACTION_MOVE:
                int dy = mLastY - y;
                int scollHeight = Math.abs(mScroller.getCurrY());
                if (scollHeight >= mRefreshHeaderHeight) {
                    dy = (int) (dy * 0.4);
                    if (mStatus != STATUS_REFRESHING) {
                        mStatus = STATUS_RELEASE_REFRESH;
                        for (OnRefreshListener onRefreshListener : mOnRefreshListenerList) {
                            onRefreshListener.onReleaseRefresh();
                        }
                    }
                } else {
                    for (OnRefreshListener onRefreshListener : mOnRefreshListenerList) {
                        onRefreshListener.onIdle();
                    }
                }
                smoothScrollBy(0, dy);
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                if ((y - mDownY) >= mRefreshHeaderHeight) {
                    //大于，进入刷新状态。
                    mStatus = STATUS_REFRESHING;
                    for (OnRefreshListener onRefreshListener : mOnRefreshListenerList) {
                        onRefreshListener.onRefreshing();
                    }
                    smoothScrollTo(0, -mRefreshHeaderHeight);
                } else {
                    smoothScrollTo(0, 0);
                    mStatus = STATUS_IDLE;
                    for (OnRefreshListener onRefreshListener : mOnRefreshListenerList) {
                        onRefreshListener.onIdle();
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                smoothScrollTo(0, 0);
                mStatus = STATUS_IDLE;
                break;
        }
        return true;
    }


    /**
     * 判断这个View是不是可以向上滑动
     *
     * @param target
     * @return
     */
    private boolean canChildScrollUp(View target) {
        return ViewCompat.canScrollVertically(target, -1);
    }

    @Override
    public void computeScroll() {
        //先判断mScroller滚动是否完成
        if (mScroller.computeScrollOffset()) {
            //这里调用View的scrollTo()完成实际的滚动
//            Log.e("TAG", mScroller.getCurrY() + "===========" + mScroller.getFinalY());
            scrollTo(0, mScroller.getCurrY() > 0 ? 0 : mScroller.getCurrY());
            for (OnRefreshListener onRefreshListener : mOnRefreshListenerList) {
                onRefreshListener.onScrollHeight(mScroller.getCurrY());
            }
            //必须调用该方法，否则不一定能看到滚动效果
            postInvalidate();
        }
        super.computeScroll();
    }

    private void smoothScrollTo(int fx, int fy) {
        int dx = fx - mScroller.getFinalX();
        int dy = fy - mScroller.getFinalY();
        smoothScrollBy(dx, dy);
    }

    //调用此方法设置滚动的相对偏移
    private void smoothScrollBy(int dx, int dy) {
        //设置mScroller的滚动偏移量
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy);
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            if (getChildAt(i) instanceof OnRefreshListener)
                mOnRefreshListenerList.add((OnRefreshListener) getChildAt(i));
        }
    }

    private int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 设置监听
     *
     * @param onRefreshListener
     */
    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        if (onRefreshListener != null)
            mOnRefreshListenerList.add(onRefreshListener);
    }

    /**
     * 开始刷新
     */
    public void startRefresh() {
        post(new Runnable() {
            @Override
            public void run() {
                smoothScrollTo(0, -mRefreshHeaderHeight);
                mStatus = STATUS_REFRESHING;
                for (OnRefreshListener onRefreshListener : mOnRefreshListenerList) {
                    onRefreshListener.onRefreshing();
                }
            }
        });
    }

    /**
     * 结束刷新
     */
    public void refreshComplete() {
        for (OnRefreshListener onRefreshListener : mOnRefreshListenerList) {
            onRefreshListener.onRefreshComplete();
        }
        mStatus = STATUS_REFRESH_COMPLETE;
        postDelayed(new Runnable() {
            @Override
            public void run() {
                smoothScrollTo(0, 0);
                mStatus = STATUS_IDLE;
                for (OnRefreshListener onRefreshListener : mOnRefreshListenerList) {
                    onRefreshListener.onIdle();
                }
            }
        }, mCompleteDelay);
    }

    public void refreshCompleteImmediately() {
        for (OnRefreshListener onRefreshListener : mOnRefreshListenerList) {
            onRefreshListener.onRefreshComplete();
        }
        mStatus = STATUS_REFRESH_COMPLETE;
        post(new Runnable() {
            @Override
            public void run() {
                smoothScrollTo(0, 0);
                mStatus = STATUS_IDLE;
                for (OnRefreshListener onRefreshListener : mOnRefreshListenerList) {
                    onRefreshListener.onIdle();
                }
            }
        });
    }

    public void setCompleteDelay(int delayMilSec) {
        if (delayMilSec > 0)
            this.mCompleteDelay = delayMilSec;
    }

    /**
     * @return private static final int STATUS_IDLE = 0;
     * private static final int STATUS_REFRESHING = 1;
     * private static final int STATUS_RELEASE_REFRESH = 2;
     */
    public int getStatus() {
        return mStatus;
    }

}
