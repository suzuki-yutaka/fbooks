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
    private ComboBox<String> GenreCBox;
    @FXML
    private DatePicker ReadStartDate;
    @FXML
    private DatePicker ReadEndDate;
    @FXML
    private TextArea MemoArea;
    @FXML
    private Label MsgOutput;

    //登録処理
    @FXML
    protected void handleButtonActionAdd() throws ClassNotFoundException {
        String[] inputText = new String[7];
        LocalDate start = null, end = null;

        //必須入力項目が入力されていない場合、登録不可
        if ("".equals(TitleField.getText()) || "".equals(AuthorField.getText()) || "".equals(CompanyField.getText()) ||
        		GenreCBox.getValue() == null) {
        	MsgOutput.setText("必須の入力項目が入力されていません。");
        	return;
        }

        if (ReadStartDate.getValue() != null)
        	start = ReadStartDate.getValue();
        if (ReadEndDate.getValue() != null)
        	end = ReadEndDate.getValue();
        //読書開始日が読書終了日より大きい場合、登録不可
        if (start != null && end != null) {
        	if (start.compareTo(end) > 0) {
        		MsgOutput.setText("読書開始日は読書終了日以前に設定してください。");
        		return;
        	}
        }

        DatabaseFbooks db = new DatabaseFbooks();
        String[] Search = new String[5];
        //登録済みタイトルのチェック
        Search[0] = TitleField.getText();
        Book[] SearchResult = db.searchBook(Search, 0);
        if (SearchResult.length > 0) {
            MsgOutput.setText("登録済みのタイトルです。");
            return;
        }

        inputText[0] = TitleField.getText();
        inputText[1] = AuthorField.getText();
        inputText[2] = CompanyField.getText();
        inputText[3] = GenreCBox.getValue().toString();
        if (start != null)
        	inputText[4] = start.toString();
        if (end != null)
        	inputText[5] = end.toString();
        inputText[6] = MemoArea.getText();

        db.addBook(inputText);
        //確定ページ
        Main.getInstance().sendAddFixController("登録されました。");
    }

    //メインページへ
	@FXML
    protected void handleButtonAction() {
        Main.getInstance().sendMainController();
    }
}