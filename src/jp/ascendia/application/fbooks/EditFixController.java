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

    private static String msgText;

    private static String[] SearchText;

    /** ラベル */
    @FXML
    private Label FixField;

    /**
     * コンストラクタ
     */
    public EditFixController(String fText, String[] stext) {
    	msgText = fText;
    	SearchText = stext;
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
    	FixField.setText(msgText);
    	FixField.setStyle("-fx-font-size: 24px");
    }

    /**
     * ボタンクリックアクション
     */
    //検索結果一覧ページへ
    @FXML
    protected void SearchPagehandleButtonAction() throws ClassNotFoundException {
    	int allflg = 1;

    	DatabaseFbooks db = new DatabaseFbooks();
    	if (SearchText[0] != null || SearchText[1] != null || SearchText[2] != null ||
    			SearchText[3] != null || SearchText[4] != null)
    		allflg = 0;
    	Book[] SearchResult = db.searchBook(SearchText, allflg);
        Main.getInstance().SearchResController(SearchResult, SearchText, allflg);

        //編集ウィンドウを閉じる
        SearchResController.editStage.getScene().getWindow().hide();
    }
}