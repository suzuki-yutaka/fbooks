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
    private Stage stage;

    public void start(Stage primaryStage) throws Exception {
        // インスタンス
        instance = this;

        // ステージの設定
        stage = primaryStage;
        stage.setWidth(800);
        stage.setHeight(600);

        // メインページに遷移
        sendMainController();

        stage.show();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * メインページへ遷移する
     */
    public void sendMainController() {

        stage.setTitle("メインページ");

        MainController controller = new MainController();
        this.replaceSceneContent(controller);
    }

    /**
     * 登録ページへ遷移する
     */
    public void sendAddPageController() {

        stage.setTitle("書籍登録");

        AddPageController controller = new AddPageController();
        this.replaceSceneContent(controller);
    }

    /**
     * 検索ページへ遷移する
     */
    public void sendSearPageController() {

        stage.setTitle("書籍検索");

        SearPageController controller = new SearPageController();
        this.replaceSceneContent(controller);
    }

    int i;

    /**
     * 検索結果ページへ遷移する
     * @param String[]
     */
    public void sendSearResController(Book[] bookArray) throws ClassNotFoundException {

        stage.setTitle("検索結果");

        Scene scene = stage.getScene();
        String style = Main.class.getResource("../css/Main.css").toExternalForm();
        scene.getStylesheets().add(style);

        SearResController controller = new SearResController(bookArray);
        this.replaceSceneContent(controller);
    }

    /**
     * 編集ページへ遷移する
     */
    public void sendEditPageController(Book[] labelText, int num) {

        stage.setTitle("書籍編集");

        EditPageController controller = new EditPageController(labelText, num);
        this.replaceSceneContent(controller);
    }

    /**
     * 登録、編集完了ページへ遷移する
     */
    public void sendFixController(String FixText) {

        stage.setTitle("完了");

        FixController controller = new FixController(FixText);
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
