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

  protected static String tmp;

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
   * @param st 検索文字
   * @param fxml 読み込み対象fxmlファイル名
   */
  public FxmlLoad(Book st, String fxml) {
    searchText = st;

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
   * @param str メッセージ
   * @param st 検索文字
   * @param fxml 読み込み対象fxmlファイル名
   */
  public FxmlLoad(String str, Book st, String fxml) {
    tmp = str;
    searchText = st;

    loadFXML(fxml);
  }

  /**
   * FXMLのロード
   */
  private void loadFXML(String fxml) {

    FXMLLoader fxmlLoader = new FXMLLoader(Controller.class.getResource(fxml));
    fxmlLoader.setRoot(this);

    fxmlLoader.setController(this);

    try {
      fxmlLoader.load();
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }
}