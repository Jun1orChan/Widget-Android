package org.nd.widget_android.indicator;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.nd.widget.indicator.PageIndicatorView;
import com.nd.widget_android.R;

/**
 * @author Administrator
 */
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
