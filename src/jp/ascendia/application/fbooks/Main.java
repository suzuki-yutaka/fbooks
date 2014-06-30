package jp.ascendia.application.fbooks;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * 画面遷移を制御するクラス
 * @version 1.0
 * @author Yutaka Suzuki
 */
public class Main extends Application {

  /**
   * Main class instance
   */
  private static Main instance;

  /**
   * ステージ
   */
  protected static Stage stage;
  protected static Stage fixStage;

  public void start(Stage primaryStage) throws Exception {
    /** インスタンス */
    instance = this;

    stage = primaryStage;
    stage.setWidth(800);
    stage.setHeight(600);

    mainController();

    stage.show();
  }

  /**
   * メイン
   * @param args 未使用
   */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * メインページへ遷移
   */
  public void mainController() {

    stage.setTitle("メインページ");

    MainController controller = new MainController();
    this.replaceSceneContent(controller);
  }

  /**
   * 登録ページへ遷移
   */
  public void addController() {

    stage.setTitle("書籍登録");

    AddController controller = new AddController();
    this.replaceSceneContent(controller);
  }

  /**
   * 検索ページへ遷移
   */
  public void searchController() {

    stage.setTitle("書籍検索");

    SearchController controller = new SearchController();
    this.replaceSceneContent(controller);
  }

  /**
   * 検索結果ページへ遷移
   *
   * @param searchResult 検索結果
   * @param searchText 検索文字
   * @param allFlg 全件検索フラグ
   * @throws ClassNotFoundException 指定された名前のクラスの定義が見つからなかった場合
   */
  public void searchResultController(Book[] searchResult, Book searchText, int allFlg)
      throws ClassNotFoundException {

    stage.setTitle("検索結果");

    SearchResultController controller = new SearchResultController(searchResult, searchText, allFlg);
    this.replaceSceneContent(controller);
  }

  /**
   * 編集ウィンドウ表示
   *
   * @param searchResult 検索結果
   * @param searchText 検索文字
   * @throws ClassNotFoundException 指定された名前のクラスの定義が見つからなかった場合
   */
  public void editController(Book searchResult, Book searchText)
      throws ClassNotFoundException {

    EditController controller = new EditController(searchResult, searchText);
    Scene editScene = new Scene(controller);
    Stage editStage = new Stage();
    fixStage = editStage;
    editStage.setTitle("書籍編集");
    editStage.setScene(editScene);
    editStage.setX(400);
    editStage.setY(100);
    editStage.setWidth(800);
    editStage.setHeight(600);
    editStage.initModality(Modality.WINDOW_MODAL);
    editStage.initOwner(stage);
    editStage.show();
  }

  /**
   * 完了ウィンドウ表示
   *
   * @param text 完了メッセージ
   * @param searchText 検索文字
   * @throws ClassNotFoundException 指定された名前のクラスの定義が見つからなかった場合
   */
  public void fixController(String text, Book searchText)
      throws ClassNotFoundException {

    FixController controller = new FixController(text, searchText);
    if (searchText == null || fixStage == null) {
      Scene fScene = new Scene(controller);
      Stage fStage = new Stage();
      fixStage = fStage;
      fStage.setTitle("完了");
      fStage.setScene(fScene);
      fStage.setWidth(400);
      fStage.setHeight(200);
      fStage.initModality(Modality.WINDOW_MODAL);
      fStage.initOwner(stage);
      fStage.show();
    } else {
      fixStage.setWidth(400);
      fixStage.setHeight(200);
      fixStage.setX(485);
      fixStage.setY(165);
      fixStage.setTitle("完了");
      fixStage.getScene().setRoot(controller);
    }
  }

  /**
   * シーンの変更
   *
   * @param controller
   */
  private void replaceSceneContent(Parent controller) {
    Scene scene = stage.getScene();
    if (scene == null) {
      scene = new Scene(controller);
      stage.setScene(scene);
    } else {
      stage.getScene().setRoot(controller);
    }
  }

  /**
   * Get Instance
   *
   * @return インスタンス
   */
  public static Main getInstance() {
    return instance;
  }
}
