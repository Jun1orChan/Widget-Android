package org.jun1or.widget_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.jun1or.widget_android.divider.DividerActivity;
import org.jun1or.widget_android.indicator.IndicatorActivity;
import org.jun1or.widget_android.pulltorefresh.PullToRefreshActivity;

public class WidgetActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget);
    }


    public void goDividerAcitivty(View view) {
        Intent intent = new Intent(this, DividerActivity.class);
        startActivity(intent);
    }

    public void goIndicatorActivity(View view) {
        Intent intent = new Intent(this, IndicatorActivity.class);
        startActivity(intent);
    }

    public void pullToRefreshActivity(View view) {
        Intent intent = new Intent(this, PullToRefreshActivity.class);
        startActivity(intent);
    }


}
