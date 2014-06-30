package jp.ascendia.application.fbooks;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * 書籍情報の登録、編集、削除完了時に使用するクラス
 * @version 1.0
 * @author Yutaka Suzuki
 */
public class FixController extends AnchorPane implements Initializable {

  private static String msgText;

  private static Book searchText;

  @FXML
  private Label FixField;

  /**
   * コンストラクタ
   *
   * @param mt 完了メッセージ
   * @param st 検索文字
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
   * 完了ウィンドウ終了
   */
  @FXML
  protected void handleButtonActionClose() {
    if (searchText == null) {
      //登録ページへ戻る
      Main.getInstance().addController();
    } else {
      //全件検索チェック
      ValueCheck vc = new ValueCheck();
      int allFlg = vc.searchAllCheck(searchText);
      //データベース検索
      DatabaseFbooks db = new DatabaseFbooks();
      Book[] searchResult = db.searchBook(searchText, allFlg);
      //検索結果一覧ページへ戻る
      try {
        Main.getInstance().searchResultController(searchResult, searchText, allFlg);
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    }

    //編集ウィンドウを閉じる
    Main.fixStage.getScene().getWindow().hide();
    Main.fixStage = null;
  }
}