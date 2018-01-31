package com.nivalsoul.webspider.weixin;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;

import com.nivalsoul.webspider.dao.DaoFactory;
import com.nivalsoul.webspider.entity.Article;
import com.nivalsoul.webspider.util.DateUtil;

public class Gongzhonghao {
    static Dao dao = null;
    static String[] types = { "热门", "推荐", "段子手", "养生堂", "私房话", "八卦精", "爱生活", "财经迷", "汽车迷", "科技咖", "潮人帮", "辣妈帮", "点赞党",
            "旅行家", "职场人", "美食家", "古今通", "学霸族", "星座控", "体育迷" };

    public static void main(String[] args) throws Exception {
        // config = new HashMap<String, String>();
        // config.put("driver", "com.mysql.jdbc.Driver");
        // config.put("url", "jdbc:mysql://172.27.8.146:3306/wuji");
        // config.put("userName", "root");
        // config.put("password", "123456");
        // work();
        System.out.println("==========");
    }

    static Map<String, String> config = null;

    /**'
     * 启动抓取文章的定时器
     * @param dbconfig 数据库连接配置，参数为Map格式,包含的key说明如下：<br/>
     * --<strong>driver</strong>       :驱动类名<br/>
     * --<strong>url</strong>            :连接url<br/>
     * --<strong>userName</strong>:用户名<br/>
     * --<strong>password</strong> :密码<br/>
     * <br/>
     * @param hour 定时器的时
     * @param minute 定时器的分
     * @param second 定时器的秒
     * @param period 调度周期（毫秒）
     */
    public static void startTimer(Map<String, String> dbconfig, int hour, int minute, int second, long period) {
        System.out.println("调用了定时器...");
        config = new HashMap<String, String>(dbconfig);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour); // 控制时
        calendar.set(Calendar.MINUTE, minute); // 控制分
        calendar.set(Calendar.SECOND, second); // 控制秒
        // 得出执行任务的时间,此处为今天的06：00：00
        Date time = calendar.getTime();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                System.out.println(DateUtil.getDateTimeString() + "开始执行微信公众号文章抓取任务");
                try {
                    work();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, time, period);// 这里设定将延时每天固定执行
    }

    // 从搜狗获取微信最热文章
    private static void work() throws Exception {
        dao = DaoFactory.getDao(config);
        String url = "http://weixin.sogou.com/pcindex/pc/";
        for (int i = 0; i < 20; i++) {
            String t = "pc_" + i;
            getPage(url + t + "/" + t + ".html", i);
            for (int j = 1; j < 16; j++) {
                try {
                    getPage(url + t + "/" + j + ".html", i);
                } catch (Exception e) {
                    System.out.println("第" + i + "页访问出错");
                    e.printStackTrace();
                    break;
                }
            }
        }
        System.out.println("抓取任务结束！");
    }

    private static void getPage(String url, int k) throws Exception {
        Document doc = Jsoup.connect(url).get();
        Elements users = doc.select("a.account");
        Elements articles = doc.select("div.txt-box");
        System.out.println("访问：" + url);
        System.out.println("文章数：" + articles.size());
        for (int i = 0; i < users.size(); i++) {
            Element user = users.get(i);
            Element article = articles.get(i);
            String author = user.text();
            Element titleLink = article.child(0).select("a").first();
            String title = titleLink.text();
            String link = titleLink.attr("href");
            String summary = article.child(1).text();
            Article a = dao.fetch(Article.class, Cnd.where("article_link", "=", link));
            if (a == null) {
                Article info = new Article();
                info.setSource("微信");
                info.setArticle_type(types[k]);
                info.setArticle_title(title);
                info.setArticle_content(summary);
                info.setArticle_link(link);
                info.setAuthor(author);
                info.setAuthor_name(author);
                info.setOriginal((byte) 0);
                info.setComment_num(0);
                info.setPub_time(new Timestamp(new Date().getTime()));
                dao.insert(info);
            }
        }
    }

}
