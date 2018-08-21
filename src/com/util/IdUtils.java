package com.util;

import com.dao.CommentDao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class IdUtils {

    /**  评论ID依次递增, 第二此启动自动赋值为数据库表中Id的最大值 */
    private int commentId = 100000;


    public IdUtils() {
        try {
            commentId = new CommentDao().initId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成14位文章ID;
     * 前12位由系统时间的毫秒级表示,
     * 后两位随机数.
     *
     * 在同一秒调用情况下重复概率为1%
     * @return
     */
    public long genArticleId() {
        SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss");
        String time = format.format(new Date());

        int r1 = (int) (Math.random() * 9 + 1);
        int r2 = (int) (Math.random() * 10);
        long id = Long.parseLong(time + r1 + r2);
        return id;
    }

    //    生成6位评论ID
    public int genCommentId() {
        return ++commentId;
    }

    //     生成八位用户ID
    public String genUserId() {
        String letters = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder id = new StringBuilder();
        Random random = new Random();
        //  前四位随机字母，后四位随机数字
        for (int i = 0; i < 4; i++) {
            id.append(letters.charAt(random.nextInt(26)));
        }
        for (int i = 0; i < 4; i++) {
            id.append(random.nextInt(10));
        }
        return id.toString();
    }

}
