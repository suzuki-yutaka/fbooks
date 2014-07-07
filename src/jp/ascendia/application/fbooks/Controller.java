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
  private static Stage stage;
  private static Stage fixStage;

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

    stage.setTitle("ホーム");

    Home controller = new Home("Home.fxml");
    this.replaceSceneContent(controller);
  }

  /**
   * 登録ページへ遷移
   */
  public void addController() {

    stage.setTitle("書籍登録");

    Add controller = new Add("Add.fxml");
    this.replaceSceneContent(controller);
  }

  /**
   * 検索ページへ遷移
   */
  public void searchController() {

    stage.setTitle("書籍検索");

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

    stage.setTitle("検索結果");

    SearchResult controller = new SearchResult(searchResult, searchText, "SearchResult.fxml");
    this.replaceSceneContent(controller);
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
    Scene editScene = new Scene(controller);

    //モーダルウィンドウ作成
    Stage editStage = new Stage();
    editStage.setTitle("書籍編集");
    editStage.setScene(editScene);
    editStage.setX(400);
    editStage.setY(100);
    editStage.setWidth(800);
    editStage.setHeight(600);
    editStage.initModality(Modality.WINDOW_MODAL);
    editStage.initOwner(stage);
    editStage.show();
    fixStage = editStage;
  }

  /**
   * 登録完了ウィンドウ表示
   *
   * @throws ClassNotFoundException 指定された名前のクラスの定義が見つからなかった場合
   */
  public void addFixController()
      throws ClassNotFoundException {

    AddFix controller = new AddFix("Fix.fxml");
    Scene fScene = new Scene(controller);

    //モーダルウィンドウ作成
    Stage fStage = new Stage();
    fStage.setTitle("登録完了");
    fStage.setScene(fScene);
    fStage.setWidth(400);
    fStage.setHeight(200);
    fStage.initModality(Modality.WINDOW_MODAL);
    fStage.initOwner(stage);
    fStage.show();
    fixStage = fStage;
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

    //モーダルウィンドウ作成
    fixStage.getScene().setRoot(controller);
    fixStage.setWidth(400);
    fixStage.setHeight(200);
    fixStage.setX(485);
    fixStage.setY(165);
    fixStage.setTitle("編集完了");
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
    Scene fScene = new Scene(controller);

    //モーダルウィンドウ作成
    Stage fStage = new Stage();
    fStage.setTitle("削除完了");
    fStage.setScene(fScene);
    fStage.setWidth(400);
    fStage.setHeight(200);
    fStage.initModality(Modality.WINDOW_MODAL);
    fStage.initOwner(stage);
    fStage.show();
    fixStage = fStage;
  }

  /**
   * モーダルウィンドウを閉じる
   */
  public void closeWindow() {
    fixStage.getScene().getWindow().hide();
    fixStage = null;
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
  public static Controller getInstance() {
    return instance;
  }
}
