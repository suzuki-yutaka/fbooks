package jp.ascendia.application.fbooks;

public class Book {
    protected String id;
    protected String title;
    protected String author;
    protected String company;
    protected String publishday;
    protected String readstart;
    protected String readend;
    protected String memo;

	public String getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public String getAuthor() {
		return author;
	}
	public String getCompany() {
		return company;
	}
	public String getPublishday() {
		return publishday;
	}
	public String getReadstart() {
		return readstart;
	}
	public String getReadend() {
		return readend;
	}
	public String getMemo() {
		return memo;
	}
}