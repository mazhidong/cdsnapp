package com.cherish.cdsnapp;


import com.cherish.bean.CommonException;
import com.cherish.bean.NewsItem;
import com.cherish.biz.NewsItemBiz;
import com.cherish.csdn.Constaint;

import junit.framework.TestCase;


import org.junit.Test;

import java.util.List;

/**
 * Created by cherish on 2017/2/22.
 */

public class test extends TestCase {
    @Test
    public void test01()
    {
        NewsItemBiz biz = new NewsItemBiz();
        int currentPage = 1;
        try
        {
            /**
             * 业界
             */
            List<NewsItem> newsItems = biz.getNewsItems(1, currentPage);
            for (NewsItem item : newsItems)
            {
                System.out.println(item);
            }

            System.out.println("----------------------");
            /**
             * 程序员杂志
             */
            newsItems = biz.getNewsItems(Constaint.NEWS_TYPE_CHENGXUYUAN, currentPage);
            for (NewsItem item : newsItems)
            {
                System.out.println(item);
            }
            System.out.println("----------------------");
            /**
             * 研发
             */
            newsItems = biz.getNewsItems(Constaint.NEWS_TYPE_YANFA, currentPage);
            for (NewsItem item : newsItems)
            {
                System.out.println(item);
            }
            System.out.println("----------------------");
            /**
             * 移动
             */
            newsItems = biz.getNewsItems(Constaint.NEWS_TYPE_YIDONG, currentPage);
            for (NewsItem item : newsItems)
            {
                System.out.println(item);
            }
            System.out.println("----------------------");

        } catch (CommonException e)
        {
            e.printStackTrace();
        }
    }



}
