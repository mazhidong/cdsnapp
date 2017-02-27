package com.cherish.cdsnapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.cherish.bean.CommonException;
import com.cherish.bean.NewsItem;
import com.cherish.biz.NewsItemBiz;
import com.viewpagerindicator.TabPageIndicator;

import java.util.List;

public class MainActivity extends FragmentActivity {
    private TabPageIndicator mIndicator ;
    private ViewPager mViewPager ;
    private FragmentPagerAdapter mAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIndicator = (TabPageIndicator) findViewById(R.id.id_indicator);
        mViewPager = (ViewPager) findViewById(R.id.id_pager);
        mAdapter = new TabAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mIndicator.setViewPager(mViewPager, 0);
    }

    public void textClick(View view){
        Intent intent = new Intent(MainActivity.this,NewsContentActivity.class);
        intent.putExtra("url", "http://www.csdn.net/article/2017-01-22/2826689");
        startActivity(intent);
    }
}
