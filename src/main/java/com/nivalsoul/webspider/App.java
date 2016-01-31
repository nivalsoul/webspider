package com.nivalsoul.webspider;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        String url = "http://www.tuicool.com/ah/0";  
        try {  
        	jsonup(url);
            //htmlunit(url);  
        } catch (Exception e) {  
        	;
        }  
        

    }

	private static void jsonup(String url) throws Exception {
		Document doc = Jsoup.connect(url).get();
		System.out.println(doc.html());
	}

	private static void htmlunit(String url) throws Exception {
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
		System.out.println(html);
	}
}
