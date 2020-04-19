package org.jun1or.widget_android.pulltorefresh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.jun1or.widget.divider.DividerItemDecoration;
import org.jun1or.widget.pulltorefresh.OnRefreshListener;
import org.jun1or.widget.pulltorefresh.PullToRefreshLayout;
import org.jun1or.widget_android.R;

import java.util.ArrayList;
import java.util.List;

public class PullToRefreshActivity extends AppCompatActivity implements OnRefreshListener {

    //下拉刷新控件
    private PullToRefreshLayout crl;

    //列表控件
    private RecyclerView rv = null;

    //显示的数据
    private List<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        crl = (PullToRefreshLayout) findViewById(R.id.crl);
        crl.setTopViewOffset(450);
//        crl.startRefresh();
        crl.setOnRefreshListener(this);
//        crl.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                crl.refreshCompleteImmediately();
//            }
//        }, 5000);
        rv = (RecyclerView) findViewById(R.id.rv);
//        HeaderReFresh = new HeaderReFresh(findViewById(R.id.rl_refresh), crl);

        //数据造假
        for (int i = 0; i < 100; i++) {
            data.add("测试" + i);
        }

        //设置下拉刷新监听
//        crl.setOnRefreshListener(HeaderReFresh);

        //初始化列表控件的布局管理器
        LinearLayoutManager layout = new LinearLayoutManager(this);
        layout.setOrientation(LinearLayoutManager.VERTICAL);

        //设置布局管理器
        rv.setLayoutManager(layout);
        rv.addItemDecoration(new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL, R.drawable.shape_divider_line, true));
        //设置适配器
        rv.setAdapter(new CommonRecyclerViewAdapter<String>(this, data) {

            @Override
            public void convert(CommonRecyclerViewHolder h, String entity, int position) {
                h.setText(android.R.id.text1, entity);
            }

            @Override
            public int getLayoutViewId(int viewType) {
                return android.R.layout.simple_list_item_1;
            }
        });
    }

    @Override
    public void onReleaseRefresh() {

    }

    @Override
    public void onRefreshing() {
        crl.refreshComplete();
    }

    @Override
    public void onIdle() {

    }

    @Override
    public void onRefreshComplete() {

    }

    @Override
    public void onScrollHeight(int height) {

    }
}
