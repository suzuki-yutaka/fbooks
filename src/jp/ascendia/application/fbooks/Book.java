package jp.ascendia.application.fbooks;

import java.time.LocalDate;

public class Book {
  protected String id;
  protected String title;
  protected String author;
  protected String company;
  protected String genre;
  protected String readStart;
  protected String readEnd;
  protected String memo;

  public void setAll(String title, String author, String company,
      String genre, LocalDate readStart, LocalDate readEnd, String memo) {
    this.title = title;
    this.author = author;
    this.company = company;
    if (genre != null)
      this.genre = genre;
    if (readStart != null)
      this.readStart = readStart.toString();
    if (readEnd != null)
      this.readEnd = readEnd.toString();
    this.memo = memo;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }

  public void setReadStart(String readStart) {
    this.readStart = readStart;
  }

  public void setReadEnd(String readEnd) {
    this.readEnd = readEnd;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

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

  public String getReadStart() {
    return readStart;
  }

  public String getReadEnd() {
    return readEnd;
  }

  public String getMemo() {
    return memo;
  }
}