package com.comic.core;

import com.comic.Main;
import com.comic.core.entry.Chapter;
import com.comic.core.entry.ComicPage;
import com.comic.core.service.ComicCore;
import com.comic.core.service.impl.DefaultComicCore;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created By shelli On 2020/8/9 20:46
 */
public class ComicSpider {
    private static final String DIR_PATH = "D:/var/";
    private String url;
    private String root;
    private String image_url;
    private CloseableHttpClient httpClient;

    public ComicCore comicCore = new DefaultComicCore();
    private String comicName;

    public ComicSpider(String url, String root, String image_url) {
        this.url = url;
        // 这里不做非空校验，或者使用下面这个。
        // Objects.requireNonNull(root);
        if (root.charAt(root.length() - 1) == '/') {
            root = root.substring(0, root.length() - 1);
        }

        this.root = root;
        this.image_url = image_url;
        this.httpClient = HttpClients.createDefault();
    }

    public ComicSpider(String url, Main.ComicWebsite comicWebsite) {
        this(url, comicWebsite.getRoot(), comicWebsite.getImageUrl());
    }

    public void start() {
        try {
            String html = this.getHtml(url);    //获取漫画主页数据
            List<Chapter> chapterList = this.mapChapters(html);  //解析数据，得到各话的地址
            this.download(chapterList);   //依次下载各话。
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从url中获取原始的网页数据
     *
     * @throws IOException
     * @throws ClientProtocolException
     */
    private String getHtml(String url) throws ClientProtocolException, IOException {
        HttpGet get = new HttpGet(url);
        //下面这两句，是因为总是报一个 Invalid cookie header，然后我在网上找到的解决方法。（去掉的话，不影响使用）。
        RequestConfig defaultConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
        get.setConfig(defaultConfig);
        //因为是初学，而且我这里只是请求一次数据即可，这里就简单设置一下 UA
        get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3100.0 Safari/537.36");
        HttpEntity entity = null;
        String html = null;
        try (CloseableHttpResponse response = httpClient.execute(get)) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                entity = response.getEntity();
                if (entity != null) {
                    html = EntityUtils.toString(entity, "UTF-8");
                }
            }
        }
        return html;
    }


    //获取章节名 链接地址
    private List<Chapter> mapChapters(String html) {
        Document doc = Jsoup.parse(html, "UTF-8");
        // ComicCore::getComicNameByDoc
        comicName = comicCore.getComicNameByDoc(doc);
        File dir = new File(DIR_PATH, comicName);
        if (!dir.exists()) {
            if (!dir.mkdir()) {
                try {
                    throw new FileNotFoundException("无法创建指定文件夹" + dir);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        // ComicCore::getNameUrlsByDoc
        Elements name_urls = comicCore.getNameUrlsByDoc(doc);
        return name_urls.stream()
                .map(name_url -> new Chapter(name_url.text(),
                        root + name_url.attr("href")))
                .collect(Collectors.toList());
    }

    /**
     * 依次下载对应的章节
     * 我使用当线程来下载，这种网站，多线程一般容易引起一些问题。
     * 方法说明：
     * 使用循环迭代的方法，以 name 创建文件夹，然后依次下载漫画。
     */
    public void download(List<Chapter> chapterList) {
        chapterList.forEach(chapter -> {
            //按照章节创建文件夹，每一个章节一个文件夹存放。
            File dir = new File(DIR_PATH + comicName, chapter.getName());
            if (!dir.exists()) {
                if (!dir.mkdir()) {
                    try {
                        throw new FileNotFoundException("无法创建指定文件夹" + dir);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }

                //开始按照章节下载
                try {
                    List<ComicPage> urlList = this.getPageUrl(chapter);
                    urlList.forEach(page -> {
                        SinglePictureDownloader downloader = new SinglePictureDownloader(page, dir.getAbsolutePath());
                        downloader.download();
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //获取每一个页漫画的位置
    private List<ComicPage> getPageUrl(Chapter chapter) throws IOException {
        String html = this.getHtml(chapter.getUrl());
        Document doc = Jsoup.parse(html, "UTF-8");
        // ComicCore::getUrlListByDoc
        List<String> urlList = comicCore.getUrlListByDoc(doc);
        AtomicInteger index = new AtomicInteger(1);  //我无法使用索引，这是别人推荐的方式
        return urlList.stream()   //注意这里拼接的不是 root 路径，而是一个新的路径
                .map(url -> new ComicPage(leftPad(index.getAndIncrement() + "", 4, '0'), this.image_url + url, chapter.getUrl()))
                .collect(Collectors.toList());
    }

    public static String leftPad(String src, int len, char ch) {
        return StringUtils.leftPad(src, len, ch);
        /*int diff = len - src.length();
        if (diff <= 0) {
            return src;
        }

        char[] charr = new char[len];
        System.arraycopy(src.toCharArray(), 0, charr, 0, src.length());
        for (int i = src.length(); i < len; i++) {
            charr[i] = ch;
        }
        return new String(charr);*/
    }
}
