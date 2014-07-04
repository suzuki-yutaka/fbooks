package jp.ascendia.application.fbooks;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * 書籍情報の検索時に使用するクラス
 * @version 1.0
 * @author Yutaka Suzuki
 */
public class Search extends FxmlLoad {

  /** 文字入力用 */
  @FXML
  private TextField titleField;
  @FXML
  private TextField authorField;
  @FXML
  private ComboBox<String> genreCBox;
  @FXML
  private DatePicker readStartDate;
  @FXML
  private DatePicker readEndDate;

  /** メッセージ出力用 */
  @FXML
  private Label msgOutput;

  /**
   * コンストラクタ
   * @param fxml fxmlファイル名
   */
  public Search(String fxml) {
    super(fxml);
  }

  /**
   * ボタンクリックアクション
   * 検索処理
   */
  @FXML
  protected void handleButtonActionSearRes() {

    //入力値の取得
    Book searchText = new Book(titleField.getText(), authorField.getText(), "",
        genreCBox.getValue(), readStartDate.getValue(), readEndDate.getValue(), "");

    //データベース検索
    Book[] searchResult = new DatabaseFbooks().searchBook(searchText);

    if (searchResult != null && searchResult.length > 0) {
      //検索結果表示ページへ
      try {
        Controller.getInstance().searchResultController(searchResult, searchText);
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    } else {
      //検索失敗
      msgOutput.setText("見つかりませんでした。");
    }
  }

  /**
   * ボタンクリックアクション
   * 全件検索処理
   */
  @FXML
  protected void handleButtonActionSearAll() {

    //データベース検索
    Book[] searchResult = new DatabaseFbooks().searchBook(null);

    if (searchResult != null && searchResult.length > 0) {
      //検索結果表示ページへ
      try {
        Controller.getInstance().searchResultController(searchResult, null);
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    } else {
      //検索失敗
      msgOutput.setText("見つかりませんでした。");
    }
  }

  /**
   * ボタンクリックアクション
   * メインページへ遷移
   */
  @FXML
  protected void handleButtonActionHomePage() {
    Controller.getInstance().mainController();
  }

  /**
   * ボタンクリックアクション
   * 登録ページへ遷移
   */
  @FXML
  protected void handleButtonActionAddPage() {
    Controller.getInstance().addController();
  }
}