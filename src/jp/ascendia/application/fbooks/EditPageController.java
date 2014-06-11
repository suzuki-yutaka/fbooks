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


public class EditPageController extends AnchorPane implements Initializable {
    /**
     * コンストラクタ
     */
    public EditPageController() {
        loadFXML();
    }

    /**
     * FXMLのロード
     */
    private void loadFXML() {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("EditPage.fxml"));
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

    //編集処理
    @FXML
    protected void handleButtonActionEdit() throws ClassNotFoundException{
        int fixflg = 0;
        if ("".equals(TitleField.getText()) || "".equals(AuthorField.getText()) || "".equals(CompanyField.getText())) {
        	fixflg = 0;
        } else {
            Class.forName("org.sqlite.JDBC");//services入ってない。1.6でもforNameする必要がある？
            Connection conn = null;
            try {
                conn = DriverManager.getConnection("jdbc:sqlite:book.db");
                //Statement stmt = conn.createStatement();
                PreparedStatement statement = conn.prepareStatement( "UPDATE book_table SET b_title = ?, b_author = ?, b_company = ?, b_pub_day = ?, b_read_start = ?, b_read_end = ?, b_memo = ?" );
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
            Main.getInstance().sendFixController("編集内容が反映されました。");
        } else {
        	Main.getInstance().sendEditPageController();
        }
    }

    //メインページへ
    @FXML
    protected void handleButtonAction() {
        Main.getInstance().sendMainController();
    }
}