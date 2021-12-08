package com.nd.widget.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.nd.widget.R;


/**
 * @author Administrator
 */
public class HorizentalProgressBar extends View {
    private int mDefaultWidth = 0;
    private int mDefaultHeight = 0;

    private Paint mBgPaint, mFgPaint, mTextPaint;
    private int mProgress;
    private float mRadius;

    private HorizentalProgressBar(Context context) {
        super(context);
    }

    public HorizentalProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizentalProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mDefaultWidth = dp2px(context, 200);
        mDefaultHeight = dp2px(context, 6);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.widget_horizental_progressbar);
        mBgPaint = new Paint();
        mBgPaint.setAntiAlias(true);
        int bgColor = ta.getColor(R.styleable.widget_horizental_progressbar_widget_progress_bg,
                ContextCompat.getColor(context, R.color.widget_progress_default_bg));
        mBgPaint.setColor(bgColor);
        mBgPaint.setStyle(Paint.Style.FILL);
        mFgPaint = new Paint(mBgPaint);
        int fgColor = ta.getColor(R.styleable.widget_horizental_progressbar_widget_progress_fg,
                ContextCompat.getColor(context, R.color.widget_progress_default_fg));
        mFgPaint.setColor(fgColor);
        mFgPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        mRadius = ta.getDimensionPixelSize(R.styleable.widget_horizental_progressbar_widget_progress_radius, 0);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        // 当布局参数设置为wrap_content时，设置默认值
        if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT
                && getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(mDefaultWidth, mDefaultHeight);
            // 宽 / 高任意一个布局参数为= wrap_content时，都设置默认值
        } else if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(mDefaultWidth, heightSize);
        } else if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(widthSize, mDefaultHeight);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //先绘制背景
        canvas.drawRoundRect(new RectF(0, 0, getWidth(), getHeight()), mRadius, mRadius, mBgPaint);
        canvas.drawRect(0, 0, getWidth() * (mProgress / 100f), getHeight(), mFgPaint);
    }

    public static int dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5F);
    }

    public void setProgress(int progress) {
        if (0 <= progress && progress <= 100) {
            mProgress = progress;
            invalidate();
        }
    }
}
