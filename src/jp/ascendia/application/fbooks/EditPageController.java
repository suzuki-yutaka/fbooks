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
        TitleField.setText("初期値");
        AuthorField.setText("初期値");
        CompanyField.setText("初期値");
        PubDayField.setText("初期値");
        ReadStartField.setText("初期値");
        ReadEndField.setText("初期値");
        MemoField.setText("初期値");
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

    //編集処理
    @FXML
    protected void handleButtonActionEdit() throws ClassNotFoundException{
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
            db.updateBook(TextField);
            fixflg = 1;
        }

        if (fixflg == 1) {
        	//確定ページへ
            Main.getInstance().sendFixController("編集内容が反映されました。");
        } else {
        	//編集失敗
        	Main.getInstance().sendEditPageController();
        }
    }

    //メインページへ
    @FXML
    protected void handleButtonAction() {
        Main.getInstance().sendMainController();
    }
}