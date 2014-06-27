package jp.ascendia.application.fbooks;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class CreateOutputElement {

  public Label linkLabelCreate(String text) {

    Label linkLabel = new Label(text);
    linkLabel.setPrefWidth(70);
    linkLabel.setPrefHeight(30);
    //css用のID登録
    linkLabel.setId("linkLabel");

    return linkLabel;
  }

  public void bookHeadLabelCreate(String text, GridPane grid, int num) {

    Label headLabel = new Label(text);
    headLabel.setAlignment(Pos.TOP_RIGHT);
    GridPane.setConstraints(headLabel, 0, num);
    GridPane.setHalignment(headLabel, HPos.RIGHT);
    grid.getChildren().add(headLabel);
  }

  public void bookOutputLabelCreate(String text, GridPane grid, int num) {

    Label outputLabel = new Label(text);
    GridPane.setConstraints(outputLabel, 1, num);
    grid.getChildren().add(outputLabel);
    outputLabel.setMaxWidth(440);
    //css用のID登録
    outputLabel.setId("outputLabel");
  }

  public void editBottonCreate(GridPane grid, Book[] searchResult, Book searchText, int num) {
    Button edit = new Button("編集");
    edit.setId(Integer.toString(num));
    GridPane.setConstraints(edit, 0, 8);
    GridPane.setHalignment(edit, HPos.CENTER);
    grid.getChildren().add(edit);

    //編集処理
    edit.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        try {
          //編集ウィンドウを表示
          Main.getInstance().editController(searchResult[Integer.parseInt(edit.getId())], searchText);
        } catch (NumberFormatException | ClassNotFoundException e) {
          // TODO 自動生成された catch ブロック
          e.printStackTrace();
        }
      }
    });
  }

  public void deleteBottonCreate(GridPane grid, Book[] searchResult, Book searchText, int num, int allFlg) {
    Button delete = new Button("削除");
    delete.setId(Integer.toString(num));
    GridPane.setConstraints(delete, 1, 8);
    GridPane.setHalignment(delete, HPos.LEFT);
    grid.getChildren().add(delete);

    //削除処理
    delete.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        //データベースのデータ削除
        DatabaseFbooks db = new DatabaseFbooks();
        db.deleteBook(searchResult[Integer.parseInt(delete.getId())]);

        //削除完了ウィンドウ表示
        try {
          Main.getInstance().fixController("削除されました。", searchText);
        } catch (ClassNotFoundException e) {
          // TODO 自動生成された catch ブロック
          e.printStackTrace();
        }
      }
    });
  }
}