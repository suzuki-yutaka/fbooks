package jp.ascendia.application.fbooks;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

/**
 * fxmlを読み込むクラス
 * @version 1.0
 * @author Yutaka Suzuki
 */
public class FxmlLoad extends AnchorPane {

  /** 書籍タイトル、メッセージ */
  protected static String text;

  /** 検索結果 */
  protected static Book searchResult[];

  /** 検索文字 */
  protected static Book searchText;

  /**
   * コンストラクタ
   *
   * @param fxml 読み込み対象fxmlファイル名
   */
  public FxmlLoad(String fxml) {
    loadFXML(fxml);
  }

  /**
   * コンストラクタ
   *
   * @param sr 検索結果
   * @param st 検索文字
   * @param fxml 読み込み対象fxmlファイル名
   */
  public FxmlLoad(Book[] sr, Book st, String fxml) {
    searchResult = sr;
    searchText = st;

    loadFXML(fxml);
  }

  /**
   * コンストラクタ
   *
   * @param title 書籍タイトル
   * @param st 検索文字
   * @param fxml 読み込み対象fxmlファイル名
   */
  public FxmlLoad(String title, Book st, String fxml) {
    text = title;
    searchText = st;

    loadFXML(fxml);
  }

  /**
   * FXMLのロード
   */
  private void loadFXML(String fxml) {

    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml));
    fxmlLoader.setRoot(this);

    fxmlLoader.setController(this);

    try {
      fxmlLoader.load();
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }
}