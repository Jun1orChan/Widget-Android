package org.nd.widget_android.divider;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nd.widget.divider.GridDividerItemDecoration;
import com.nd.widget.progress.HorizentalProgressBar;
import com.nd.widget_android.R;

import java.util.Timer;
import java.util.TimerTask;

public class DividerActivity extends AppCompatActivity {
    private int mProgress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_divider);
        RecyclerView recList = (RecyclerView) findViewById(R.id.recList);
        recList.setHasFixedSize(true);
        recList.setLayoutManager(new GridLayoutManager(this, 4));
        recList.addItemDecoration(new GridDividerItemDecoration(this, R.drawable.shape_item));
        recList.setAdapter(new PhotoRecAdapter());
        final HorizentalProgressBar horizentalProgressBar = (HorizentalProgressBar) findViewById(R.id.pb);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        horizentalProgressBar.setProgress(mProgress++);
                    }
                });
            }
        }, 0, 10);
    }
}
