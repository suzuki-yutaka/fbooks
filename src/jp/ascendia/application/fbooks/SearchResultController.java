package jp.ascendia.application.fbooks;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

public class SearchResultController extends AnchorPane implements Initializable {

  /**
   * コンストラクタ
   */
  //検索結果出力
  public SearchResultController(Book[] searchResult, Book searchText, int allFlg) throws ClassNotFoundException {

    CreateOutputElement cp = new CreateOutputElement();

    Scene scene = Main.stage.getScene();
    String style = Main.class.getResource("../fbooks/css/Main.css").toExternalForm();
    scene.getStylesheets().add(style);

    //ページリンク作成
    HBox linkBox = new HBox();
    //css用のID登録
    linkBox.setId("linkBox");
    AnchorPane.setLeftAnchor(linkBox, 0.0);
    AnchorPane.setRightAnchor(linkBox, 0.0);

    //ページ遷移用label作成
    Label home = cp.linkLabelCreate("ホーム");
    home.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        Main.getInstance().mainController();
      }
    });
    linkBox.getChildren().add(home);

    Label add = cp.linkLabelCreate("登録");
    add.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        Main.getInstance().addController();
      }
    });
    linkBox.getChildren().add(add);

    Label search = cp.linkLabelCreate("検索");
    search.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        Main.getInstance().searchController();
      }
    });
    linkBox.getChildren().add(search);

    this.getChildren().add(linkBox);

    //検索結果件数
    HBox hbox = new HBox();
    Label Num = new Label(Integer.toString(searchResult.length));
    Label NumText = new Label("件見つかりました。");
    hbox.getChildren().add(Num);
    hbox.getChildren().add(NumText);
    hbox.setLayoutX(37);
    hbox.setLayoutY(57);
    //css用のID登録
    hbox.setId("hbox");
    this.getChildren().add(hbox);

    //スクロールパネル作成
    ScrollPane sp = new ScrollPane();
    sp.setPrefSize(750, 443);
    sp.setLayoutX(17);
    sp.setLayoutY(100);

    //グリッドパネル格納用のVBox作成
    VBox vbox = new VBox();
    vbox.setPrefSize(735, 400);
    vbox.setLayoutX(37);
    vbox.setLayoutY(50);
    //css用のID登録
    vbox.setId("vbox");

    for (int i = 0; i < searchResult.length; i++) {
      //検索結果格納、グリッドパネル作成
      GridPane grid = new GridPane();
      grid.setPadding(new Insets(30, 0, 30, 100));
      grid.setVgap(5);
      grid.setHgap(5);
      grid.setMinHeight(USE_PREF_SIZE);
      grid.setMaxHeight(USE_PREF_SIZE);
      //css用のID登録
      grid.setId("grid");

      //書籍項目作成
      cp.bookHeadLabelCreate("タイトル：", grid, 0);
      cp.bookHeadLabelCreate("著者：", grid, 1);
      cp.bookHeadLabelCreate("出版社：", grid, 2);
      cp.bookHeadLabelCreate("ジャンル：", grid, 3);
      cp.bookHeadLabelCreate("読書開始日：", grid, 4);
      cp.bookHeadLabelCreate("読書終了日：", grid, 5);
      cp.bookHeadLabelCreate("メモ：", grid, 6);

      //書籍情報の取得
      cp.bookOutputLabelCreate(searchResult[i].title, grid, 0);
      cp.bookOutputLabelCreate(searchResult[i].author, grid, 1);
      cp.bookOutputLabelCreate(searchResult[i].company, grid, 2);
      cp.bookOutputLabelCreate(searchResult[i].genre, grid, 3);
      cp.bookOutputLabelCreate(searchResult[i].readStart, grid, 4);
      cp.bookOutputLabelCreate(searchResult[i].readEnd, grid, 5);
      cp.bookOutputLabelCreate(searchResult[i].memo, grid, 6);

      //編集ボタン作成
      cp.editBottonCreate(grid, searchResult, searchText, i);

      //削除ボタン作成
      cp.deleteBottonCreate(grid, searchResult, searchText, i, allFlg);

      //区切りライン作成
      Line line = new Line();
      line.setEndX(735);
      //css用のID登録
      line.setId("line");

      vbox.getChildren().add(grid);
      vbox.getChildren().add(line);
    }
    sp.setContent(vbox);
    this.getChildren().add(sp);
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {
  }
}