package org.jun1or.widget.pulltorefresh.header;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.jun1or.widget.R;
import org.jun1or.widget.progress.JCircleProgress;
import org.jun1or.widget.pulltorefresh.OnRefreshListener;


/**
 * @author Administrator
 */
public class ClassicHeaderView extends FrameLayout implements OnRefreshListener {

    private ImageView mImgArrow;
    private TextView mTvStatus;
    private JCircleProgress mJCircleProgress;

    public ClassicHeaderView(Context context) {
        this(context, null);
    }

    public ClassicHeaderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClassicHeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(getContext()).inflate(R.layout.widget_ptr_header_classic, this, true);
        mImgArrow = (ImageView) findViewById(R.id.imgArrow);
        mTvStatus = (TextView) findViewById(R.id.tvStatus);
        mJCircleProgress = (JCircleProgress) findViewById(R.id.pbLoading);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.widget_ptr_header);
        int textColor = ta.getColor(R.styleable.widget_ptr_header_widget_textColor, Color.parseColor("#666666"));
        mTvStatus.setTextColor(textColor);
        int circlePregressStartColor = ta.getColor(R.styleable.widget_ptr_header_widget_circleProgressStartColor, Color.parseColor("#00999999"));
        int circleProgressEndColor = ta.getColor(R.styleable.widget_ptr_header_widget_circleProgressEndColor, Color.parseColor("#EECCCCCC"));
        mJCircleProgress.setColor(circlePregressStartColor, circleProgressEndColor);
        ta.recycle();
    }

    @Override
    public void onReleaseRefresh() {
        mImgArrow.setImageResource(R.mipmap.widget_ptr_up);
        mTvStatus.setText(getContext().getString(R.string.widget_ptr_release_refresh));
    }

    @Override
    public void onRefreshing() {
        mImgArrow.setVisibility(GONE);
        mJCircleProgress.setVisibility(VISIBLE);
        mTvStatus.setText(getContext().getString(R.string.widget_ptr_refreshing));
    }

    @Override
    public void onIdle() {
        mImgArrow.setVisibility(VISIBLE);
        mJCircleProgress.setVisibility(GONE);
        mImgArrow.setImageResource(R.mipmap.widget_ptr_down);
        mTvStatus.setText(getContext().getString(R.string.widget_ptr_will_refresh));
    }

    @Override
    public void onRefreshComplete() {
        mImgArrow.setVisibility(VISIBLE);
        mJCircleProgress.setVisibility(GONE);
        mImgArrow.setImageResource(R.mipmap.widget_ptr_complete);
        mTvStatus.setText(getContext().getString(R.string.widget_ptr_refresh_complete));
    }

    @Override
    public void onScrollHeight(int height) {
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }
}
