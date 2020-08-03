package org.jun1or.widget.pulltorefresh;

/**
 * 刷新的监听
 *
 * @author Administrator
 */
public interface OnRefreshListener {

    /**
     * 释放刷新
     */
    void onReleaseRefresh();

    /**
     * 正在刷新
     */
    void onRefreshing();

    /**
     * 空闲
     */
    void onIdle();

    /**
     * 刷新完成
     */
    void onRefreshComplete();

    /**
     * 滑动高度
     *
     * @param height
     */
    void onScrollHeight(int height);

}