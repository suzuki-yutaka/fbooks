package jp.ascendia.application.fbooks;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;


public class EditFixController extends AnchorPane implements Initializable {

	/** ラベルに表示するテキスト */
    private final String FixText;

    private final String[] SearchText;

    /** ラベル */
    @FXML
    private Label FixField;

    /**
     * コンストラクタ
     */
    public EditFixController(String FixText, String[] text) {
    	this.FixText = FixText;
    	this.SearchText = text;
        loadFXML();
    }

    /**
     * FXMLのロード
     */
    private void loadFXML() {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("EditFixPage.fxml"));
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
    	FixField.setText(FixText);
    	FixField.setStyle("-fx-font-size: 24px");
    }

    /**
     * ボタンクリックアクション
     */

    //メインページへ
    @FXML
    protected void handleButtonAction() {
        Main.getInstance().sendMainController();
    }
    //検索結果一覧ページへ
    @FXML
    protected void SearchPagehandleButtonAction() throws ClassNotFoundException {
    	DatabaseFbooks db = new DatabaseFbooks();
    	Book[] bookArray = db.searchBook(SearchText, 0);
        Main.getInstance().sendSearResController(bookArray, SearchText);
    }
}