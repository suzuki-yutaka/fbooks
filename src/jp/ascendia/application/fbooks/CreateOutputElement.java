package jp.ascendia.application.fbooks;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class CreateOutputElement {

  public void linkLabelCreate(String text, HBox hbox) {

    Label label = new Label(text);
    label.setPrefWidth(70);
    label.setPrefHeight(30);
    label.setAlignment(Pos.CENTER);
    label.setStyle("-fx-font-size: 14px; -fx-underline: true; -fx-text-fill: #1e90ff;");

    label.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        Main.getInstance().mainController();
      }
    });

    hbox.getChildren().add(label);
  }

  public void bookHeadLabelCreate(String text, GridPane grid, int num) {

    Label label = new Label(text);
    label.setAlignment(Pos.TOP_RIGHT);
    GridPane.setConstraints(label, 0, num);
    GridPane.setHalignment(label, HPos.RIGHT);
    grid.getChildren().add(label);
  }

  public void bookOutputLabelCreate(String text, GridPane grid, int num) {

    Label label = new Label(text);
    GridPane.setConstraints(label, 1, num);
    grid.getChildren().add(label);
    label.setStyle("-fx-wrap-text: true;");
    label.setMaxWidth(440);
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

        //検索結果一覧へ
        Book[] result = db.searchBook(searchText, allFlg);
        try {
          Main.getInstance().searchResultController(result, searchText, allFlg);
        } catch (ClassNotFoundException e) {
          // TODO 自動生成された catch ブロック
          e.printStackTrace();
        }
      }
    });
  }
}