package jp.ascendia.application.fbooks;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class SearchResController extends AnchorPane implements Initializable {

    /**
     * コンストラクタ
     */
    //検索結果出力
    public SearchResController(Book[] SearchResult, String[] SearchText, int allflg) throws ClassNotFoundException {
        //メニューボタン
        Scene scene = Main.stage.getScene();
        String style = Main.class.getResource("../css/Main.css").toExternalForm();
        scene.getStylesheets().add(style);

    	HBox menu = new HBox();
    	menu.setStyle("-fx-background-color: #eeeeee;");
    	AnchorPane.setLeftAnchor(menu, 0.0);
    	AnchorPane.setRightAnchor(menu, 0.0);
    	Label home = new Label("ホーム");
    	home.setPrefWidth(70);
    	home.setPrefHeight(30);
    	home.setAlignment(Pos.CENTER);
    	home.setStyle("-fx-font-size: 14px; -fx-underline: true; -fx-text-fill: #1e90ff;");
    	Label add = new Label("登録");
    	add.setPrefWidth(70);
    	add.setPrefHeight(30);
    	add.setAlignment(Pos.CENTER);
    	add.setStyle("-fx-font-size: 14px; -fx-underline: true; -fx-text-fill: #1e90ff;");
    	Label search = new Label("検索");
    	search.setPrefWidth(70);
    	search.setPrefHeight(30);
    	search.setAlignment(Pos.CENTER);
    	search.setStyle("-fx-font-size: 14px; -fx-underline: true; -fx-text-fill: #1e90ff;");

    	home.setOnMouseClicked(new EventHandler<MouseEvent>() {
        	@Override
        	public void handle(MouseEvent mouseEvent) {
        		Main.getInstance().MainController();
        	}
        });

    	add.setOnMouseClicked(new EventHandler<MouseEvent>() {
        	@Override
        	public void handle(MouseEvent mouseEvent) {
        		Main.getInstance().AddController();
        	}
        });

    	search.setOnMouseClicked(new EventHandler<MouseEvent>() {
        	@Override
        	public void handle(MouseEvent mouseEvent) {
        		Main.getInstance().SearchController();
        	}
        });
    	menu.getChildren().add(home);
    	menu.getChildren().add(add);
    	menu.getChildren().add(search);
    	this.getChildren().add(menu);

        //検索結果件数
        HBox hbox = new HBox();
        Label Num  = new Label(Integer.toString(SearchResult.length));
        Label NumText  = new Label("件見つかりました。");
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

        for (int i = 0; i < SearchResult.length; i++) {
            //検索結果格納、グリッドパネル作成
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
	        		EditController controller = new EditController(SearchResult, Integer.parseInt(Edit.getId()), SearchText);
	                Scene editScene = new Scene(controller);
		            Stage stage = new Stage();
		            Main.fixStage = stage;
		            stage.setTitle("検索結果");
		            stage.setScene(editScene);
		            stage.setX(400);
		            stage.setY(100);
		            stage.setWidth(800);
		            stage.setHeight(600);
		            stage.initModality(Modality.WINDOW_MODAL);
		            stage.initOwner(Main.stage);
		            stage.show();
	        	}
	        });

	        //削除処理
	        clear.setOnAction(new EventHandler<ActionEvent>() {
	        	@Override
	        	public void handle(ActionEvent actionEvent) {
	                DatabaseFbooks db = new DatabaseFbooks();
	                db.deleteBook(SearchResult, Integer.parseInt(Edit.getId()));

	                Book[] SearchResult = db.searchBook(SearchText, allflg);
	                try {
						Main.getInstance().SearchResController(SearchResult, SearchText, allflg);
					} catch (ClassNotFoundException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					}
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
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}