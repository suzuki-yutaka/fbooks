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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * 書籍情報の検索時に使用するクラス
 * @version 1.0
 * @author Yutaka Suzuki
 */
public class SearchController extends AnchorPane implements Initializable {
  /**
   * コンストラクタ
   */
  public SearchController() {
    loadFXML();
  }

  /**
   * FXMLのロード
   */
  private void loadFXML() {
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("SearchPage.fxml"));
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
  private ComboBox<String> GenreCBox;
  @FXML
  private DatePicker ReadStartDate;
  @FXML
  private DatePicker ReadEndDate;
  @FXML
  private Label MsgOutput;

  /**
   * ボタンクリックアクション
   * 検索処理
   */
  @FXML
  protected void handleButtonActionSearRes() {
    Book searchText = new Book();

    //入力値の取得
    searchText.setAll(TitleField.getText(), AuthorField.getText(), "",
        GenreCBox.getValue(), ReadStartDate.getValue(), ReadEndDate.getValue(), "");

    //全件検索チェック
    ValueCheck vc = new ValueCheck();
    int allFlg = vc.searchAllCheck(searchText);

    //データベース検索
    DatabaseFbooks db = new DatabaseFbooks();
    Book[] searchResult = db.searchBook(searchText, allFlg);

    if (searchResult != null && searchResult.length > 0) {
      //検索結果表示ページへ
      try {
        Main.getInstance().searchResultController(searchResult, searchText, allFlg);
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    } else {
      //検索失敗
      MsgOutput.setText("見つかりませんでした。");
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
   * 登録ページへ遷移
   */
  @FXML
  protected void handleButtonActionAddPage() {
    Main.getInstance().addController();
  }
}