package jp.ascendia.application.fbooks;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class AddPageController extends AnchorPane implements Initializable {
    /**
     * コンストラクタ
     */
    public AddPageController() {
        loadFXML();
    }

    /**
     * FXMLのロード
     */
    private void loadFXML() {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("AddPage.fxml"));
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
    }

    /**
     * ボタンクリックアクション
     */
    @FXML
    private TextField TitleField;
    @FXML
    private TextField AuthorField;
    @FXML
    private TextField CompanyField;
    @FXML
    private TextField PubDayField;
    @FXML
    private TextField ReadStartField;
    @FXML
    private TextField ReadEndField;
    @FXML
    private TextArea MemoField;

    //登録処理
    @FXML
    protected void handleButtonActionAdd() throws ClassNotFoundException {
        int fixflg = 0;
        if ("".equals(TitleField.getText()) || "".equals(AuthorField.getText()) || "".equals(CompanyField.getText())) {
        	fixflg = 0;
        } else {
            Class.forName("org.sqlite.JDBC");//services入ってない。1.6でもforNameする必要がある？
            Connection conn = null;
            try {
                conn = DriverManager.getConnection("jdbc:sqlite:book.db");
                /*
            	Statement stmt = conn.createStatement();
            	//テーブル作成。
            	// 実行後getConnection時に指定したbook.dbという名前のファイルが作成される。
            	stmt.execute( "CREATE TABLE book_table( b_id INTEGER PRIMARY KEY AUTOINCREMENT, b_title TEXT NOT NULL UNIQUE, b_author TEXT NOT NULL,"
            		+ " b_company TEXT NOT NULL, b_pub_day NUMERIC NOT NULL, b_read_start NUMERIC, b_read_end NUMERIC, b_memo TEXT )" );
                 */
                // INSERT。
                PreparedStatement statement = conn.prepareStatement( "INSERT INTO book_table(b_title, b_author, b_company, b_pub_day, b_read_start, b_read_end, b_memo) VALUES "
                		+ "(?, ?, ?, ?, ?, ?, ?)" );
                statement.setString(1, TitleField.getText());
                statement.setString(2, AuthorField.getText());
                statement.setString(3, CompanyField.getText());
                statement.setString(4, PubDayField.getText());
                statement.setString(5, ReadStartField.getText());
                statement.setString(6, ReadEndField.getText());
                statement.setString(7, MemoField.getText());
                statement.executeUpdate();
                fixflg = 1;
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

        //確定ページへ
        if (fixflg == 1) {
        	Main.getInstance().sendFixController("登録されました。");
        } else {
        	//登録失敗
        	Main.getInstance().sendAddPageController();
        }
    }

    //メインページへ
	@FXML
    protected void handleButtonAction() {
        Main.getInstance().sendMainController();
    }
}