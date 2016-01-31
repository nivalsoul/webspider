package com.nivalsoul.webspider.techinfo;

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

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.nivalsoul.webspider.dao.DaoFactory;
import com.nivalsoul.webspider.entity.Article;
import com.nivalsoul.webspider.entity.TechInfo;
import com.nivalsoul.webspider.util.DateUtil;


public class Tuicool {
	static Dao dao = null;
	static String[] types = {"热门", "科技","创投","数码","技术"};
	static String[] urls = {"http://www.tuicool.com/ah/0",
			"http://www.tuicool.com/ah/101000000",
			"http://www.tuicool.com/ah/101040000",
			"http://www.tuicool.com/ah/101050000",
			"http://www.tuicool.com/ah/20"};
	
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
				System.out.println(DateUtil.getDateTimeString()+"开始执行推酷文章抓取任务");
				try {
					work();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, time, period);// 这里设定将延时每天固定执行
	}

	// 从推酷获取文章
	private static void work() throws Exception {
		dao = DaoFactory.getDao(config);
		for(int i = 0;i<5;i++){
			String url=urls[i];
			for (int j = 0; j < 10; j++) {
				try {
					getPage(url+"/"+j+"?lang=1", i);
				} catch (Exception e) {
					System.out.println("第"+i+"页访问出错");
					e.printStackTrace();
					break;
				}
				try {
		            Thread.sleep(5000);
		        } catch (InterruptedException e) {
		            e.printStackTrace(); 
		        }
			}
		}
		System.out.println("抓取任务结束！");
	}

	private static void getPage(String url, int k) throws Exception {
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
		Elements articles = doc.select("div#list_article div.single_fake");
		System.out.println("访问："+url);
		System.out.println("文章数："+articles.size());
		for(int i=0;i<articles.size();i++){
			Element article = articles.get(i);
			Element titleLink = article.select("div.article_title a").first();
			String title = titleLink.text();
			String link = "http://www.tuicool.com" + titleLink.attr("href");
			String summary = article.select("div.article_cut").text();
			Element tip = article.select("div.tip").first();
			String author = tip.child(0).text();
			String time = tip.child(1).text();
			if (time.length()==11) {
				time = DateUtil.getDateString().substring(0, 5)+time+":00";
			}
			Timestamp pubTime = null;
			try {
				pubTime = DateUtil.getTimestamp(time);
			} catch (Exception e) {
				pubTime = DateUtil.getTimestamp();
			}
			TechInfo a = dao.fetch(TechInfo.class, Cnd.where("article_link", "=", link));
			if(a==null){
				TechInfo info = new TechInfo();
				info.setSource("推酷");
				info.setArticle_type(types[k]);
				info.setArticle_title(title);
				info.setArticle_content(summary);
				info.setArticle_link(link);
				info.setAuthor(author);
				info.setAuthor_link("");;
				info.setOriginal((byte) 0);
				info.setComment_num(0);
				info.setPub_time(pubTime);
				dao.insert(info);
			}
		}
	}

}
