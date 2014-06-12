package jp.ascendia.application.fbooks;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DatabaseFbooks {
    Connection connection;
    static final String NAME = "org.sqlite.JDBC";
    static final String DATABASE_URL = "jdbc:sqlite:book.db";

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

    private final String[] labelText = new String[10];
    public String[] searchBook(String[] TextField) {
        try {
            connect();

            PreparedStatement statement = connection.prepareStatement(
            		"SELECT * FROM book_table WHERE b_title = ? or b_author = ? or b_read_start = ? or b_read_end = ?" );
            statement.setString(1, TextField[0]);
            statement.setString(2, TextField[1]);
            statement.setString(3, TextField[2]);
            statement.setString(4, TextField[3]);

            ResultSet rs = statement.executeQuery();
            while( rs.next() ) {
                labelText[0] = rs.getString( 2 );
                labelText[1] = rs.getString( 3 );
                labelText[2] = rs.getString( 4 );
                labelText[3] = rs.getString( 5 );
                labelText[4] = rs.getString( 6 );
                labelText[5] = rs.getString( 7 );
                labelText[6] = rs.getString( 8 );
            }

            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return labelText;
    }

    public void addBook(String[] TextField) {
        try {
            connect();

/*
 *  Statement stmt = conn.createStatement();
 *  //テーブル作成。
 *  // 実行後getConnection時に指定したbook.dbという名前のファイルが作成される。
 *  stmt.execute( "CREATE TABLE book_table( b_id INTEGER PRIMARY KEY AUTOINCREMENT, b_title TEXT NOT NULL UNIQUE, b_author TEXT NOT NULL,"
 *  		+ " b_company TEXT NOT NULL, b_pub_day NUMERIC NOT NULL, b_read_start NUMERIC, b_read_end NUMERIC, b_memo TEXT )" );
 *
 */
            PreparedStatement statement = connection.prepareStatement(
            		"INSERT INTO book_table(b_title, b_author, b_company, b_pub_day, b_read_start, b_read_end, b_memo) "
            		+ "VALUES (?, ?, ?, ?, ?, ?, ?)" );
            statement.setString(1, TextField[0]);
            statement.setString(2, TextField[1]);
            statement.setString(3, TextField[2]);
            statement.setString(4, TextField[3]);
            statement.setString(5, TextField[4]);
            statement.setString(6, TextField[5]);
            statement.setString(7, TextField[6]);
            statement.executeUpdate();

            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateBook(String[] TextField) {
        try {
            connect();

            PreparedStatement statement = connection.prepareStatement(
            		"UPDATE book_table SET b_title = ?, b_author = ?, b_company = ?, b_pub_day = ?, b_read_start = ?, b_read_end = ?, b_memo = ?" );
            statement.setString(1, TextField[0]);
            statement.setString(2, TextField[1]);
            statement.setString(3, TextField[2]);
            statement.setString(4, TextField[3]);
            statement.setString(5, TextField[4]);
            statement.setString(6, TextField[5]);
            statement.setString(7, TextField[6]);
            statement.executeUpdate();

            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteBook(String[] labelText) {
        try {
            connect();

            PreparedStatement statement = connection.prepareStatement("DELETE FROM book_table WHERE b_title = ?");
            statement.setString(1, labelText[0]);
            statement.executeUpdate();

            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}