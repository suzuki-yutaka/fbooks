package jp.ascendia.application.fbooks;

import javafx.application.Platform;
import javafx.fxml.FXML;

/**
 * メイン画面を出力するクラス
 * @version 1.0
 * @author Yutaka Suzuki
 */
public class MainController extends FxmlLoad {
  /**
   * コンストラクタ
   * @param fxml fxmlファイル名
   */
  public MainController(String fxml) {
    super(fxml);
  }

  /**
   * ボタンクリックアクション
   * 登録ページへ遷移
   */
  @FXML
  protected void handleButtonActionAddPage() {
    Main.getInstance().addController();
  }

  /**
   * ボタンクリックアクション
   * 検索ページへ遷移
   */
  @FXML
  protected void handleButtonActionSearchPage() {
    Main.getInstance().searchController();
  }

  /**
   * ボタンクリックアクション
   * アプリケーション終了
   */
  @FXML
  protected void handleButtonActionClose() {
    Platform.exit();
  }

}