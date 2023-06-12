package com.yigitozdemir.overflowscraper;

public class OverFlowItem {
	private String title;
	private String url;
	private int likes;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	
	public String toString()
	{
		return getTitle()+","+getUrl()+","+getLikes()+System.lineSeparator();
	}
}
