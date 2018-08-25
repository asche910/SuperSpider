package com.service;

import com.bean.Module;
import com.dao.ModuleDao;
import com.util.SpiderUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 模块(简书的专题)爬虫
 * 入口：https://www.jianshu.com/recommendations/collections
 * 模块实例： https://www.jianshu.com/c/V2CqjW
 * 文章实例： https://www.jianshu.com/p/443219930c5b
 * 故此爬虫多处直接传递链接后缀的关键字符串(如‘V2CqjW’、‘443219930c5b’)
 *
 * @author As_
 * @date 2018-08-21 12:31:35
 * @github https://github.com/apknet
 *
 */

public class ModuleSpider {

    public void run() {
        try {

            List<String> moduleList = getModuleList("https://www.jianshu.com/recommendations/collections");

            for (String key: moduleList) {
                //  每个Module爬取10页内容的文章
                System.out.println((moduleList.indexOf(key) + 1) + "." + key);
                for (int page = 1; page < 11; page++) {
                    String moduleUrl = String.format("https://www.jianshu.com/c/%s?order_by=top&page=%d", key, page);

                    List<String> articleList = getArticleList(moduleUrl);

                    for (String articlekey: articleList) {
                        new ArticleSpider().run(articlekey, moduleList.indexOf(key) + 1);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回Module 关键字符段
     * @param url
     * @return
     * @throws IOException
     * @throws SQLException
     */

    private List<String> getModuleList(String url) throws IOException, SQLException {

        List<String> moduleList = new ArrayList<>();
        int i = 0;

        Document document = Jsoup.connect(url).get();
        Element container = document.selectFirst("div[id=list-container]");
        Elements modules = container.children();
        for(Element ele: modules){
            String relUrl = ele.select("a[target=_blank]").attr("href");
            String moduleKey = relUrl.substring(relUrl.indexOf("/c/") + 3);
            moduleList.add(moduleKey);

//             System.out.println(moduleKey);
//              以下爬取数据创建Module对象并持久化

            String name = ele.select("h4[class=name]").text();
            String brief = ele.selectFirst("p[class=collection-description]").text();
            String imgcover = SpiderUtils.handleUrl(ele.selectFirst("img[class=avatar-collection]").attr("src"));

            String articleNu = ele.select("a[target=_blank]").get(1).text();
            int articleNum  = Integer.parseInt(articleNu.substring(0, articleNu.indexOf("篇")));

            String userNu = ele.select("div[class=count]").text().replace(articleNu, "");
            Matcher matcher = Pattern.compile("(\\D*)(\\d*\\.\\d*)?(\\D*)").matcher(userNu);
            matcher.find();
            int userNum = (int)(Float.parseFloat(matcher.group(2)) * 1000);

            Module module = new Module((++i), name, brief, imgcover, userNum, articleNum, "apknet");
            new ModuleDao().addModule(module);

            System.out.println(name + "------------------>");
        }
        return moduleList;
    }



    /**
     * 文章链接实例 https://www.jianshu.com/p/443219930c5b
     * 故此处返回List的String为url后的那段字符串
     *
     * @param url
     * @return
     */
    private List<String> getArticleList(String url) {

        System.out.println("getArticleList:  --------------------->");

        List<String> keyList = new ArrayList<>();

        Document document = null;
        try {
            document = Jsoup.connect(url).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Element ulElement = document.select("ul[class=note-list]").first();

        Elements elements = ulElement.select("li[id]");
        for (Element ele : elements) {
            String relUrl = ele.select("a[class=title]").attr("href");
            String key = relUrl.substring(relUrl.indexOf("/p/") + 3);
            keyList.add(key);
            System.out.println(key);
        }

        return keyList;
    }

}
