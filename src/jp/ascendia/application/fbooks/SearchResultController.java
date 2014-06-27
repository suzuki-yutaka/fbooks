package jp.ascendia.application.fbooks;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
    HBox menu = new HBox();
    //menu.setStyle("-fx-background-color: #eeeeee;");
    AnchorPane.setLeftAnchor(menu, 0.0);
    AnchorPane.setRightAnchor(menu, 0.0);
    //ページ遷移用label作成
    cp.linkLabelCreate("ホーム", menu);
    cp.linkLabelCreate("登録", menu);
    cp.linkLabelCreate("検索", menu);
    this.getChildren().add(menu);

    //検索結果件数
    HBox hbox = new HBox();
    Label Num = new Label(Integer.toString(searchResult.length));
    Label NumText = new Label("件見つかりました。");
    hbox.getChildren().add(Num);
    hbox.getChildren().add(NumText);
    hbox.setLayoutX(37);
    hbox.setLayoutY(57);
    hbox.setStyle("-fx-font-size: 24px;");
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
    vbox.setStyle("-fx-background-color: white;");

    for (int i = 0; i < searchResult.length; i++) {
      //検索結果格納、グリッドパネル作成
      GridPane grid = new GridPane();
      grid.setPadding(new Insets(30, 0, 30, 100));
      grid.setVgap(5);
      grid.setHgap(5);
      grid.setMinHeight(USE_PREF_SIZE);
      grid.setMaxHeight(USE_PREF_SIZE);
      grid.setStyle("-fx-font-size: 14px;");

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
      line.setStyle("-fx-stroke:  #1e90ff;");

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