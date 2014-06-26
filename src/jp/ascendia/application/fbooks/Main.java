package jp.ascendia.application.fbooks;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
    // インスタンス
    instance = this;

    // ステージの設定
    stage = primaryStage;
    stage.setWidth(800);
    stage.setHeight(600);

    // メインページに遷移
    mainController();

    stage.show();
  }

  /**
   * @param args
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
   * @param Book[], String[]
   */
  public void searchResultController(Book[] searchResult, Book searchText, int allFlg)
      throws ClassNotFoundException {

    stage.setTitle("検索結果");

    SearchResController controller = new SearchResController(searchResult, searchText, allFlg);
    this.replaceSceneContent(controller);
  }

  /**
   * シーンの変更
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
   * @return
   */
  public static Main getInstance() {
    return instance;
  }
}
