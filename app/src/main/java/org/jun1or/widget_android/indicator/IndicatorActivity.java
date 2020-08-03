package org.jun1or.widget_android.indicator;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import org.jun1or.widget.indicator.PageIndicatorView;
import org.jun1or.widget_android.R;

public class IndicatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator);
        ViewPager vp = (ViewPager) findViewById(R.id.vp);
        vp.setAdapter(new VpAdapter());
        PageIndicatorView pageIndicatorView = (PageIndicatorView) findViewById(R.id.indicator);
        pageIndicatorView.setViewPager(vp);
    }
}
