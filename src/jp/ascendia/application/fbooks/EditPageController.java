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
    private static Book[] initText = null;
    private static int i;

	/**
     * コンストラクタ
     */
    public EditPageController(Book[] labelText, int num) {
        initText = labelText.clone();
        i = num;

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
        TitleField.setText(initText[i].title);
        AuthorField.setText(initText[i].author);
        CompanyField.setText(initText[i].company);
        PubDayField.setText(initText[i].publishday);
        ReadStartField.setText(initText[i].readstart);
        ReadEndField.setText(initText[i].readend);
        MemoField.setText(initText[i].memo);
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
        int fixflg = 0;
        if (!("".equals(TitleField.getText()) || "".equals(AuthorField.getText()) || "".equals(CompanyField.getText()))) {
            TextField[0] = TitleField.getText();
            TextField[1] = AuthorField.getText();
            TextField[2] = CompanyField.getText();
            TextField[3] = PubDayField.getText();
            TextField[4] = ReadStartField.getText();
            TextField[5] = ReadEndField.getText();
            TextField[6] = MemoField.getText();

            DatabaseFbooks db = new DatabaseFbooks();
            String[] SearchText = new String[4];

            SearchText[0] = TextField[0];
            Book[] bookArray = db.searchBook(SearchText);

            if (bookArray.length != 0) {
                //ID、タイトルが同一の場合のみ更新可能、現状タイトルの編集はできない。
                if (bookArray[0].id.equals(initText[i].id) &&  TextField[0].equals(initText[i].title)) {
                    DatabaseFbooks db2 = new DatabaseFbooks();
                    db2.updateBook(TextField);
                    fixflg = 1;
                }
            }
        }

        if (fixflg == 1) {
        	//確定ページへ
            Main.getInstance().sendFixController("編集内容が反映されました。");
        } else {
        	//編集失敗
        	Main.getInstance().sendEditPageController(initText, i);
        }
    }

    //メインページへ
    @FXML
    protected void handleButtonAction() {
        Main.getInstance().sendMainController();
    }
}