package com.example.jeason.swipe.DummyData;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ExpandableListData {

    //TODO, data is not fully completed.
    //http://www.yxjs.org
    private static String chuZu = "心密初祖大愚阿阇黎";
    private static String erZu = "心密二祖仁知阿阇黎";
    private static String sanZu = "心密三祖元音阿阇黎";

    public static LinkedHashMap<String, List<String>> getData() {
        LinkedHashMap<String, List<String>> data = new LinkedHashMap<>();

        List<String> san_zu_article = new ArrayList<>();
        san_zu_article.add("佛法修证心要");
        san_zu_article.add("佛法修证心要问答集");
        san_zu_article.add("大手印浅释");
        //中有成就秘笈在下一个版本中单独做一个section
        san_zu_article.add("净土指归");
        san_zu_article.add("禅净密互融互通的修法");
        san_zu_article.add("成佛的诀窍");
        san_zu_article.add("略论禅宗");
        san_zu_article.add("人人皆当成佛");
        san_zu_article.add("法偈");//image + text http://www.yxjs.org/wenji/03_yyasl/10_faji/01.htm

        //TODO, not fully completed.
        List<String> er_zu_article = new ArrayList<>();
        er_zu_article.add("序传");
        er_zu_article.add("金刚般若波罗蜜经分段贯释");
        er_zu_article.add("金刚般若波罗蜜经白话述义");
        er_zu_article.add("般若波罗蜜多心经分段贯释");
        er_zu_article.add("般若波罗蜜多心经白话分段解释");
        er_zu_article.add("大方广圆觉修多罗了义经抉隐");
        er_zu_article.add("大鉴禅师法宝坛经述旨");

        List<String> chu_zu_article = new ArrayList<>();
        chu_zu_article.add("大愚阿阇黎略传");
        chu_zu_article.add("解脱歌");
        chu_zu_article.add("修行要诀");

        data.put(chuZu, chu_zu_article);
        data.put(erZu, er_zu_article);
        data.put(sanZu, san_zu_article);
        return data;
    }

    public static List<String> getTitle() {
        ArrayList<String> title = new ArrayList<>();
        title.add(chuZu);
        title.add(erZu);
        title.add(sanZu);
        return title;
    }

}
