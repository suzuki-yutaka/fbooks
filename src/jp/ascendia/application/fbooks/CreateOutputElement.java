package jp.ascendia.application.fbooks;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * 書籍情報の検索結果に出力するラベル、ボタンを作成するクラス
 * @version 1.0
 * @author Yutaka Suzuki
 */
public class CreateOutputElement {

  /**
   * リンク用ラベル作成
   *
   * @param text リンク先ラベル名
   * @return linkLabel リンク先ラベル
   */
  public Label linkLabelCreate(String text) {

    Label linkLabel = new Label(text);
    linkLabel.setPrefWidth(70);
    linkLabel.setPrefHeight(30);
    /** css用のID登録 */
    linkLabel.setId("linkLabel");

    return linkLabel;
  }

  /**
   * 書籍項目ラベル作成
   *
   * @param text 書籍項目名
   * @param grid グリッドパネル
   * @param num 行番号
   */
  public void bookHeadLabelCreate(String text, GridPane grid, int num) {

    Label headLabel = new Label(text);
    headLabel.setAlignment(Pos.TOP_RIGHT);
    GridPane.setConstraints(headLabel, 0, num);
    GridPane.setHalignment(headLabel, HPos.RIGHT);
    grid.getChildren().add(headLabel);
  }

  /**
   * 書籍情報出力用ラベル作成
   *
   * @param text 書籍情報出力用
   * @param grid グリッドパネル
   * @param num 行番号
   */
  public void bookOutputLabelCreate(String text, GridPane grid, int num) {

    Label outputLabel = new Label(text);
    GridPane.setConstraints(outputLabel, 1, num);
    grid.getChildren().add(outputLabel);
    outputLabel.setMaxWidth(440);
    /** css用のID登録 */
    outputLabel.setId("outputLabel");
  }

  /**
   * 編集ボタン作成
   *
   * @param grid グリッドパネル
   * @param searchResult 検索結果
   * @param searchText 検索文字
   * @param num 編集先を特定するためのID
   */
  public void editBottonCreate(GridPane grid, Book[] searchResult, Book searchText, int num) {
    Button edit = new Button("編集");
    edit.setId(Integer.toString(num));
    GridPane.setConstraints(edit, 0, 8);
    GridPane.setHalignment(edit, HPos.CENTER);
    grid.getChildren().add(edit);

    /**
     * ボタンクリックアクション
     * 編集ウィンドウを表示
     */
    edit.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        try {
          Main.getInstance().editController(searchResult[Integer.parseInt(edit.getId())], searchText);
        } catch (ClassNotFoundException e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * 削除ボタン作成
   *
   * @param grid グリッドパネル
   * @param searchResult 検索結果
   * @param searchText 検索文字
   * @param num 削除先を特定するためのID
   * @param allFlg 全件検索フラグ
   */
  public void deleteBottonCreate(GridPane grid, Book[] searchResult, Book searchText, int num, int allFlg) {
    Button delete = new Button("削除");
    delete.setId(Integer.toString(num));
    GridPane.setConstraints(delete, 1, 8);
    GridPane.setHalignment(delete, HPos.LEFT);
    grid.getChildren().add(delete);

    /**
     * ボタンクリックアクション
     * 削除完了ウィンドウを表示
     */
    delete.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        DatabaseFbooks db = new DatabaseFbooks();
        db.deleteBook(searchResult[Integer.parseInt(delete.getId())]);

        try {
          Main.getInstance().fixController("削除されました。", searchText);
        } catch (ClassNotFoundException e) {
          e.printStackTrace();
        }
      }
    });
  }
}