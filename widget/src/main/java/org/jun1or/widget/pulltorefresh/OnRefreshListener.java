package org.jun1or.widget.pulltorefresh;

/**
 * 刷新的监听
 */
public interface OnRefreshListener {

    void onReleaseRefresh();

    void onRefreshing();

    void onIdle();

    void onRefreshComplete();

    void onScrollHeight(int height);

}