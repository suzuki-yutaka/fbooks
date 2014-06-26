package jp.ascendia.application.fbooks;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class FixController extends AnchorPane implements Initializable {

  private static String msgText;

  private static Book searchText;

  /** ラベル */
  @FXML
  private Label FixField;

  /**
   * コンストラクタ
   */
  public FixController(String mt, Book st) {
    msgText = mt;
    searchText = st;
    loadFXML();
  }

  /**
   * FXMLのロード
   */
  private void loadFXML() {

    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("FixPage.fxml"));
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
    FixField.setText(msgText);
    FixField.setStyle("-fx-font-size: 24px");
  }

  /**
   * ボタンクリックアクション
   */
  @FXML
  protected void handleButtonActionClose() throws ClassNotFoundException {
    if (searchText == null) {

      //登録ページへ戻る
      Main.getInstance().addController();
    } else {
      int allflg = 1;

      DatabaseFbooks db = new DatabaseFbooks();
      if (searchText.getTitle() != null || searchText.getAuthor() != null || searchText.getGenre()!= null ||
          searchText.getReadStart() != null || searchText.getReadEnd() != null)
        allflg = 0;
      Book[] searchResult = db.searchBook(searchText, allflg);

      //検索結果一覧ページへ戻る
      Main.getInstance().searchResultController(searchResult, searchText, allflg);
    }

    //編集ウィンドウを閉じる
    Main.fixStage.getScene().getWindow().hide();
  }
}