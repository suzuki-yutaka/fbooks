package jp.ascendia.application.fbooks;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * 編集完了ウィンドウ表示クラス
 * @version 1.0
 * @author Yutaka Suzuki
 */
public class EditFix extends FxmlLoad implements Initializable {

  /** メッセージ出力用 */
  @FXML
  protected Label fixField;

  /**
   * コンストラクタ
   *
   * @param st 検索文字
   * @param fxml 読み込み対象fxmlファイル名
   */
  public EditFix(Book st, String fxml) {
    super(st, fxml);
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {
      fixField.setText("編集内容が反映されました。");
  }

  /**
   * ボタンクリックアクション
   * ウィンドウを閉じる
   */
  @FXML
  protected void handleButtonActionClose() {

    //データベース検索
    Book[] searchResult = new DatabaseFbooks().searchBook(searchText);

    if (searchResult != null && searchResult.length > 0) {
      try {
        //検索結果一覧ページへ戻る
        Controller.getInstance().searchResultController(searchResult, searchText);
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    }

    //ウィンドウを閉じる
    Controller.getInstance().closeFixWindow();
  }
}