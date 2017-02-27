package com.cherish.cdsnapp.dao;

import com.cherish.bean.CommonException;
import com.cherish.cdsnapp.bean.News;
import com.cherish.csdn.DataUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cherish on 2017/2/27.
 */

public class NewsDto {
    private List<News> newses;
    private String nextPageUrl ;

    public List<News> getNewses()
    {
        return newses;
    }
    public void setNewses(List<News> newses)
    {
        this.newses = newses;
    }
    public String getNextPageUrl()
    {
        return nextPageUrl;
    }
    public void setNextPageUrl(String nextPageUrl)
    {
        this.nextPageUrl = nextPageUrl;
    }


    /**
     * 根据文章的url返回一个NewsDto对象
     *
     * @return
     * @throws CommonException
     */
    public List<News> getNews(String urlStr) throws CommonException
    {
        NewsDto newsDto = new NewsDto();
        List<News> newses = new ArrayList<News>();
        String htmlStr = DataUtil.doGet(urlStr);
        Document doc = Jsoup.parse(htmlStr);
        /*Document doc = null;
        try {
            URL url = new URL(urlStr);
            doc = Jsoup.parse(url,1000);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        // 获得文章中的第一个detail
        Element detailEle = doc.select(".left .detail").get(0);
        // 标题
        Element titleEle = detailEle.select("h1.title").get(0);
        News news = new News();
        news.setTitle(titleEle.text());
        news.setType(News.NewsType.TITLE);
        newses.add(news);
        // 摘要
        Element summaryEle = detailEle.select("div.summary").get(0);
        news = new News();
        news.setSummary(summaryEle.text());
        newses.add(news);
        // 内容
        Element contentEle = detailEle.select("div.con.news_content").get(0);
        Elements childrenEle = contentEle.children();

        for (Element child : childrenEle)
        {
            Elements imgEles = child.getElementsByTag("img");
            // 图片
            if (imgEles.size() > 0)
            {
                for (Element imgEle : imgEles)
                {
                    if (imgEle.attr("src").equals(""))
                        continue;
                    news = new News();
                    news.setImageLink(imgEle.attr("src"));
                    newses.add(news);
                }
            }
            // 移除图片
            imgEles.remove();

            if (child.text().equals(""))
                continue;

            news = new News();
            news.setType(News.NewsType.CONTENT);

            try
            {
                if(child.children().size()==1)
                {
                    Element cc = child.child(0);
                    if(cc.tagName().equals("b"))
                    {
                        news.setType(News.NewsType.BOLD_TITLE);
                    }
                }

            } catch (IndexOutOfBoundsException e)
            {
                e.printStackTrace();
            }
            news.setContent(child.outerHtml());
            newses.add(news);
        }
        newsDto.setNewses(newses);
        return newses;
        //return newsDto;
    }

    public List<News> getNewsMobile(String urlStr) throws CommonException
    {
        NewsDto newsDto = new NewsDto();
        List<News> newses = new ArrayList<News>();
        String htmlStr = DataUtil.doGet(urlStr);
        Document doc = Jsoup.parse(htmlStr);

        // 获得文章中的第一个detail
        Element detailEle = doc.select(".wrapper").get(0);
        // 标题
        Element titleEle = detailEle.select("h1").get(0);
        News news = new News();
        news.setTitle(titleEle.text());
        news.setType(News.NewsType.TITLE);
        newses.add(news);
        // 摘要
        //暂时省略
        /*Element summaryEle = detailEle.select("div.summary")..get(0);
        news = new News();
        news.setSummary(summaryEle.text());
        newses.add(news);*/
        // 内容
        Element contentEle = detailEle.select("div.text").get(0);
        Elements childrenEle = contentEle.children();

        for (Element child : childrenEle)
        {
            Elements imgEles = child.getElementsByTag("img");
            // 图片
            if (imgEles.size() > 0)
            {
                for (Element imgEle : imgEles)
                {
                    if (imgEle.attr("src").equals(""))
                        continue;
                    news = new News();
                    news.setImageLink(imgEle.attr("src"));
                    news.setType(News.NewsType.IMG);
                    newses.add(news);

                }
            }
            // 移除图片
            imgEles.remove();

            if (child.text().equals(""))
                continue;

            news = new News();
            news.setType(News.NewsType.CONTENT);

            try
            {
                if(child.children().size()==1)
                {
                    Element cc = child.child(0);
                    if(cc.tagName().equals("b"))
                    {
                        news.setType(News.NewsType.BOLD_TITLE);
                    }
                }

            } catch (IndexOutOfBoundsException e)
            {
                e.printStackTrace();
            }
            news.setContent(child.outerHtml());
            newses.add(news);
        }
        newsDto.setNewses(newses);
        return newses;
        //return newsDto;
    }
}
