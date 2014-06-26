package jp.ascendia.application.fbooks;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class DatabaseFbooks {
  Connection connection;
  static final String NAME = "org.sqlite.JDBC";
  static final String DATABASE_URL = "jdbc:sqlite:book_table.db";

  public DatabaseFbooks() {
    connect();
  }

  protected boolean connect() {
    try {
      if (connection != null) {
        if (connection.getWarnings() == null)
          return true;
        disConnect();
      }
      Class.forName(NAME);
      connection = DriverManager.getConnection(DATABASE_URL);

      if (connection.getWarnings() == null)
        return true;
      disConnect();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return false;
  }

  protected void disConnect() {
    if (connection != null) {
      try {
        connection.close();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
      connection = null;
    }
  }

  public void addBook(Book book) {
    try {
      connect();

      PreparedStatement statement = connection.prepareStatement(
          "INSERT INTO book_table(b_title, b_author, b_company, b_genre, b_read_start, b_read_end, b_memo) "
              + "VALUES (?, ?, ?, ?, date(?), date(?), ?)");
      statement.setString(1, book.getTitle());
      statement.setString(2, book.getAuthor());
      statement.setString(3, book.getCompany());
      statement.setString(4, book.getGenre());
      statement.setString(5, book.getReadStart());
      statement.setString(6, book.getReadEnd());
      statement.setString(7, book.getMemo());
      statement.executeUpdate();

      statement.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  public void updateBook(Book book) {
    try {
      connect();

      PreparedStatement statement = connection.prepareStatement(
          "UPDATE book_table SET b_title = ?, b_author = ?, b_company = ?, b_genre = ?,"
              + " b_read_start = ?, b_read_end = ?, b_memo = ? WHERE b_id = ?");
      statement.setString(1, book.getTitle());
      statement.setString(2, book.getAuthor());
      statement.setString(3, book.getCompany());
      statement.setString(4, book.getGenre());
      statement.setString(5, book.getReadStart());
      statement.setString(6, book.getReadEnd());
      statement.setString(7, book.getMemo());
      statement.setString(8, book.getId());
      statement.executeUpdate();

      statement.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  public void deleteBook(Book book) {
    try {
      connect();

      PreparedStatement statement = connection.prepareStatement(
          "DELETE FROM book_table WHERE b_id = ?");
      statement.setString(1, book.getId());
      statement.executeUpdate();

      statement.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  public Book[] searchBook(Book book, int allflg) {
    Book[] bookArray = null;
    try {
      PreparedStatement statement;
      connect();

      if (allflg == 1) {
        statement = connection.prepareStatement(
            "SELECT * FROM book_table");
      } else {
        statement = connection.prepareStatement(
            "SELECT * FROM book_table WHERE b_title = ? or b_author = ? or b_genre = ?"
                + " or b_read_start = ? or b_read_end = ?");
        statement.setString(1, book.getTitle());
        statement.setString(2, book.getAuthor());
        statement.setString(3, book.getGenre());
        statement.setString(4, book.getReadStart());
        statement.setString(5, book.getReadEnd());
      }

      ResultSet rs = statement.executeQuery();
      bookArray = createBooks(rs);

      statement.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    return bookArray;
  }

  protected Book createBook(ResultSet rs) throws SQLException {
    Book book = new Book();
    book.id = rs.getString("b_id");
    book.title = rs.getString("b_title");
    book.author = rs.getString("b_author");
    book.company = rs.getString("b_company");
    book.genre = rs.getString("b_genre");
    book.readStart = rs.getString("b_read_start");
    book.readEnd = rs.getString("b_read_end");
    book.memo = rs.getString("b_memo");

    return book;
  }

  protected Book[] createBooks(ResultSet rs) throws SQLException {
    Vector<Book> books = new Vector<Book>();

    while (rs.next())
      books.add(createBook(rs));

    Book[] bookArray = new Book[books.size()];
    books.toArray(bookArray);

    return bookArray;
  }
}