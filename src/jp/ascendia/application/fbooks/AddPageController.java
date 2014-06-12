package jp.ascendia.application.fbooks;

import java.io.IOException;
import java.net.URL;
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
    /** 入力テキスト用 */
    private final String[] TextField = new String[10];

    //登録処理
    @FXML
    protected void handleButtonActionAdd() throws ClassNotFoundException {
        int fixflg;
        if ("".equals(TitleField.getText()) || "".equals(AuthorField.getText()) || "".equals(CompanyField.getText())) {
            fixflg = 0;
        } else {
            TextField[0] = TitleField.getText();
            TextField[1] = AuthorField.getText();
            TextField[2] = CompanyField.getText();
            TextField[3] = PubDayField.getText();
            TextField[4] = ReadStartField.getText();
            TextField[5] = ReadEndField.getText();
            TextField[6] = MemoField.getText();

            DatabaseFbooks db = new DatabaseFbooks();
            db.addBook(TextField);
            fixflg = 1;
        }

        if (fixflg == 1) {
        	//確定ページ
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