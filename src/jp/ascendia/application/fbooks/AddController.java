package jp.ascendia.application.fbooks;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
public class AddController extends AnchorPane {

  /** 文字入力用 */
  @FXML
  private TextField titleField;
  @FXML
  private TextField authorField;
  @FXML
  private TextField companyField;
  @FXML
  private ComboBox<String> genreCBox;
  @FXML
  private DatePicker readStartDate;
  @FXML
  private DatePicker readEndDate;
  @FXML
  private TextArea memoArea;

  /** メッセージ出力用 */
  @FXML
  private Label msgOutput;

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

  /**
   * ボタンクリックアクション
   * 登録処理
   */
  @FXML
  protected void handleButtonActionAdd() {

    //入力値の取得
    Book input = new Book(titleField.getText(), authorField.getText(), companyField.getText(),
        genreCBox.getValue(), readStartDate.getValue(), readEndDate.getValue(), memoArea.getText());

    //入力値チェック
    ValueCheck vc = new ValueCheck();
    String result = vc.inputValueCheck(input);
    if (!result.equals("OK")) {
      msgOutput.setText(result);
      return;
    }

    //データベース検索
    Book searchTmp = new Book("", input.getTitle(), "", "", "", "", "", "");
    DatabaseFbooks db = new DatabaseFbooks();
    Book[] searchResult = db.searchBook(searchTmp, 0);

    //登録済みタイトルのチェック
    if (searchResult != null && searchResult.length > 0) {
      msgOutput.setText("登録済みのタイトルです。");
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