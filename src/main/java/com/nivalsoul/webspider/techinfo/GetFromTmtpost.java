package com.nivalsoul.webspider.techinfo;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.nivalsoul.webspider.dao.DaoFactory;
import com.nivalsoul.webspider.entity.Article;
import com.nivalsoul.webspider.entity.TechInfo;
import com.nivalsoul.webspider.util.DateUtil;

/**
 * 抓取钛媒体文章
 * @author nivalsoul
 *
 */
public class GetFromTmtpost {
	static Dao dao = null;
	static String[] types = {"最新", "热文","推荐"};
	static String[] urls = {"http://www.tmtpost.com/new",
			"http://www.tmtpost.com/hot",
			"http://www.tmtpost.com"};
	
	public static void main(String[] args) throws Exception {
		work();
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
	 public static void startTimer(Map<String, String> dbconfig, 
			 int hour, int minute, int second, long period) {
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
				System.out.println(DateUtil.getDateTimeString()+"开始执行钛媒体文章抓取任务");
				try {
					work();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, time, period);// 这里设定将延时每天固定执行
	}

	private static void work() throws Exception {
		dao = DaoFactory.getDao(config);
		int k=1;
		while(k<6){
			String url = urls[0]+"/"+k;
			getArticlesByNew(url);
			k++;
		}
		k=1;
		while(k<6){
			String url = urls[1]+"/"+k;
			getArticlesByHot(url);
			k++;
		}
		
		System.out.println("抓取任务结束！");
	}

	private static String getArticlesByNew(String url) throws Exception {
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_38);  
        //设置webClient的相关参数  
        webClient.getOptions().setJavaScriptEnabled(true);  
        webClient.getOptions().setCssEnabled(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());  
        //webClient.getOptions().setTimeout(50000);  
        webClient.getOptions().setThrowExceptionOnScriptError(false);  
        //模拟浏览器打开一个目标网址  
        HtmlPage rootPage = webClient.getPage(url);  
        System.out.println("为了获取js执行的数据 线程开始沉睡等待");  
        Thread.sleep(3000);//主要是这个线程的等待 因为js加载也是需要时间的  
        System.out.println("线程结束沉睡");  
        String html = rootPage.asXml();  
		//转为Jsoup的dom对象
		Document doc = Jsoup.parse(html);
		Elements articles = doc.select("ul.latest-list li");
		System.out.println("访问："+url);
		System.out.println("文章数："+articles.size());
		for(int i=0;i<articles.size();i++){
			Element article = articles.get(i);
			Element titleLink = article.select("a").first();
			String title = titleLink.text();
			String link = "http://www.tmtpost.com" + titleLink.attr("href");
			String time = article.select("time").text();
			Timestamp pubTime = null;
			try {
				pubTime = DateUtil.getTimestamp(time+":00");
			} catch (Exception e) {
				pubTime = DateUtil.getTimestamp();
			}
			TechInfo a = dao.fetch(TechInfo.class, Cnd.where("article_link", "=", link));
			if(a==null){
				TechInfo info = new TechInfo();
				info.setSource("钛媒体");
				info.setArticle_type("最新");
				info.setArticle_title(title);
				info.setArticle_content("");
				info.setArticle_link(link);
				info.setAuthor("钛媒体");
				info.setAuthor_link("");
				info.setOriginal((byte) 0);
				info.setComment_num(0);
				info.setPub_time(pubTime);
				dao.insert(info);
			}
		}
		return null;
	}
	
	private static String getArticlesByHot(String url) throws Exception {
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_38);  
        //设置webClient的相关参数  
        webClient.getOptions().setJavaScriptEnabled(true);  
        webClient.getOptions().setCssEnabled(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());  
        //webClient.getOptions().setTimeout(50000);  
        webClient.getOptions().setThrowExceptionOnScriptError(false);  
        //模拟浏览器打开一个目标网址  
        HtmlPage rootPage = webClient.getPage(url);  
        System.out.println("为了获取js执行的数据 线程开始沉睡等待");  
        Thread.sleep(3000);//主要是这个线程的等待 因为js加载也是需要时间的  
        System.out.println("线程结束沉睡");  
        String html = rootPage.asXml();  
		//转为Jsoup的dom对象
		Document doc = Jsoup.parse(html);
		Elements articles = doc.select("div.mod-article-list ul li");
		System.out.println("访问："+url);
		System.out.println("文章数："+articles.size());
		for(int i=0;i<articles.size();i++){
			Element article = articles.get(i);
			Element titleLink = article.select("div.cont h2 a").first();
			String title = titleLink.text().substring(3);
			String link = "http://www.tmtpost.com" + titleLink.attr("href");
			Element des = article.select("div.cont div.info").first();
			Element authorInfo = des.select("span.author a.name").first();
			String author = authorInfo.text();
			String authorLink = "http://www.tmtpost.com" + authorInfo.attr("href");
			String time = des.select("span.author").text().split(" • ")[1];
			Timestamp pubTime = null;
			try {
				pubTime = DateUtil.getTimestamp(time+":00");
			} catch (Exception e) {
				pubTime = DateUtil.getTimestamp();
			}
			String summary = article.select("div.cont p.intro").text();
			Elements tags = article.select("div.cont div.tag a");
			String tg="";
			for (Element e : tags) {
				tg += e.text()+"#";
			}
			TechInfo a = dao.fetch(TechInfo.class, Cnd.where("article_link", "=", link));
			if(a==null){
				TechInfo info = new TechInfo();
				info.setSource("钛媒体");
				info.setArticle_type("热门");
				info.setArticle_title(title);
				info.setArticle_content(tg+"#"+summary);
				info.setArticle_link(link);
				info.setAuthor(author);
				info.setAuthor_link(authorLink);
				info.setOriginal((byte) 0);
				info.setComment_num(0);
				info.setPub_time(pubTime);
				dao.insert(info);
			}
		}
		return null;
	}

	/**
	 * 获取36氪文章，返回下一页链接
	 * @param url
	 * @return
	 * @throws Exception
	 */
	private static String getArticlesFrom36kr(String url) throws Exception {
        WebClient webClient = new WebClient(BrowserVersion.FIREFOX_38);  
        //设置webClient的相关参数  
        webClient.getOptions().setJavaScriptEnabled(true);  
        webClient.getOptions().setCssEnabled(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());  
        //webClient.getOptions().setTimeout(50000);  
        webClient.getOptions().setThrowExceptionOnScriptError(false);  
        //模拟浏览器打开一个目标网址  
        HtmlPage rootPage = webClient.getPage(url);  
        System.out.println("为了获取js执行的数据 线程开始沉睡等待");  
        Thread.sleep(3000);//主要是这个线程的等待 因为js加载也是需要时间的  
        System.out.println("线程结束沉睡");  
        String html = rootPage.asXml();  
        //FileUtils.write(new File("G:\\36kr.html"), html);
		//转为Jsoup的dom对象
		Document doc = Jsoup.parse(html);
		Elements articles = doc.select("article");
		System.out.println("访问："+url);
		System.out.println("文章数："+articles.size());
		for(int i=0;i<articles.size();i++){
			try {
				Element article = articles.get(i);
				Element desc = article.select("div.desc").first();
				if(desc.select("div.author").size()==0)//推广文章则跳过
					continue;
				Element titleLink = desc.child(0);
				String title = titleLink.text();
				String link = "http://36kr.com"+titleLink.attr("href");
				Element authorInfo = desc.select("div.author").first();
				String author = authorInfo.child(0).select("span.name").first().text();
				String authorLink = "http://36kr.com"+authorInfo.child(0).attr("href");
				String time = authorInfo.select("span.time .timeago").attr("title");
				Timestamp pubTime = DateUtil.getTimestamp(time);
				String summary = desc.child(2).text();
				String type = article.child(0).select("span.mask-tags").text();
				TechInfo a = dao.fetch(TechInfo.class, Cnd.where("article_link", "=", link));
				if(a==null){
					TechInfo info = new TechInfo();
					info.setSource("36氪");
					info.setArticle_type(type);
					info.setArticle_title(title);
					info.setArticle_content(summary);
					info.setArticle_link(link);
					info.setAuthor(author);
					info.setAuthor_link(authorLink);
					info.setOriginal((byte) 0);
					info.setComment_num(0);
					info.setPub_time(pubTime);
					dao.insert(info);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//下一页链接
		String nextPageUrl = "http://36kr.com"+doc.select("a#info_flows_next_link").attr("href");
		return nextPageUrl;
	}

}
