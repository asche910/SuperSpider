package com.service;

import com.bean.Article;
import com.bean.Comment;
import com.bean.User;
import com.dao.ArticleDao;
import com.dao.CommentDao;
import com.dao.UserDao;
import com.util.IdUtils;
import com.util.SpiderUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * 此类对简书文章内容页进行了详细的解析爬取；
 * 封装后的唯一入口函数 {@link ArticleSpider#run(String, int)}.
 *
 * author As_
 * date 2018-08-21 12:34:23
 * github https://github.com/apknet
 */


public class ArticleSpider {

    /**    长度为4 ：阅读量、评论数、点赞数、文章对应的评论id */
    private List<Integer> readComLikeList = new ArrayList<>();

    /**   文章Id  */
    private long articleId;

    /**
     * 此类的入口函数;
     * 爬虫范围包括文章的详情信息、评论的详细信息；
     * 并同时持久化数据；
     *  key实例：443219930c5b
     *
     * @param key
     * @param flag_type 文章所属分类的索引
     */

    public void run(String key, int flag_type){

        try {
            articleSpider(key, flag_type);

            //    以下参数由articleSpeder()函数获得
            String comUrl = String.format("https://www.jianshu.com/notes/%d/comments?comment_id=&author_only=false&since_id=0&max_id=1586510606000&order_by=desc&page=1", readComLikeList.get(3));

            comSpider(comUrl);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 链接实例 https://www.jianshu.com/p/443219930c5b
     * 故只接受最后一段关键字
     * @param key
     * @param flag_type 文章所属分类的索引
     * @throws Exception
     */
    private void articleSpider(String key, int flag_type) throws Exception {

        String url = "https://www.jianshu.com/p/" + key;

        Document document = getDocument(url);

        IdUtils idUtils = new IdUtils();

        List<Integer> list =  getReadComLikeList(document);

        articleId = idUtils.genArticleId();
        String title = getTitle(document);
        String time = getTime(document);
        String imgCover = getImgCover(document);
        String brief = getBrief(document);
        String content = getContent(document);
        String author = idUtils.genUserId();
        int type = flag_type;
        int readNum = list.get(0);
        int comNum = list.get(1);
        int likeNum = list.get(2);

        //   数据库添加对应对象数据
        Article article = new Article(articleId, title, time, imgCover, brief, content, author, type, readNum, comNum, likeNum);
        new ArticleDao().addArticle(article);

        User user = new User();
        user.setUser(idUtils.genUserId());
        user.setName(getAuthorName(document));
        user.setImgAvatar(getAuthorAvatar(document));
        new UserDao().addUser(user);

    }

    private Document getDocument(String url) throws IOException {

        Document document = Jsoup.connect(url)
                .header("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:61.0) Gecko/20100101 Firefox/61.0")
                .header("Content-Type", "application/json; charset=utf-8")
                .header("Cookie", "_m7e_session=2e930226548924f569cb27ba833346db;locale=zh-CN;default_font=font2;read_mode=day")
                .get();
        return document;
    }

    private String getTitle(Document doc){
        return doc.select("h1[class=title]").text();
    }

    private String getTime(Document doc){
        Element element = doc.select("span[class=publish-time]").first();
        return element.text();
    }


    private String getImgCover(Document doc){
        Element element = doc.select("img[data-original-format=image/jpeg]").first();
        if (element.hasAttr("data-original-src")){
            String url = element.attr("data-original-src").trim();
           return SpiderUtils.handleUrl(url);
        }
        return null;
    }

    private String getBrief(Document doc){
        return doc.select("meta[name=description]").first().attr("content");
    }

    private String getContent(Document doc){
        Element element = doc.select("div[class=show-content-free]").first();
        //        System.out.println(element.html());

        Elements eles = element.select("div[class=image-package]");
        for(Element ele: eles){
            Element imgEle = ele.select("img").first();
            ele.replaceWith(imgEle);
        }

        String result = element.html().replaceAll("data-original-", "");
        return result;
    }

    private String getAuthorAvatar(Document doc){
        String url = doc.select("div[class=author]").select("img").attr("src");
        return SpiderUtils.handleUrl(url);
    }

    private String getAuthorName(Document doc){
        Element element = doc.select("script[data-name=page-data]").first();
        JSONObject jsonObject = new JSONObject(element.html()).getJSONObject("note").getJSONObject("author");
        return jsonObject.getString("nickname");
    }

    private List<Integer> getReadComLikeList(Document doc){
        Element element = doc.select("script[data-name=page-data]").first();
        JSONObject jsonObject = new JSONObject(element.html()).getJSONObject("note");

        readComLikeList.add(jsonObject.getInt("views_count"));
        readComLikeList.add(jsonObject.getInt("comments_count"));
        readComLikeList.add(jsonObject.getInt("likes_count"));
        readComLikeList.add(jsonObject.getInt("id"));

        System.out.println(Arrays.toString(readComLikeList.toArray()));
        return readComLikeList;
    }

    /**
     *  评论区爬虫---包括：
     *  评论楼层、时间、内容、评论者、评论回复信息等
     *  具体可查看{@link Comment}
     *
     * @param url
     * @throws Exception
     */
    private void comSpider(String url) throws Exception {
        URLConnection connection = new URL(url).openConnection();

        //  需加上Accept header，不然导致406
        connection.setRequestProperty("Accept", "*/*");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String line;
        StringBuilder str = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            str.append(line);
        }

        System.out.println(str.toString());

        JSONObject jb = new JSONObject(str.toString());

        boolean hasComment = jb.getBoolean("comment_exist");
        if(hasComment){
            int pages = jb.getInt("total_pages");
            if(pages > 20){
            //  某些热门文章评论成千上万， 此时没必要全部爬取
                pages = 20;
            }
            int curPage = jb.getInt("page");


            JSONArray jsonArray = jb.getJSONArray("comments");
            Iterator iterator = jsonArray.iterator();
            while(iterator.hasNext()){
                JSONObject comment = (JSONObject) iterator.next();
                JSONArray comChildren = comment.getJSONArray("children");
                JSONObject userObj = comment.getJSONObject("user");
                IdUtils idUtils = new IdUtils();
                CommentDao commentDao = new CommentDao();
                UserDao userDao = new UserDao();


                int commentId = idUtils.genCommentId();
                String userId = idUtils.genUserId();


                System.out.println(comment.toString());
                //    评论内容相关
                String content = comment.getString("compiled_content");
                String time = comment.getString("created_at");
                int floor = comment.getInt("floor");
                int likeNum = comment.getInt("likes_count");
                int parentId = 0;

                //    评论者信息
                String name = userObj.getString("nickname");
                String avatar = userObj.getString("avatar");

                Comment newCom = new Comment(commentId, articleId, content, time, floor, likeNum, parentId, userId, avatar, name);
                commentDao.addComment(newCom);

                User user = new User();
                user.setUser(userId);
                user.setName(name);
                user.setImgAvatar(avatar);

                userDao.addUser(user);

                //    爬取评论中的回复
                Iterator childrenIte = comChildren.iterator();
                while(childrenIte.hasNext()){
                    JSONObject child = (JSONObject) childrenIte.next();

                    Comment childCom = new Comment();
                    childCom.setId(idUtils.genCommentId());
                    childCom.setArticleId(articleId);
                    childCom.setComment(child.getString("compiled_content"));
                    childCom.setTime(child.getString("created_at"));
                    childCom.setParentId(child.getInt("parent_id"));
                    childCom.setFloor(floor);
                    childCom.setNameAuthor(child.getJSONObject("user").getString("nickname"));

                    commentDao.addComment(childCom);

                }
            }

            //  实现自动翻页
            if(curPage == 1){
                for(int i = 2; i <= pages; i++){
                    System.out.println("page-------------------> " + i);
                    int index = url.indexOf("page=");
                    String sub_url = url.substring(0, index + 5);
                    String nextPage = sub_url + i;
                    comSpider(nextPage);
                }
            }
        }
    }
}
