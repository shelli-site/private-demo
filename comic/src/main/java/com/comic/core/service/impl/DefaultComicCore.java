package com.comic.core.service.impl;

import com.comic.core.service.ComicCore;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created By shelli On 2020/8/9 23:43
 * 动漫之家规则[http://manhua.dmzj.com/]
 */
public class DefaultComicCore implements ComicCore {
    @Override
    public String getComicNameByDoc(Document doc) {
        Elements nameEl = doc.select(".odd_anim_title").first()
                .select(".odd_anim_title_m").first()
                .select("span > a > h1");
        return nameEl.text();
    }

    @Override
    public Elements getNameUrlsByDoc(Document doc) {
        return doc.select(".cartoon_online_border").first().select("ul > li > a");
    }

    @Override
    public List<String> getUrlListByDoc(Document doc) {
        Element script = doc.getElementsByTag("script").get(0); //获取第三个脚本的数据
        // 获取的script 无法直接解析，必须先将 page url 取出来，
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        List<String> urlList = new ArrayList<>();
        try {
            urlList = ((ScriptObjectMirror) engine.eval(script.data() + "arr_pages")).entrySet().stream().map(m -> m.getValue().toString()).collect(Collectors.toList());
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return urlList;
    }
}
