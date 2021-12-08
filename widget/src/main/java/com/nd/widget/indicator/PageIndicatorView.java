package com.nd.widget.indicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.nd.widget.R;


/**
 * 思路：通过ViewPager.addOnPageChangeListener()进行联动
 *
 * @author Administrator
 */

public class PageIndicatorView extends View {


    /**
     * 页面总数
     */
    private int mPageTotolCount;
    /**
     * 间隔
     */
    private int mInterval;
    private Drawable mSelectedDrawable, mUnSelectedDrawable;
    /**
     * 当前选中项
     */
    private int mCurItemIndex;

    public PageIndicatorView(Context context) {
        this(context, null);
    }

    public PageIndicatorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.widget_PageIndicatorView);
        mInterval = (int) ta.getDimension(R.styleable.widget_PageIndicatorView_widget_interval, dp2px(context, 4));
        mSelectedDrawable = ta.getDrawable(R.styleable.widget_PageIndicatorView_widget_selectedDrawable);
        mUnSelectedDrawable = ta.getDrawable(R.styleable.widget_PageIndicatorView_widget_unSelectedDrawable);
        if (mSelectedDrawable == null) {
            mSelectedDrawable = ContextCompat.getDrawable(context, R.drawable.widget_indicator_selected);
        }
        if (mUnSelectedDrawable == null) {
            mUnSelectedDrawable = ContextCompat.getDrawable(context, R.drawable.widget_indicator_unselected);
        }
        ta.recycle();
    }

    public void setSelectedDrawable(Drawable selectedDrawable) {
        mSelectedDrawable = selectedDrawable;
    }

    public void setUnSelectedDrawable(Drawable unSelectedDrawable) {
        mUnSelectedDrawable = unSelectedDrawable;
    }

    public void setViewPager(ViewPager viewPager) {
        viewPager.addOnPageChangeListener(mOnPageChangeListener);
        PagerAdapter pagerAdapter = viewPager.getAdapter();
        if (pagerAdapter != null) {
            mPageTotolCount = pagerAdapter.getCount();
        }
        mCurItemIndex = viewPager.getCurrentItem();
    }


    public void setInterval(int interval) {
        this.mInterval = interval;
    }

    public int getInterval() {
        return this.mInterval;
    }

    public void setPageTotolCount(int count) {
        this.mPageTotolCount = count;
    }

    public int getPageTotolCount() {
        return mPageTotolCount;
    }

    public void setSelectedItemIndex(int index) {
        if (index >= mPageTotolCount) {
            index = index % mPageTotolCount;
        }
        mCurItemIndex = index;
        invalidate();
    }

    public int getSelectedIndex() {
        return mCurItemIndex;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec),
                measureHeight(heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mSelectedDrawable == null || mUnSelectedDrawable == null || mPageTotolCount <= 0) {
            return;
        }
        for (int i = 0; i < mPageTotolCount; i++) {
            //绘制所有未选中的indicator
            int left = getPaddingLeft() + i * (mInterval + mUnSelectedDrawable.getIntrinsicWidth());
            int top = getPaddingTop();
            int right = left + mUnSelectedDrawable.getIntrinsicWidth();
            int bottom = top + mUnSelectedDrawable.getIntrinsicHeight();
            mUnSelectedDrawable.setBounds(left, top, right, bottom);
            mUnSelectedDrawable.draw(canvas);
        }
        //绘制选中项indicator
        int left = getPaddingLeft() + mCurItemIndex * (mInterval + mUnSelectedDrawable.getIntrinsicWidth());
        int top = getPaddingTop();
        int right = left + mUnSelectedDrawable.getIntrinsicWidth();
        int bottom = top + mUnSelectedDrawable.getIntrinsicHeight();
        mSelectedDrawable.setBounds(left, top, right, bottom);
        mSelectedDrawable.draw(canvas);
    }

    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = getPaddingLeft() + getPaddingRight()
                    + (mPageTotolCount * mSelectedDrawable.getIntrinsicWidth()) + (mPageTotolCount - 1)
                    * mInterval;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    private int measureHeight(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = mSelectedDrawable.getIntrinsicHeight() + getPaddingTop() + getPaddingBottom();
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }


    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mCurItemIndex = position;
            invalidate();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
