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
public class Controller extends Application {

  /**
   * Controller class instance
   */
  private static Controller instance;

  /**
   * ステージ
   */
  private static Stage mainStage;
  private static Stage editStage;
  private static Stage fixStage;

  public void start(Stage primaryStage) throws Exception {
    /** インスタンス */
    instance = this;

    mainStage = primaryStage;
    mainStage.setWidth(800);
    mainStage.setHeight(600);

    mainController();

    mainStage.show();
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

    mainStage.setTitle("読書記録アプリ");

    Home controller = new Home("Home.fxml");
    this.replaceSceneContent(controller);
  }

  /**
   * 登録ページへ遷移
   */
  public void addController() {

    mainStage.setTitle("登録");

    Add controller = new Add("Add.fxml");
    this.replaceSceneContent(controller);
  }

  /**
   * 検索ページへ遷移
   */
  public void searchController() {

    mainStage.setTitle("検索");

    Search controller = new Search("Search.fxml");
    this.replaceSceneContent(controller);
  }

  /**
   * 検索結果ページへ遷移
   *
   * @param searchResult 検索結果
   * @param searchText 検索文字
   * @throws ClassNotFoundException 指定された名前のクラスの定義が見つからなかった場合
   */
  public void searchResultController(Book[] searchResult, Book searchText)
      throws ClassNotFoundException {

    SearchResult controller = new SearchResult(searchResult, searchText, "SearchResult.fxml");
    //モーダルウィンドウ作成
    if (editStage == null) {
      Scene scene = new Scene(controller);
      Stage stage = new Stage();
      stage.setTitle("検索結果");
      stage.setScene(scene);
      stage.setX(400);
      stage.setY(100);
      stage.setWidth(800);
      stage.setHeight(600);
      stage.initModality(Modality.WINDOW_MODAL);
      stage.initOwner(mainStage);
      stage.show();
      editStage = stage;
    } else {
      editStage.getScene().setRoot(controller);
    }
  }

  /**
   * 編集ウィンドウ表示
   *
   * @param id 編集対象の書籍ID
   * @param searchText 検索文字
   * @throws ClassNotFoundException 指定された名前のクラスの定義が見つからなかった場合
   */
  public void editController(String id, Book searchText)
      throws ClassNotFoundException {

    Edit controller = new Edit(id, searchText, "Edit.fxml");
    editStage.setTitle("編集");
    editStage.getScene().setRoot(controller);
  }

  /**
   * 登録完了ウィンドウ表示
   *
   * @throws ClassNotFoundException 指定された名前のクラスの定義が見つからなかった場合
   */
  public void addFixController()
      throws ClassNotFoundException {

    AddFix controller = new AddFix("Fix.fxml");
    Scene scene = new Scene(controller);

    //モーダルウィンドウ作成
    Stage stage = new Stage();
    stage.setTitle("登録完了");
    stage.setScene(scene);
    stage.setWidth(400);
    stage.setHeight(200);
    stage.initModality(Modality.WINDOW_MODAL);
    stage.initOwner(mainStage);
    stage.show();
    fixStage = stage;
  }

  /**
   * 編集完了ウィンドウ表示
   *
   * @param searchText 検索文字
   * @throws ClassNotFoundException 指定された名前のクラスの定義が見つからなかった場合
   */
  public void editFixController(Book searchText)
      throws ClassNotFoundException {

    EditFix controller = new EditFix(searchText, "Fix.fxml");
    Scene scene = new Scene(controller);

    //モーダルウィンドウ作成
    Stage stage = new Stage();
    stage.setTitle("編集完了");
    stage.setScene(scene);
    stage.setWidth(400);
    stage.setHeight(200);
    stage.initModality(Modality.WINDOW_MODAL);
    stage.initOwner(editStage);
    stage.show();
    fixStage = stage;
  }

  /**
   * 削除完了ウィンドウ表示
   *
   * @param searchText 検索文字
   * @throws ClassNotFoundException 指定された名前のクラスの定義が見つからなかった場合
   */
  public void delFixController(Book searchText)
      throws ClassNotFoundException {

    DelFix controller = new DelFix(searchText, "Fix.fxml");
    Scene scene = new Scene(controller);

    //モーダルウィンドウ作成
    Stage stage = new Stage();
    stage.setTitle("削除完了");
    stage.setScene(scene);
    stage.setWidth(400);
    stage.setHeight(200);
    stage.initModality(Modality.WINDOW_MODAL);
    stage.initOwner(editStage);
    stage.show();
    fixStage = stage;
  }

  /**
   * モーダルウィンドウを閉じる
   */
  public void closeWindow() {
    editStage.getScene().getWindow().hide();
    editStage = null;
  }

  /**
   * モーダルウィンドウを閉じる
   */
  public void closeFixWindow() {
    fixStage.getScene().getWindow().hide();
    fixStage = null;
  }

  /**
   * シーンの変更
   *
   * @param controller
   */
  private void replaceSceneContent(Parent controller) {
    Scene scene = mainStage.getScene();
    if (scene == null) {
      scene = new Scene(controller);
      mainStage.setScene(scene);
    } else {
      mainStage.getScene().setRoot(controller);
    }
  }

  /**
   * Get Instance
   *
   * @return インスタンス
   */
  public static Controller getInstance() {
    return instance;
  }
}
