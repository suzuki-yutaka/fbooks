package jp.ascendia.application.fbooks;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class SearchResController extends AnchorPane implements Initializable {

    /**
     * コンストラクタ
     */
    public SearchResController(Book[] SearchResult, String[] SearchText) throws ClassNotFoundException {
        //検索結果件数
        HBox hbox = new HBox();
        Label Num  = new Label(Integer.toString(SearchResult.length));
        Label NumText  = new Label("件見つかりました。");
        hbox.getChildren().add(Num);
        hbox.getChildren().add(NumText);
        hbox.setLayoutX(37);
        hbox.setLayoutY(37);
        hbox.setStyle("-fx-font-size: 24px;");
        this.getChildren().add(hbox);

        //スクロールパネル作成
        ScrollPane sp = new ScrollPane();
        sp.setPrefSize(750, 400);
        sp.setLayoutX(17);
        sp.setLayoutY(80);

        //グリッドパネル格納用のVBox作成
        VBox vbox = new VBox();
        vbox.setPrefSize(735, 400);
        vbox.setLayoutX(37);
        vbox.setLayoutY(50);
        vbox.setStyle("-fx-background-color: white;");
        vbox.setStyle("-fx-font-size: 14px;");

        for (int i = 0; i < SearchResult.length; i++) {
            //グリッドパネル作成
            GridPane grid = new GridPane();
            grid.setPadding(new Insets(20, 0, 30, 100));
            grid.setVgap(5);
            grid.setHgap(5);
            grid.setMinHeight(USE_PREF_SIZE);
            grid.setMaxHeight(USE_PREF_SIZE);

            Label Titlelabel  = new Label("タイトル：");
            Titlelabel.setAlignment(Pos.TOP_RIGHT);
            GridPane.setConstraints(Titlelabel, 0, 0);
            GridPane.setHalignment(Titlelabel, HPos.RIGHT);
            grid.getChildren().add(Titlelabel);
            Label Authorlabel  = new Label("著者：");
            GridPane.setConstraints(Authorlabel, 0, 1);
            GridPane.setHalignment(Authorlabel, HPos.RIGHT);
            grid.getChildren().add(Authorlabel);
            Label Companylabel  = new Label("出版社：");
            GridPane.setConstraints(Companylabel, 0, 2);
            GridPane.setHalignment(Companylabel, HPos.RIGHT);
            grid.getChildren().add(Companylabel);
            Label PubDaylabel  = new Label("出版日：");
            GridPane.setConstraints(PubDaylabel, 0, 3);
            GridPane.setHalignment(PubDaylabel, HPos.RIGHT);
            grid.getChildren().add(PubDaylabel);
            Label ReadStartlabel  = new Label("読書開始日：");
            GridPane.setConstraints(ReadStartlabel, 0, 4);
            GridPane.setHalignment(ReadStartlabel, HPos.RIGHT);
            grid.getChildren().add(ReadStartlabel);
            Label ReadEndlabel  = new Label("読書終了日：");
            GridPane.setConstraints(ReadEndlabel, 0, 5);
            GridPane.setHalignment(ReadEndlabel, HPos.RIGHT);
            grid.getChildren().add(ReadEndlabel);
            Label Memolabel  = new Label("メモ：");
            GridPane.setConstraints(Memolabel, 0, 6);
            GridPane.setHalignment(Memolabel, HPos.RIGHT);
            grid.getChildren().add(Memolabel);

            Label ResTitlelabel  = new Label(SearchResult[i].title);
            GridPane.setConstraints(ResTitlelabel, 1, 0);
            grid.getChildren().add(ResTitlelabel);
            ResTitlelabel.setStyle("-fx-wrap-text: true;");
            ResTitlelabel.setMaxWidth(440);
            Label ResAuthorlabel  = new Label(SearchResult[i].author);
            GridPane.setConstraints(ResAuthorlabel, 1, 1);
            grid.getChildren().add(ResAuthorlabel);
            ResAuthorlabel.setStyle("-fx-wrap-text: true;");
            ResAuthorlabel.setMaxWidth(440);
            Label ResCompanylabel  = new Label(SearchResult[i].company);
            GridPane.setConstraints(ResCompanylabel, 1, 2);
            grid.getChildren().add(ResCompanylabel);
            ResCompanylabel.setStyle("-fx-wrap-text: true;");
            ResCompanylabel.setMaxWidth(440);
            Label ResPubDaylabel  = new Label(SearchResult[i].publishday);
            GridPane.setConstraints(ResPubDaylabel, 1, 3);
            grid.getChildren().add(ResPubDaylabel);
            Label ResReadStartlabel  = new Label(SearchResult[i].readstart);
            GridPane.setConstraints(ResReadStartlabel, 1, 4);
            grid.getChildren().add(ResReadStartlabel);
            Label ResReadEndlabel  = new Label(SearchResult[i].readend);
            GridPane.setConstraints(ResReadEndlabel, 1, 5);
            grid.getChildren().add(ResReadEndlabel);
            Label ResMemolabel  = new Label(SearchResult[i].memo);
            ResMemolabel.setStyle("-fx-wrap-text: true;");
            ResMemolabel.setMaxWidth(440);
	        GridPane.setConstraints(ResMemolabel, 1, 6);
	        grid.getChildren().add(ResMemolabel);

	        //編集ボタン
	        Button Edit = new Button("編集");
	        Edit.setId(Integer.toString(i));
	        GridPane.setConstraints(Edit, 0, 8);
	        GridPane.setHalignment(Edit, HPos.CENTER);
	        grid.getChildren().add(Edit);

	        //削除ボタン
	        Button clear = new Button("削除");
	        GridPane.setConstraints(clear, 1, 8);
	        GridPane.setHalignment(clear, HPos.LEFT);
	        grid.getChildren().add(clear);

	        //編集処理
	        Edit.setOnAction(new EventHandler<ActionEvent>() {
	        	@Override
	        	public void handle(ActionEvent actionEvent) {
	        		Main.getInstance().sendEditPageController(SearchResult, Integer.parseInt(Edit.getId()), SearchText);
	        	}
	        });

	        //削除処理
	        clear.setOnAction(new EventHandler<ActionEvent>() {
	        	@Override
	        	public void handle(ActionEvent actionEvent) {
	                DatabaseFbooks db = new DatabaseFbooks();
	                db.deleteBook(SearchResult, Integer.parseInt(Edit.getId()));

	                //確定ページへ
	                Main.getInstance().sendEditFixController("削除されました。", SearchText);
	        	}
	        });

	        vbox.getChildren().add(grid);
        }
        sp.setContent(vbox);
        this.getChildren().add(sp);

        //戻るボタン
        HBox hbox2 = new HBox();
        Button back = new Button("戻る");
        hbox2.setLayoutX(360);
        hbox2.setLayoutY(500);
        hbox2.setAlignment(Pos.BOTTOM_CENTER);
        hbox2.getChildren().add(back);
        this.getChildren().add(hbox2);

        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            	Main.getInstance().sendSearchPageController();
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}