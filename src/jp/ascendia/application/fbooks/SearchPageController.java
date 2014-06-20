package jp.ascendia.application.fbooks;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


public class SearchPageController extends AnchorPane implements Initializable {
    /**
     * コンストラクタ
     */
    public SearchPageController() {
        loadFXML();
    }

    /**
     * FXMLのロード
     */
    private void loadFXML() {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("SearchPage.fxml"));
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
    private DatePicker ReadStartField;
    @FXML
    private DatePicker ReadEndField;

    //検索ページへ
    @FXML
    protected void handleButtonActionSearRes() throws ClassNotFoundException {
        /** 入力テキスト用 */
        String[] SearchText = new String[4];
        int allflg = 1;

        if (!"".equals(TitleField.getText())) {
            SearchText[0] = TitleField.getText();
            allflg = 0;
        }
        if (!"".equals(AuthorField.getText())) {
            SearchText[1] = AuthorField.getText();
            allflg = 0;
        }
        if (ReadStartField.getValue() != null) {
            SearchText[2] = ReadStartField.getValue().toString();
            allflg = 0;
        }
        if (ReadEndField.getValue() != null) {
            SearchText[3] = ReadEndField.getValue().toString();
            allflg = 0;
        }

        DatabaseFbooks db = new DatabaseFbooks();
        Book[] SearchResult = db.searchBook(SearchText, allflg);

        if(SearchResult.length > 0) {
        	//検索結果表示ページへ
        	Main.getInstance().sendSearchResController(SearchResult, SearchText);
        } else {
        	//検索失敗
            Main.getInstance().sendSearchFixController("見つかりませんでした。");
        }
    }

    //メインページへ
    @FXML
    protected void handleButtonAction() {
        Main.getInstance().sendMainController();
    }
}