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
import javafx.scene.shape.Line;


public class SearchResController extends AnchorPane implements Initializable {

    /**
     * コンストラクタ
     */
    //検索結果出力
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

        for (int i = 0; i < SearchResult.length; i++) {
            //グリッドパネル作成
            GridPane grid = new GridPane();
            grid.setPadding(new Insets(30, 0, 30, 100));
            grid.setVgap(5);
            grid.setHgap(5);
            grid.setMinHeight(USE_PREF_SIZE);
            grid.setMaxHeight(USE_PREF_SIZE);
            grid.setStyle("-fx-font-size: 14px;");

            Label Title  = new Label("タイトル：");
            Title.setAlignment(Pos.TOP_RIGHT);
            GridPane.setConstraints(Title, 0, 0);
            GridPane.setHalignment(Title, HPos.RIGHT);
            grid.getChildren().add(Title);
            Label Author  = new Label("著者：");
            GridPane.setConstraints(Author, 0, 1);
            GridPane.setHalignment(Author, HPos.RIGHT);
            grid.getChildren().add(Author);
            Label Company  = new Label("出版社：");
            GridPane.setConstraints(Company, 0, 2);
            GridPane.setHalignment(Company, HPos.RIGHT);
            grid.getChildren().add(Company);
            Label Genre  = new Label("ジャンル：");
            GridPane.setConstraints(Genre, 0, 3);
            GridPane.setHalignment(Genre, HPos.RIGHT);
            grid.getChildren().add(Genre);
            Label ReadStart  = new Label("読書開始日：");
            GridPane.setConstraints(ReadStart, 0, 4);
            GridPane.setHalignment(ReadStart, HPos.RIGHT);
            grid.getChildren().add(ReadStart);
            Label ReadEnd  = new Label("読書終了日：");
            GridPane.setConstraints(ReadEnd, 0, 5);
            GridPane.setHalignment(ReadEnd, HPos.RIGHT);
            grid.getChildren().add(ReadEnd);
            Label Memo  = new Label("メモ：");
            GridPane.setConstraints(Memo, 0, 6);
            GridPane.setHalignment(Memo, HPos.RIGHT);
            grid.getChildren().add(Memo);

            Label ResTitle  = new Label(SearchResult[i].title);
            GridPane.setConstraints(ResTitle, 1, 0);
            grid.getChildren().add(ResTitle);
            ResTitle.setStyle("-fx-wrap-text: true;");
            ResTitle.setMaxWidth(440);
            Label ResAuthor  = new Label(SearchResult[i].author);
            GridPane.setConstraints(ResAuthor, 1, 1);
            grid.getChildren().add(ResAuthor);
            ResAuthor.setStyle("-fx-wrap-text: true;");
            ResAuthor.setMaxWidth(440);
            Label ResCompany  = new Label(SearchResult[i].company);
            GridPane.setConstraints(ResCompany, 1, 2);
            grid.getChildren().add(ResCompany);
            ResCompany.setStyle("-fx-wrap-text: true;");
            ResCompany.setMaxWidth(440);
            Label ResGenre  = new Label(SearchResult[i].genre);
            GridPane.setConstraints(ResGenre, 1, 3);
            grid.getChildren().add(ResGenre);
            Label ResReadStart  = new Label(SearchResult[i].readstart);
            GridPane.setConstraints(ResReadStart, 1, 4);
            grid.getChildren().add(ResReadStart);
            Label ResReadEnd  = new Label(SearchResult[i].readend);
            GridPane.setConstraints(ResReadEnd, 1, 5);
            grid.getChildren().add(ResReadEnd);
            Label ResMemo  = new Label(SearchResult[i].memo);
            ResMemo.setStyle("-fx-wrap-text: true;");
            ResMemo.setMaxWidth(440);
	        GridPane.setConstraints(ResMemo, 1, 6);
	        grid.getChildren().add(ResMemo);

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

	        //ライン
	        Line line = new Line();
	        line.setEndX(735);
	        line.setStyle("-fx-stroke:  #1e90ff;");

	        vbox.getChildren().add(grid);
	        vbox.getChildren().add(line);
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