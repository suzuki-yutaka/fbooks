package jp.ascendia.application.fbooks;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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
        GenreCBox.setValue(initText[i].getGenre());
        if (initText[i].getReadstart() != null)
        	ReadStartDate.setValue(LocalDate.parse(initText[i].getReadstart()));
        if (initText[i].getReadend() != null)
        	ReadEndDate.setValue(LocalDate.parse(initText[i].getReadend()));
        MemoArea.setText(initText[i].getMemo());
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
    private ComboBox<String> GenreCBox;
    @FXML
    private DatePicker ReadStartDate;
    @FXML
    private DatePicker ReadEndDate;
    @FXML
    private TextArea MemoArea;
    @FXML
    private Label MsgOutput;

    //編集処理
    @FXML
    protected void handleButtonActionEdit() throws ClassNotFoundException{
    	String[] inputText = new String[8];
    	LocalDate start = null, end = null;
    	Book[] SearchResult = null;

    	//必須入力項目が入力されていない場合、更新不可
        if ("".equals(TitleField.getText()) || "".equals(AuthorField.getText()) || "".equals(CompanyField.getText()) ||
        		GenreCBox.getValue() == null) {
        		MsgOutput.setText("必須の入力項目が入力されていません。");
        	return;
        }

        if (ReadStartDate.getValue() != null)
        	start = ReadStartDate.getValue();
        if (ReadEndDate.getValue() != null)
        	end = ReadEndDate.getValue();
        //読書開始日が読書終了日より大きい場合、更新不可
        if (start != null && end != null) {
        	if (start.compareTo(end) > 0) {
        		MsgOutput.setText("読書開始日は読書終了日以前に設定してください。");
        		return;
        	}
        }

        inputText[1] = TitleField.getText();
        inputText[2] = AuthorField.getText();
        inputText[3] = CompanyField.getText();
        inputText[4] = GenreCBox.getValue().toString();
        if (start != null)
        	inputText[5] = start.toString();
        if (end != null)
        	inputText[6] = end.toString();
        inputText[7] = MemoArea.getText();

        DatabaseFbooks db = new DatabaseFbooks();
        String[] Search = new String[5];
        //登録済みタイトルのチェック
        if (inputText[1].compareTo(initText[i].title) != 0) {
            Search[0] = inputText[1];
            SearchResult = db.searchBook(Search, 0);
            if (SearchResult.length > 0) {
                if (!SearchResult[0].id.equals(initText[i].id)) {
                	MsgOutput.setText("登録済みのタイトルです。");
                	return;
                }
            }
        }

        inputText[0] = initText[i].id;
        db.updateBook(inputText);
        Main.getInstance().sendEditFixController("編集内容が反映されました。", SearchText);
    }

    //検索結果ページへ
    @FXML
    protected void handleButtonAction() throws ClassNotFoundException {
    	int allflg = 1;

    	DatabaseFbooks db = new DatabaseFbooks();
    	if (SearchText[0] != null || SearchText[1] != null || SearchText[2] != null ||
    			SearchText[3] != null || SearchText[4] != null)
    		allflg = 0;
    	Book[] SearchResult = db.searchBook(SearchText, allflg);
        Main.getInstance().sendSearchResController(SearchResult, SearchText);
    }
}