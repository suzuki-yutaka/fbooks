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
  private ComboBox<String> GenreCBox;
  @FXML
  private DatePicker ReadStartDate;
  @FXML
  private DatePicker ReadEndDate;
  @FXML
  private Label MsgOutput;

  //検索処理
  @FXML
  protected void handleButtonActionSearRes() throws ClassNotFoundException {
    Book searchText = new Book();

    //入力値の取得
    searchText.setAll(TitleField.getText(), AuthorField.getText(), "",
        GenreCBox.getValue(), ReadStartDate.getValue(), ReadEndDate.getValue(), "");

    //全件検索チェック
    int allFlg = ValueCheck.searchAllCheck(searchText);

    //データベース検索
    DatabaseFbooks db = new DatabaseFbooks();
    Book[] searchResult = db.searchBook(searchText, allFlg);

    if (searchResult != null && searchResult.length > 0) {
      //検索結果表示ページへ
      Main.getInstance().searchResultController(searchResult, searchText, allFlg);
    } else {
      //検索失敗
      MsgOutput.setText("見つかりませんでした。");
    }
  }

  //メインページへ
  @FXML
  protected void handleButtonActionHomePage() {
    Main.getInstance().mainController();
  }

  //登録ページへ
  @FXML
  protected void handleButtonActionAddPage() {
    Main.getInstance().addController();
  }
}