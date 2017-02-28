package com.cherish.cdsnapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class copyrightActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.copyright);
    }

    /**
     * 点击返回按钮
     * @param view
     */
    public void back(View view)
    {
        finish();
    }

}
