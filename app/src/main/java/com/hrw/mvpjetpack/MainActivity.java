package com.hrw.mvpjetpack;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.hrw.mvplibrary.activity.BaseActivity;

public class MainActivity extends BaseActivity {
    TextView tvGotoSmartContainer;
    @Override
    protected void init() {
        tvGotoSmartContainer = findViewById(R.id.tv_go_smart_container);
        tvGotoSmartContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SmartContainerActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int createContentView() {
        return R.layout.activity_main;
    }
}
