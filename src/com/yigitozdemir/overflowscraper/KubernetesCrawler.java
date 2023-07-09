package com.yigitozdemir.overflowscraper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class KubernetesCrawler {
	public final String URLTemplate = "https://stackoverflow.com/questions/tagged/kubernetes?tab=votes&page={1}&pagesize=50";
	public final int MAXIMUM_PAGE = 50;
	public StringBuilder stringBuilder;
	public ArrayList<OverFlowItem> overflowItems;
	public final String CSVPath = "C:\\Users\\010999862\\eclipse-workspace\\my.csv";
	
	public static void main(String[] args) {
		int onPage = 1;
		KubernetesCrawler runner = new KubernetesCrawler();
		runner.initialize(runner);
		
		
		while(onPage <= runner.MAXIMUM_PAGE) {
			try {
				Document page = runner.getPage(runner.URLTemplate.replace("{1}", String.valueOf(onPage)));
				Elements elements = page.getElementsByClass("s-post-summary    js-post-summary");
				
				for(Element el: elements) {
					int likes = runner.getLikes(el);
					Elements aHrefElement = runner.getHrefElement(el);
					String title = aHrefElement.text().replaceAll(System.lineSeparator(), " ").replaceAll("[\r\n]+", " ").replaceAll(",", "");
					String postUrl = "https://stackoverflow.com" + aHrefElement.attr("href");
					
					OverFlowItem ofi = new OverFlowItem();
					ofi.setLikes(likes);
					ofi.setTitle(title);
					ofi.setUrl(postUrl);
					
					runner.overflowItems.add(ofi);
				}
				/**
				 * 
				 */
				//System.out.println(elements.size());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			for(OverFlowItem item: runner.overflowItems)
			{
				runner.stringBuilder.append(item.toString());
			}
			runner.overflowItems.clear();
			System.out.println(onPage + "/" + runner.MAXIMUM_PAGE);
			onPage++;
		}
		
		try {
			runner.dumpToText();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Program completed");
		System.exit(1);
	}
	
	public void dumpToText() throws IOException {
		File file = new File(CSVPath);
		FileUtils.write(file, stringBuilder.toString(), false);
	}
	private Elements getHrefElement(Element el) {
		return el.getElementsByClass("s-link");
	}
	private int getLikes(Element el)
	{
		Elements el2 = el.getElementsByClass("s-post-summary--stats-item-number");
		Element element = el2.get(0);

		return Integer.parseInt(element.text());
	}
	
	private void initialize(KubernetesCrawler runner) {
		runner.stringBuilder = new StringBuilder();
		runner.overflowItems = new ArrayList();
		
		
		runner.stringBuilder.append("Title,Url,Likes");
		runner.stringBuilder.append(System.lineSeparator());
	}

	public Document getPage(String url) throws IOException {
		return Jsoup.connect(url).get();
	}
}
