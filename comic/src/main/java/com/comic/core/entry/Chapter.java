package com.comic.core.entry;

/**
 * Created By shelli On 2020/8/9 20:48
 */
public class Chapter {
    private String name;  //章节名
    private String url;   //对应章节的链接

    public Chapter(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Chapter [name=" + name + ", url=" + url + "]";
    }
}
