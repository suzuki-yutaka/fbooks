package jp.ascendia.application.fbooks;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

/**
 * メイン画面を出力するクラス
 * @version 1.0
 * @author Yutaka Suzuki
 */
public class MainController extends AnchorPane {
  /**
   * コンストラクタ
   */
  public MainController() {
    loadFXML();
  }

  /**
   * FXMLのロード
   */
  private void loadFXML() {

    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainPage.fxml"));
    fxmlLoader.setRoot(this);

    // 自分自身をコントロールとして設定
    fxmlLoader.setController(this);

    try {
      fxmlLoader.load();
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
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