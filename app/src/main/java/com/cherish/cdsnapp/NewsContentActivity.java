package com.cherish.cdsnapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cherish.bean.CommonException;
import com.cherish.cdsnapp.bean.News;
import com.cherish.cdsnapp.dao.NewsDto;

import java.util.List;

import me.maxwin.view.XListView;

public class NewsContentActivity extends Activity{
    //private XListView mListView;
    private ListView mListView;

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

        //mListView = (XListView) findViewById(R.id.id_listview);
        mListView = (ListView) findViewById(R.id.id_listview);
        mProgressBar = (ProgressBar) findViewById(R.id.id_newsContentPro);

        mListView.setAdapter(mAdapter);
        //mListView.setPullLoadEnable(true);
        //mListView.setXListViewListener(this);

        mProgressBar.setVisibility(View.VISIBLE);
        //onRefresh();
        new LoadDataTask().execute();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                News news = mDatas.get(position);
                String imageLink = news.getImageLink();
                //Toast.makeText(NewContentActivity.this, imageLink, 1).show();
                Intent intent = new Intent(NewsContentActivity.this,ImageShowActivity.class);
                intent.putExtra("url", imageLink);
                startActivity(intent);
            }
        });

    }


    class LoadDataTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try
            {
                //NewsDto mNewsDto = newsDto.getNews(url);
                List<News> list = newsDto.getNewsMobile(url);
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


    public void copyright(View view)
    {
        Intent intent = new Intent(this,copyrightActivity.class);
        startActivity(intent);
    }


}
