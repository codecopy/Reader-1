package com.sismics.util;

import java.io.File;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.sismics.reader.core.dao.file.rss.RssReader;
import com.sismics.reader.core.model.jpa.Article;
import com.sismics.reader.core.model.jpa.Feed;
import com.sismics.reader.core.util.sanitizer.ArticleSanitizer;

/**
 * Test of the article sanitizer.
 * 
 * @author jtremeaux
 */
public class TestArticleSanitizer {
    /**
     * Tests the article sanitizer.
     * 
     * @throws Exception
     */
    @Test
    public void articleSanitizerImageTest() throws Exception {
        // Load a feed
        String url = new File(getClass().getResource("/feed/feed_atom_akewea.xml").getFile()).toURI().toString();
        RssReader reader = new RssReader(url);
        reader.readRssFeed();
        Feed feed = reader.getFeed();
        Assert.assertEquals("http://blog.akewea.com/", feed.getUrl());
        List<Article> articleList = reader.getArticleList();
        Assert.assertEquals(20, articleList.size());
        Article article = articleList.get(0);

        // Images: transform relative URLs to absolute
        ArticleSanitizer articleSanitizer = new ArticleSanitizer(feed.getUrl());
        String html = articleSanitizer.sanitize(article.getDescription());
        Assert.assertTrue(html.contains("http://blog.akewea.com/themes/akewea-4/smilies/redface.png\""));
    }

    /**
     * Tests the article sanitizer.
     * 
     * @throws Exception
     */
    @Test
    public void articleSanitizerIframeVimeoTest() throws Exception {
        // Load a feed
        String url = new File(getClass().getResource("/feed/feed_rss2_fubiz.xml").getFile()).toURI().toString();
        RssReader reader = new RssReader(url);
        reader.readRssFeed();
        Feed feed = reader.getFeed();
        Assert.assertEquals("http://www.fubiz.net", feed.getUrl());
        List<Article> articleList = reader.getArticleList();
        Assert.assertEquals(10, articleList.size());
        Article article = articleList.get(0);

        // Allow iframes to Vimeo
        ArticleSanitizer articleSanitizer = new ArticleSanitizer(feed.getUrl());
        String html = articleSanitizer.sanitize(article.getDescription());
        Assert.assertTrue(html.contains("Déjà nominé dans la"));
        Assert.assertTrue(html.contains("<iframe src=\"http://player.vimeo.com/video/63898090?title&#61;0&amp;byline&#61;0&amp;portrait&#61;0&amp;color&#61;ffffff\" width=\"640\" height=\"360\">"));
    }

    /**
     * Tests the article sanitizer.
     * 
     * @throws Exception
     */
    @Test
    public void articleSanitizerIframeDailymotionTest() throws Exception {
        // Load a feed
        String url = new File(getClass().getResource("/feed/feed_rss2_korben.xml").getFile()).toURI().toString();
        RssReader reader = new RssReader(url);
        reader.readRssFeed();
        Feed feed = reader.getFeed();
        Assert.assertEquals("http://korben.info", feed.getUrl());
        List<Article> articleList = reader.getArticleList();
        Assert.assertEquals(30, articleList.size());
        Article article = articleList.get(21);

        // Allow iframes to Vimeo
        ArticleSanitizer articleSanitizer = new ArticleSanitizer(feed.getUrl());
        String html = articleSanitizer.sanitize(article.getDescription());
        Assert.assertTrue(html.contains("On ne va pas faire les étonnés, hein"));
        Assert.assertTrue(html.contains("<iframe src=\"http://www.dailymotion.com/embed/video/xy9zdc\" height=\"360\" width=\"640\">"));
    }

    /**
     * Tests the article sanitizer.
     * 
     * @throws Exception
     */
    @Test
    public void articleSanitizerIframeYoutubeTest() throws Exception {
        // Load a feed
        String url = new File(getClass().getResource("/feed/feed_rss2_korben.xml").getFile()).toURI().toString();
        RssReader reader = new RssReader(url);
        reader.readRssFeed();
        Feed feed = reader.getFeed();
        Assert.assertEquals("http://korben.info", feed.getUrl());
        List<Article> articleList = reader.getArticleList();
        Assert.assertEquals(30, articleList.size());
        Article article = articleList.get(20);

        // Allow iframes to Vimeo
        ArticleSanitizer articleSanitizer = new ArticleSanitizer(feed.getUrl());
        String html = articleSanitizer.sanitize(article.getDescription());
        Assert.assertTrue(html.contains("Y&#39;a pas que XBMC dans la vie"));
        Assert.assertTrue(html.contains("<iframe src=\"http://www.youtube.com/embed/n2d4c8JIT0E?feature&#61;player_embedded\" height=\"360\" width=\"640\">"));
    }

    /**
     * Tests the article sanitizer.
     * 
     * @throws Exception
     */
    @Test
    public void articleSanitizerIframeSlashdotTest() throws Exception {
        // Load a feed
        String url = new File(getClass().getResource("/feed/feed_rss2_slashdot.xml").getFile()).toURI().toString();
        RssReader reader = new RssReader(url);
        reader.readRssFeed();
        Feed feed = reader.getFeed();
        Assert.assertEquals("http://slashdot.org/", feed.getUrl());
        List<Article> articleList = reader.getArticleList();
        Assert.assertEquals(25, articleList.size());
        Article article = articleList.get(0);

        // Allow unknown iframes
        ArticleSanitizer articleSanitizer = new ArticleSanitizer(feed.getUrl());
        String html = articleSanitizer.sanitize(article.getDescription());
        Assert.assertTrue(html.contains("Higgs data and the cosmic microwave background map from the Planck mission"));
        Assert.assertFalse(html.contains("iframe"));
    }
}
