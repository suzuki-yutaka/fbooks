package jp.ascendia.application.fbooks;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * 登録完了ウィンドウ表示クラス
 * @version 1.0
 * @author Yutaka Suzuki
 */
public class AddFixController extends FxmlLoad implements Initializable {

  /** メッセージ出力用 */
  @FXML
  protected Label fixField;

  /**
   * コンストラクタ
   *
   * @param msg 出力メッセージ
   * @param st 検索文字
   * @param fxml 読み込み対象fxmlファイル名
   */
  public AddFixController(String msg, Book st, String fxml) {
    super(msg, st, fxml);
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    fixField.setText(text);
  }

  /**
   * ボタンクリックアクション
   * ウィンドウを閉じる
   */
  @FXML
  protected void handleButtonActionClose() {
    //登録ページへ戻る
    Main.getInstance().addController();

    //ウィンドウを閉じる
    Main.fixStage.getScene().getWindow().hide();
    Main.fixStage = null;
  }

}