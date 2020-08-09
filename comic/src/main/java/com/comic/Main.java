package com.comic;

import com.comic.core.ComicSpider;

/**
 * Created By shelli On 2020/8/9 20:52
 */
public class Main {
    public static void main(String[] args) {
        String url = "http://manhua.dmzj.com/wsdgd/";  //目录url
        ComicSpider spider = new ComicSpider(url, ComicWebsite.DMZJ);
        spider.start();
    }

    public enum ComicWebsite {
        DMZJ("http://manhua.dmzj.com", "http://images.dmzj.com/");

        ComicWebsite(String root, String image_url) {
            this.root = root;
            this.image_url = image_url;
        }

        //网站根路径，用于拼接字符串
        private String root;
        //图片url
        private String image_url;

        public String getRoot() {
            return root;
        }

        public String getImageUrl() {
            return image_url;
        }
    }
}

