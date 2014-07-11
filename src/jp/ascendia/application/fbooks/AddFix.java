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
public class AddFix extends FxmlLoad implements Initializable {

  /** メッセージ出力用 */
  @FXML
  protected Label fixField;

  /**
   * コンストラクタ
   *
   * @param fxml 読み込み対象fxmlファイル名
   */
  public AddFix(String fxml) {
    super(fxml);
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    fixField.setText("登録されました！");
  }

  /**
   * ボタンクリックアクション
   * ウィンドウを閉じる
   */
  @FXML
  protected void handleButtonActionClose() {
    //登録ページへ戻る
    Controller.getInstance().mainController();

    //ウィンドウを閉じる
    Controller.getInstance().closeFixWindow();
  }

}