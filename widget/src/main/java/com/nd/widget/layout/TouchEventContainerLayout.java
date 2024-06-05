package com.nd.widget.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * 例如：高德地图，一些情况下不方便对其触摸事件进行拦截。所以采用外层包裹一层的方式，进行事件拦截，解决手势冲突问题
 *
 * @author Administrator
 */

public class TouchEventContainerLayout extends FrameLayout {
    private ViewGroup mViewGroup;

    public TouchEventContainerLayout(Context context) {
        super(context);
    }

    public TouchEventContainerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setViewGroup(ViewGroup viewGroup) {
        this.mViewGroup = viewGroup;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mViewGroup != null) {
            if (ev.getAction() == MotionEvent.ACTION_UP) {
                mViewGroup.requestDisallowInterceptTouchEvent(false);
            } else {
                mViewGroup.requestDisallowInterceptTouchEvent(true);
            }
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}