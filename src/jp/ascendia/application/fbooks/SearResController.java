package jp.ascendia.application.fbooks;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;


public class SearResController extends AnchorPane implements Initializable {
    /** ラベルに表示するテキスト */
	private final String[] labelText = new String[10];

    /** ラベル */
    @FXML
    private Label TitleField;
    @FXML
    private Label AuthorField;
    @FXML
    private Label CompanyField;
    @FXML
    private Label PubDayField;
    @FXML
    private Label ReadStartField;
    @FXML
    private Label ReadEndField;
    @FXML
    private Label MemoField;

    /**
     * コンストラクタ
     */
    public SearResController(String[] SearchText) throws ClassNotFoundException {
        DbSearch(SearchText);
        loadFXML();
    }

    public void DbSearch(String[] SearchText) throws ClassNotFoundException {
        // 準備。
        Class.forName("org.sqlite.JDBC");//services入ってない。1.6でもforNameする必要がある？
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:book.db");
            PreparedStatement statement = conn.prepareStatement( "SELECT * FROM book_table WHERE b_title = ? or b_author = ? or b_read_start = ? or b_read_end = ?" );
            statement.setString(1, SearchText[0]);
            statement.setString(2, SearchText[1]);
            statement.setString(3, SearchText[2]);
            statement.setString(4, SearchText[3]);
            ResultSet rs = statement.executeQuery();
            /*
             * Statement stmt = conn.createStatement();
             * ResultSet rs = stmt.executeQuery( "SELECT * FROM book_table" );
             * */
            while( rs.next() ) {
                System.out.println( rs.getInt( 1 ) );
                System.out.println( rs.getString( 2 ) );
                System.out.println( rs.getString( 3 ) );
                System.out.println( rs.getString( 4 ) );
                System.out.println( rs.getString( 5 ) );
                System.out.println( rs.getString( 6 ) );
                System.out.println( rs.getString( 7 ) );
                System.out.println( rs.getString( 8 ) );

                labelText[0] = rs.getString( 2 );
                labelText[1] = rs.getString( 3 );
                labelText[2] = rs.getString( 4 );
                labelText[3] = rs.getString( 5 );
                labelText[4] = rs.getString( 6 );
                labelText[5] = rs.getString( 7 );
                labelText[6] = rs.getString( 8 );
            }
        } catch (SQLException e) {
        	System.err.println(e.getMessage());
        } finally {
            try {
            // 閉じる
            	conn.close();
            } catch (SQLException e) {
            	System.err.println(e.getMessage());
            }
        }
    }

    /**
     * FXMLのロード
     */
    private void loadFXML() {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("SearchResPage.fxml"));
        fxmlLoader.setRoot(this);

        // 自分自身をコントロールとして設定
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TitleField.setText(labelText[0]);
        AuthorField.setText(labelText[1]);
        CompanyField.setText(labelText[2]);
        PubDayField.setText(labelText[3]);
        ReadStartField.setText(labelText[4]);
        ReadEndField.setText(labelText[5]);
        MemoField.setText(labelText[6]);
    }

    /**
     * ボタンクリックアクション
     */

    //編集ページへ
    @FXML
    protected void handleButtonActionEditPage() {
        Main.getInstance().sendEditPageController();
    }

    //削除処理
    @FXML
    protected void handleButtonActionDel() throws ClassNotFoundException {
        // 準備。
        Class.forName("org.sqlite.JDBC");//services入ってない。1.6でもforNameする必要がある？
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:book.db");
            Statement stmt = conn.createStatement();
            //DELETE
            stmt.executeUpdate("DELETE FROM book_table");
        } catch (SQLException e) {
        	System.err.println(e.getMessage());
        } finally {
            try {
            // 閉じる
            	conn.close();
            } catch (SQLException e) {
            	System.err.println(e.getMessage());
            }
        }

        //確定ページへ
        Main.getInstance().sendFixController("削除されました。");
    }

    //メインページへ
    @FXML
    protected void handleButtonAction() {
        Main.getInstance().sendMainController();
    }
}