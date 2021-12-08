package com.nd.widget.progress;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

import com.nd.widget.R;

/**
 * 圆形转动环
 * Created by Android on 2017/8/24.
 *
 * @author Administrator
 */

public class JCircleProgress extends View {

    public static final int RADIUS = 40;

    private Paint mPaint;
    private ValueAnimator mValueAnimator;
    private int mDefaultWidth = 0;
    private int mDefaultHeight = 0;

    private int mDegree;

    public JCircleProgress(Context context) {
        super(context);
        init(context, null);
    }

    public JCircleProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public JCircleProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void cancelAnimation() {
        if (mValueAnimator != null) {
            mValueAnimator.cancel();
        }
        mValueAnimator = null;
    }

    public void setColor(int startColor, int endColor) {
        Shader shader = new SweepGradient(0, 0, new int[]{startColor, endColor}, null);
        mPaint.setShader(shader);
    }

    public void setStrokeWidth(float width_dp) {
        mPaint.setStrokeWidth(dp2px(getContext(), width_dp));
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.widget_JCircleProgress);
        int startColor = typedArray.getColor(R.styleable.widget_JCircleProgress_widget_startColor, Color.parseColor("#00999999"));
        int endColor = typedArray.getColor(R.styleable.widget_JCircleProgress_widget_endColor, Color.parseColor("#EECCCCCC"));
        float strokeWidth = typedArray.getDimension(R.styleable.widget_JCircleProgress_widget_strokeWidth, dp2px(context, 2));
        mPaint.setStrokeWidth(strokeWidth);
        mDefaultWidth = mDefaultHeight = dp2px(getContext(), RADIUS + 2);
        Shader shader = new SweepGradient(0, 0, new int[]{startColor, endColor}, null);
        mPaint.setShader(shader);
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 获取圆心的x坐标
        int center = getWidth() / 2;
        canvas.translate(center, center);
        canvas.rotate(mDegree);
        canvas.drawCircle(0, 0, center - mPaint.getStrokeWidth(), mPaint);
        if (mValueAnimator == null) {
            startAnimation();
        }
    }


    private void startAnimation() {
        mValueAnimator = ValueAnimator.ofFloat(0, 1);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mDegree = (int) (((Float) animation.getAnimatedValue()) * 360);
                invalidate();
            }
        });
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.setDuration(1500);
        mValueAnimator.setRepeatCount(Animation.INFINITE);
        mValueAnimator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        // 当布局参数设置为wrap_content时，设置默认值
        if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT && getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(mDefaultWidth, mDefaultHeight);
            // 宽 / 高任意一个布局参数为= wrap_content时，都设置默认值
        } else if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(mDefaultWidth, heightSize);
        } else if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(widthSize, mDefaultHeight);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        cancelAnimation();
        super.onDetachedFromWindow();
    }

    private int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
