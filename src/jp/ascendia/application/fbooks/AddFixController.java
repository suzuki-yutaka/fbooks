package jp.ascendia.application.fbooks;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;


public class AddFixController extends AnchorPane implements Initializable {

	/** ラベルに表示するテキスト */
    private static String msgText;

    /** ラベル */
    @FXML
    private Label FixField;

    /**
     * コンストラクタ
     */
    public AddFixController(String text) {
    	msgText = text;
        loadFXML();
    }

    /**
     * FXMLのロード
     */
    private void loadFXML() {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("AddFixPage.fxml"));
        fxmlLoader.setRoot(this);

        // 自分自身をコントロールとして設定
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	FixField.setText(msgText);
    	FixField.setStyle("-fx-font-size: 24px");
    }

    /**
     * ボタンクリックアクション
     */

    //メインページへ
    @FXML
    protected void handleButtonAction() {
        Main.getInstance().sendMainController();
    }
    //登録ページへ
    @FXML
    protected void AddPagehandleButtonAction() {
        Main.getInstance().sendAddPageController();
    }
}