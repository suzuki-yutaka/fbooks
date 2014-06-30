package jp.ascendia.application.fbooks;

import java.io.IOException;
import java.net.URL;
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

/**
 * 書籍情報の登録時に使用するクラス
 * @version 1.0
 * @author Yutaka Suzuki
 */
public class AddController extends AnchorPane implements Initializable {

  /**
   * コンストラクタ
   */
  public AddController() {
    loadFXML();
  }

  /**
   * FXMLのロード
   */
  private void loadFXML() {
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("AddPage.fxml"));
    fxmlLoader.setRoot(this);

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

  /**
   * ボタンクリックアクション
   * 登録処理
   */
  @FXML
  protected void handleButtonActionAdd() {
    Book input = new Book();

    //入力値の取得
    input.setAll(TitleField.getText(), AuthorField.getText(), CompanyField.getText(),
        GenreCBox.getValue(), ReadStartDate.getValue(), ReadEndDate.getValue(), MemoArea.getText());

    //入力値チェック
    ValueCheck vc = new ValueCheck();
    String result = vc.inputValueCheck(input);
    if (!result.equals("OK")) {
      MsgOutput.setText(result);
      return;
    }

    //データベース検索
    Book searchTmp = new Book();
    searchTmp.setTitle(TitleField.getText());
    DatabaseFbooks db = new DatabaseFbooks();
    Book[] searchResult = db.searchBook(searchTmp, 0);

    //登録済みタイトルのチェック
    if (searchResult != null && searchResult.length > 0) {
      MsgOutput.setText("登録済みのタイトルです。");
      return;
    }

    //データベース登録
    db.addBook(input);

    //登録完了ウィンドウ表示
    try {
      Main.getInstance().fixController("登録されました！", null);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * ボタンクリックアクション
   * メインページへ遷移
   */
  @FXML
  protected void handleButtonActionHomePage() {
    Main.getInstance().mainController();
  }

  /**
   * ボタンクリックアクション
   * 検索ページへ遷移
   */
  @FXML
  protected void handleButtonActionSearchPage() {
    Main.getInstance().searchController();
  }
}