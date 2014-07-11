package jp.ascendia.application.fbooks;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * メイン画面を出力するクラス
 * @version 1.0
 * @author Yutaka Suzuki
 */
public class Home extends FxmlLoad {
  /** 登録用 */
  @FXML
  private TextField addTitleField;
  @FXML
  private TextField addAuthorField;
  @FXML
  private TextField addCompanyField;
  @FXML
  private ComboBox<String> addGenreCBox;
  @FXML
  private DatePicker addReadStartDate;
  @FXML
  private DatePicker addReadEndDate;
  @FXML
  private TextArea addMemoArea;

  /** 検査用 */
  @FXML
  private TextField searchTitleField;
  @FXML
  private TextField searchAuthorField;
  @FXML
  private ComboBox<String> searchGenreCBox;
  @FXML
  private DatePicker searchReadStartDate;
  @FXML
  private DatePicker searchReadEndDate;

  /** メッセージ出力用 */
  @FXML
  private Label addMsgOutput;
  @FXML
  private Label searchMsgOutput;

  /**
   * コンストラクタ
   * @param fxml fxmlファイル名
   */
  public Home(String fxml) {
    super(fxml);
  }

  /**
   * ボタンクリックアクション
   * 登録処理
   */
  @FXML
  protected void handleButtonActionAdd() {

    //入力値の取得
    Book input = new Book(addTitleField.getText(), addAuthorField.getText(), addCompanyField.getText(),
        addGenreCBox.getValue(), addReadStartDate.getValue(), addReadEndDate.getValue(), addMemoArea.getText());

    //入力値チェック
    String result = new ValueCheck().inputValueCheck(input);
    if (result != null) {
      addMsgOutput.setText(result);
      return;
    }

    //データベース検索
    DatabaseFbooks db = new DatabaseFbooks();
    Book[] searchResult = db.searchBook(new Book("", input.getTitle(), "", "", "", "", "", ""));

    //登録済みタイトルのチェック
    if (searchResult != null && searchResult.length > 0) {
      addMsgOutput.setText("登録済みのタイトルです。");
      return;
    }

    //データベース登録
    db.addBook(input);

    //登録完了ウィンドウ表示
    try {
      Controller.getInstance().addFixController();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * ボタンクリックアクション
   * 検索処理
   */
  @FXML
  protected void handleButtonActionSearchRes() {

    //入力値の取得
    Book searchText = new Book(searchTitleField.getText(), searchAuthorField.getText(), "",
        searchGenreCBox.getValue(), searchReadStartDate.getValue(), searchReadEndDate.getValue(), "");

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
      searchMsgOutput.setText("見つかりませんでした。");
    }
  }

  /**
   * ボタンクリックアクション
   * 全件検索処理
   */
  @FXML
  protected void handleButtonActionSearchAll() {

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
      searchMsgOutput.setText("見つかりませんでした。");
    }
  }
}