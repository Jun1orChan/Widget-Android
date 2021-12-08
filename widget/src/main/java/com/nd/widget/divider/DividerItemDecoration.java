package com.nd.widget.divider;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Administrator
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    private int mItemPosition;

    private Drawable mDivider;
    private Context mContext;
    private int mOrientation;
    private boolean mIsNeedEndLine;

    public DividerItemDecoration(Context context, int orientation, int resId, boolean isNeedEndLine) {
        mContext = context;
        setOrientation(orientation);
        mDivider = mContext.getResources().getDrawable(resId);
        this.mIsNeedEndLine = isNeedEndLine;
    }

    /**
     * 设置分割线的显示样式
     *
     * @param resId drawable资源,可以使自定义的shape文件
     */
    public void setDivider(int resId) {
        mDivider = mContext.getResources().getDrawable(resId);
    }


    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent, state);
        } else {
            drawHorizontal(c, parent, state);
        }
    }

    public void drawVertical(Canvas c, RecyclerView parent, RecyclerView.State state) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            int childPosition = parent.getChildAdapterPosition(child);
            // 当前item是否需要divider（通常最后一个不需要）
            if (!mIsNeedEndLine) {
                if (!hasDivider(state, childPosition)) {
                    continue;
                }
            }
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }


    public void drawHorizontal(Canvas c, RecyclerView parent, RecyclerView.State state) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            int childPosition = parent.getChildAdapterPosition(child);
            // 当前item是否需要divider（通常最后一个不需要）
            if (!mIsNeedEndLine) {
                if (!hasDivider(state, childPosition)) {
                    continue;
                }
            }
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    private boolean hasDivider(RecyclerView.State state, int childPosition) {
        if (childPosition == state.getItemCount() - 1) {
            return false;
        }
        return true;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int current = parent.getChildLayoutPosition(view);
        //holder出现异常时，可能为-1
        if (current == -1) {
            return;
        }
        if (mOrientation == VERTICAL_LIST) {
            if (state.getItemCount() - 1 == current && !mIsNeedEndLine) {
                outRect.set(0, 0, 0, 0);
            } else {
                outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
            }
        } else {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
    }
}
