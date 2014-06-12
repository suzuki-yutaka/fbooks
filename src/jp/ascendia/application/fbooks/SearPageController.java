package jp.ascendia.application.fbooks;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


public class SearPageController extends AnchorPane implements Initializable {
    /**
     * コンストラクタ
     */
    public SearPageController() {
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
    private TextField ReadStartField;
    @FXML
    private TextField ReadEndField;

    /** 入力テキスト用 */
    private final String[] SearchText = new String[4];
    /** 検索結果用 */
    private String[] labelText = new String[10];

    //検索ページへ
    @FXML
    protected void handleButtonActionSearRes() throws ClassNotFoundException {
        SearchText[0] = TitleField.getText();
        SearchText[1] = AuthorField.getText();
        SearchText[2] = ReadStartField.getText();
        SearchText[3] = ReadEndField.getText();

        DatabaseFbooks db = new DatabaseFbooks();
        labelText = db.searchBook(SearchText);

        if("".equals(labelText[0]) || labelText[0] == null) {
        	//検索失敗
        	Main.getInstance().sendFixController("見つかりませんでした。");
        } else {
        	//検索結果表示ページへ
        	Main.getInstance().sendSearResController(labelText);
        }
    }

    //メインページへ
    @FXML
    protected void handleButtonAction() {
        Main.getInstance().sendMainController();
    }
}