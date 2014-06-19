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

public class AddPageController extends AnchorPane implements Initializable {

	private static String[] initText;

    /**
     * コンストラクタ
     */
    public AddPageController(String[] text) {
        if (text != null)
        	initText = text;
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
    	if (initText != null) {
    		TitleField.setText(initText[0]);
    		AuthorField.setText(initText[1]);
    		CompanyField.setText(initText[2]);
    		if (initText[3] != null)
    			PubDayField.setValue(LocalDate.parse(initText[3]));
    		if (initText[4] != null)
    			ReadStartField.setValue(LocalDate.parse(initText[4]));
    		if (initText[5] != null)
    			ReadEndField.setValue(LocalDate.parse(initText[5]));
    		MemoField.setText(initText[6]);
    	}
    	initText = null;
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
    @FXML
    private TextField ResultField;

    /** 入力テキスト用 */
    private static String[] TextField = new String[7];

    //登録処理
    @FXML
    protected void handleButtonActionAdd() throws ClassNotFoundException {
        boolean result;

        if (!("".equals(TitleField.getText()) || "".equals(AuthorField.getText()) || "".equals(CompanyField.getText()) ||
        		PubDayField.getValue() == null)) {
        	setText();

            DatabaseFbooks db = new DatabaseFbooks();
            result = db.addBook(TextField);
            if (result) {
            	//確定ページ
            	Main.getInstance().sendAddFixController("登録されました。");
            }
        } else {
        	//登録失敗
        	setText();
        	Main.getInstance().sendAddPageController(TextField);
        }
    }

    protected void setText() {
        TextField[0] = TitleField.getText();
        TextField[1] = AuthorField.getText();
        TextField[2] = CompanyField.getText();
        if (PubDayField.getValue() != null)
        	TextField[3] = PubDayField.getValue().toString();
        if (ReadStartField.getValue() != null)
        	TextField[4] = ReadStartField.getValue().toString();
        if (ReadEndField.getValue() != null)
        	TextField[5] = ReadEndField.getValue().toString();
        TextField[6] = MemoField.getText();
    }

    //メインページへ
	@FXML
    protected void handleButtonAction() {
        Main.getInstance().sendMainController();
    }
}