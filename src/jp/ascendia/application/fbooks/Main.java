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
     * メインページへ遷移
     */
    public void sendMainController() {

        stage.setTitle("メインページ");

        MainController controller = new MainController();
        this.replaceSceneContent(controller);
    }

    /**
     * 登録ページへ遷移
     */
    public void sendAddPageController(String[] text) {

        stage.setTitle("書籍登録");

        AddPageController controller = new AddPageController(text);
        this.replaceSceneContent(controller);
    }

    /**
     * 検索ページへ遷移
     */
    public void sendSearPageController() {

        stage.setTitle("書籍検索");

        SearPageController controller = new SearPageController();
        this.replaceSceneContent(controller);
    }

    /**
     * 検索結果ページへ遷移
     * @param String[]
     */
    public void sendSearResController(Book[] bookArray, String[] SearchText) throws ClassNotFoundException {

        stage.setTitle("検索結果");

        Scene scene = stage.getScene();
        String style = Main.class.getResource("../css/Main.css").toExternalForm();
        scene.getStylesheets().add(style);

        SearResController controller = new SearResController(bookArray, SearchText);
        this.replaceSceneContent(controller);
    }

    /**
     * 編集ページへ遷移
     */
    public void sendEditPageController(Book[] labelText, int num, String[] text) {

        stage.setTitle("書籍編集");

        EditPageController controller = new EditPageController(labelText, num, text);
        this.replaceSceneContent(controller);
    }

    /**
     * 登録完了ページへ遷移
     */
    public void sendAddFixController(String FixText) {

        stage.setTitle("登録完了");

        AddFixController controller = new AddFixController(FixText);
        this.replaceSceneContent(controller);
    }

    /**
     * 編集完了ページへ遷移
     */
    public void sendEditFixController(String FixText, String[] SearchText) {

        stage.setTitle("編集完了");

        EditFixController controller = new EditFixController(FixText, SearchText);
        this.replaceSceneContent(controller);
    }

    /**
     * 検索結果ページへ遷移
     */
    public void sendSearchFixController(String FixText) {

        stage.setTitle("検索結果");

        SearchFixController controller = new SearchFixController(FixText);
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
