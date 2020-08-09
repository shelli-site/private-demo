package com.comic.core.service;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;

/**
 * Created By shelli On 2020/8/9 23:42
 */
public interface ComicCore {
    /**
     * @Param: [doc]
     * @Return: java.lang.String
     * Created By shelli On 2020/8/9 23:58
     * 获取漫画名称
     **/
    String getComicNameByDoc(Document doc);
    /**
     * @Param: [doc]
     * @Return: org.jsoup.select.Elements
     * Created By shelli On 2020/8/9 23:50
     * 获取章节名的a标签列表
     **/
    Elements getNameUrlsByDoc(Document doc);

    /**
     * @Param: [doc]
     * @Return: java.util.List<java.lang.String>
     * Created By shelli On 2020/8/9 23:51
     * 获取章节的图片url列表
     **/
    List<String> getUrlListByDoc(Document doc);
}
