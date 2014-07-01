package jp.ascendia.application.fbooks;

import java.time.LocalDate;

/**
 * 書籍情報クラス
 * @version 1.0
 * @author Yutaka Suzuki
 */
public class Book {

  /** 書籍ID */
  private String id;
  /** 書籍タイトル */
  private String title;
  /** 著者 */
  private String author;
  /** 出版社 */
  private String company;
  /** ジャンル */
  private String genre;
  /** 読書開始日 */
  private String readStart;
  /** 読書終了日 */
  private String readEnd;
  /** メモ */
  private String memo;

  /**
   * 全入力値の取得
   *
   * @param title タイトル
   * @param author 著者
   * @param company 出版社
   * @param genre ジャンル
   * @param readStart 読書開始日
   * @param readEnd 読書終了日
   * @param memo メモ
   */
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