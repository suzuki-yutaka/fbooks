package jp.ascendia.application.fbooks;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


public class EditPageController extends AnchorPane implements Initializable {
    private static Book[] initText;
    private static int i;
    private static String[] SearchText;

	/**
     * コンストラクタ
     */
    public EditPageController(Book[] labelText, int num, String[] text) {
        initText = labelText;
        i = num;
        SearchText = text;

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
        TitleField.setText(initText[i].getTitle());
        AuthorField.setText(initText[i].getAuthor());
        CompanyField.setText(initText[i].getCompany());
        PubDayField.setValue(LocalDate.parse(initText[i].getPublishday()));
        if (initText[i].getReadstart() != null)
        	ReadStartField.setValue(LocalDate.parse(initText[i].getReadstart()));
        if (initText[i].getReadend() != null)
        	ReadEndField.setValue(LocalDate.parse(initText[i].getReadend()));
        MemoField.setText(initText[i].getMemo());
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
    private DatePicker PubDayField;
    @FXML
    private DatePicker ReadStartField;
    @FXML
    private DatePicker ReadEndField;
    @FXML
    private TextArea MemoField;
    /** 入力テキスト用 */
    private static String[] TextField = new String[7];
    private static Book[] bookArray;

    //編集処理
    @FXML
    protected void handleButtonActionEdit() throws ClassNotFoundException{

        if (!("".equals(TitleField.getText()) || "".equals(AuthorField.getText()) || "".equals(CompanyField.getText()) ||
        		PubDayField.getValue() == null)) {
            TextField[0] = TitleField.getText();
            TextField[1] = AuthorField.getText();
            TextField[2] = CompanyField.getText();
            TextField[3] = PubDayField.getValue().toString();
            if (ReadStartField.getValue() != null)
            	TextField[4] = ReadStartField.getValue().toString();
            if (ReadEndField.getValue() != null)
            	TextField[5] = ReadEndField.getValue().toString();
            TextField[6] = MemoField.getText();

            DatabaseFbooks db = new DatabaseFbooks();
            String[] Search = new String[4];
            Search[0] = TextField[0];
            bookArray = db.searchBook(Search, 0);

            if (bookArray.length != 0) {
                //ID、タイトルが同一の場合のみ更新可能、現状タイトルの編集はできない。
                if (bookArray[0].id.equals(initText[i].id) &&  TextField[0].equals(initText[i].title)) {
                    db.updateBook(TextField);
                    Main.getInstance().sendEditFixController("編集内容が反映されました。", SearchText);
                }
            }
        } else {
        	//編集失敗
        	Main.getInstance().sendEditPageController(initText, i, SearchText);
        }
    }

    //検索結果ページへ
    @FXML
    protected void handleButtonAction() throws ClassNotFoundException {
    	DatabaseFbooks db = new DatabaseFbooks();
    	bookArray = db.searchBook(SearchText, 0);
        Main.getInstance().sendSearResController(bookArray, SearchText);
    }
}