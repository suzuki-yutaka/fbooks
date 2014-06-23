package jp.ascendia.application.fbooks;

public class Book {
    protected String id;
    protected String title;
    protected String author;
    protected String company;
    protected String genre;
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
	public String getGenre() {
		return genre;
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