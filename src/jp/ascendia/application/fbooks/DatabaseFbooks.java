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
    static final String DATABASE_URL = "jdbc:sqlite:C:/Users/Owner/Desktop/Fbooks_common/book_table.db";
    public DatabaseFbooks() {
        connect();
    }

    protected boolean connect() {
        try {
            if (connection != null) {
                if (connection.getWarnings() == null)
                    return true;
                disconnect();
            }
            Class.forName(NAME);
            connection = DriverManager.getConnection(DATABASE_URL);

            if (connection.getWarnings() == null)
                return true;
            disconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    protected void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            connection = null;
        }
    }

    public void addBook(String[] text) {
        try {
            connect();

            PreparedStatement statement = connection.prepareStatement(
            		"INSERT INTO book_table(b_title, b_author, b_company, b_genre, b_read_start, b_read_end, b_memo) "
            		+ "VALUES (?, ?, ?, ?, date(?), date(?), ?)" );
            statement.setString(1, text[0]);
            statement.setString(2, text[1]);
            statement.setString(3, text[2]);
            statement.setString(4, text[3]);
            statement.setString(5, text[4]);
            statement.setString(6, text[5]);
            statement.setString(7, text[6]);
            statement.executeUpdate();

            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateBook(String[] text) {
        try {
            connect();

            PreparedStatement statement = connection.prepareStatement(
            		"UPDATE book_table SET b_title = ?, b_author = ?, b_company = ?, b_genre = ?,"
            		+ " b_read_start = ?, b_read_end = ?, b_memo = ? WHERE b_id = ?" );
            statement.setString(1, text[1]);
            statement.setString(2, text[2]);
            statement.setString(3, text[3]);
            statement.setString(4, text[4]);
            statement.setString(5, text[5]);
            statement.setString(6, text[6]);
            statement.setString(7, text[7]);
            statement.setString(8, text[0]);
            statement.executeUpdate();

            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteBook(Book[] text, int i) {
        try {
            connect();

            PreparedStatement statement = connection.prepareStatement(
            		"DELETE FROM book_table WHERE b_title = ?");
            statement.setString(1, text[i].title);
            statement.executeUpdate();

            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Book[] searchBook(String[] text, int allflg) {
        Book[] bookArray = null;
        try {
        	PreparedStatement statement;
            connect();

            if (allflg == 1) {
                statement = connection.prepareStatement(
                		"SELECT * FROM book_table" );
            } else {
                statement = connection.prepareStatement(
                		"SELECT * FROM book_table WHERE b_title = ? or b_author = ? or b_genre = ?"
                		+ " or b_read_start = ? or b_read_end = ?" );
                statement.setString(1, text[0]);
                statement.setString(2, text[1]);
                statement.setString(3, text[2]);
                statement.setString(4, text[3]);
                statement.setString(5, text[4]);
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
        book.readstart = rs.getString("b_read_start");
        book.readend = rs.getString("b_read_end");
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