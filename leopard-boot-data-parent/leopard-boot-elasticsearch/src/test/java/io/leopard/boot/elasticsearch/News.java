package io.leopard.boot.elasticsearch;

public class News {

	private long newsId;

	private String title;

	private String tag;

	private String publishTime;

	public News() {
		super();
	}

	public News(String title, String tag, String publishTime) {
		super();
		this.title = title;
		this.tag = tag;
		this.publishTime = publishTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public long getNewsId() {
		return newsId;
	}

	public void setNewsId(long newsId) {
		this.newsId = newsId;
	}
}
