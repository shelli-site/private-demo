package com.comic.core.entry;

/**
 * Created By shelli On 2020/8/9 20:49
 */
public class ComicPage {
    private String number;  //每一页的序号
    private String url;  //每一页的链接
    private String refererUrl;

    public String getRefererUrl() {
        return refererUrl;
    }

    public void setRefererUrl(String refererUrl) {
        this.refererUrl = refererUrl;
    }

    public ComicPage(String number, String url, String refererUrl) {
        this.number = number;
        this.url = url;
        this.refererUrl = refererUrl;
    }

    public String getNumber() {
        return number;
    }

    public String getUrl() {
        return url;
    }
}
