package org.nd.widget_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nd.widget_android.R;

import org.nd.widget_android.divider.DividerActivity;
import org.nd.widget_android.indicator.IndicatorActivity;

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


}
