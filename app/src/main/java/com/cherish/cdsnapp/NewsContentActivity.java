package com.cherish.cdsnapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cherish.bean.CommonException;
import com.cherish.cdsnapp.bean.News;
import com.cherish.cdsnapp.dao.NewsDto;

import java.util.List;

import me.maxwin.view.XListView;

public class NewsContentActivity extends Activity implements XListView.IXListViewListener{
    private XListView mListView;

    /**
     * 该页面的url
     */
    private String url;
    private NewsDto newsDto;
    private List<News> mDatas;

    private ProgressBar mProgressBar;
    private NewContentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_content);

        newsDto = new NewsDto();

        Bundle extras = getIntent().getExtras();
        url = extras.getString("url");


        mAdapter = new NewContentAdapter(this);

        mListView = (XListView) findViewById(R.id.id_listview);
        mProgressBar = (ProgressBar) findViewById(R.id.id_newsContentPro);

        mListView.setAdapter(mAdapter);
        mListView.setPullLoadEnable(true);
        mListView.setXListViewListener(this);

        mProgressBar.setVisibility(View.VISIBLE);
        onRefresh();
       // new LoadDataTask().execute();
       /* List<News> list = null;
        try {
            list = newsDto.getNews(url);
            for (News news : list) {
                System.out.println(news);

            }
        } catch (CommonException e) {
            e.printStackTrace();
            System.out.println("123");
        }*/

    }

    @Override
    public void onRefresh() {
        new LoadDataTask().execute();
    }

    @Override
    public void onLoadMore() {

    }

    class LoadDataTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try
            {
                //NewsDto mNewsDto = newsDto.getNews(url);
                List<News> list = newsDto.getNews(url);
                //System.out.println(mNewsDto.getNewses().size());
                for (News news : list) {
                    System.out.println(news);

                }
                //mDatas = mNewsDto.getNewses();
                mDatas = list;
            } catch (CommonException e)
            {
                Looper.prepare();
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(mDatas == null)
                return ;
            mAdapter.addList(mDatas);
            mAdapter.notifyDataSetChanged();
            mProgressBar.setVisibility(View.GONE);
        }
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