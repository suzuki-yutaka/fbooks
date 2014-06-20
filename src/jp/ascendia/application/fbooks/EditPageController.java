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
    public EditPageController(Book[] SearchResult, int num, String[] text) {
        initText = SearchResult;
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


    //編集処理
    @FXML
    protected void handleButtonActionEdit() throws ClassNotFoundException{
    	String[] inputText = new String[7];

        if (!("".equals(TitleField.getText()) || "".equals(AuthorField.getText()) || "".equals(CompanyField.getText()) ||
        		PubDayField.getValue() == null)) {
            inputText[0] = TitleField.getText();
            inputText[1] = AuthorField.getText();
            inputText[2] = CompanyField.getText();
            inputText[3] = PubDayField.getValue().toString();
            if (ReadStartField.getValue() != null)
            	inputText[4] = ReadStartField.getValue().toString();
            if (ReadEndField.getValue() != null)
            	inputText[5] = ReadEndField.getValue().toString();

            inputText[6] = MemoField.getText();

            DatabaseFbooks db = new DatabaseFbooks();
            String[] Search = new String[4];
            Search[0] = inputText[0];
            Book[] SearchResult = db.searchBook(Search, 0);

            if (SearchResult.length > 0) {
                //ID、タイトルが同一の場合のみ更新可能、現状タイトルの編集はできない。
                if (SearchResult[0].id.equals(initText[i].id) &&  inputText[0].equals(initText[i].title)) {
                    db.updateBook(inputText);
                    Main.getInstance().sendEditFixController("編集内容が反映されました。", SearchText);
                }
            }
        }
    }

    //検索結果ページへ
    @FXML
    protected void handleButtonAction() throws ClassNotFoundException {
    	int allflg = 0;

    	DatabaseFbooks db = new DatabaseFbooks();
    	if (SearchText[0] == null && SearchText[1] == null && SearchText[2] == null && SearchText[3] == null)
    		allflg = 1;
    	Book[] SearchResult = db.searchBook(SearchText, allflg);
        Main.getInstance().sendSearchResController(SearchResult, SearchText);
    }
}