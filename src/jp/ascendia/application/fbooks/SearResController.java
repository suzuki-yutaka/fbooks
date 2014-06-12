package jp.ascendia.application.fbooks;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;


public class SearResController extends AnchorPane implements Initializable {

    /** ラベルに表示するテキスト */
    private final String[] labelText = new String[10];

    /** ラベル */
    @FXML
    private Label Titlelabel;
    @FXML
    private Label Authorlabel;
    @FXML
    private Label Companylabel;
    @FXML
    private Label PubDaylabel;
    @FXML
    private Label ReadStartlabel;
    @FXML
    private Label ReadEndlabel;
    @FXML
    private Label Memolabel;
    @FXML
    private Label FixTitlelabel;
    @FXML
    private Label FixAuthorlabel;
    @FXML
    private Label FixCompanylabel;
    @FXML
    private Label FixPubDaylabel;
    @FXML
    private Label FixReadStartlabel;
    @FXML
    private Label FixReadEndlabel;
    @FXML
    private Label FixMemolabel;

    /**
     * コンストラクタ
     */
    public SearResController(String[] SearchText) throws ClassNotFoundException {
        labelText[0] = SearchText[0];
        labelText[1] = SearchText[1];
        labelText[2] = SearchText[2];
        labelText[3] = SearchText[3];
        labelText[4] = SearchText[4];
        labelText[5] = SearchText[5];
        labelText[6] = SearchText[6];

        loadFXML();
    }

    /**
     * FXMLのロード
     */
    private void loadFXML() {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("SearchResPage.fxml"));
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
        Titlelabel.setText("タイトル");
        Authorlabel.setText("著者");
        Companylabel.setText("出版社");
        PubDaylabel.setText("出版日");
        ReadStartlabel.setText("読書開始日");
        ReadEndlabel.setText("読書終了日");
        Memolabel.setText("メモ");
        FixTitlelabel.setText(labelText[0]);
        FixAuthorlabel.setText(labelText[1]);
        FixCompanylabel.setText(labelText[2]);
        FixPubDaylabel.setText(labelText[3]);
        FixReadStartlabel.setText(labelText[4]);
        FixReadEndlabel.setText(labelText[5]);
        FixMemolabel.setText(labelText[6]);
    }

    /**
     * ボタンクリックアクション
     */

    //編集ページへ
    @FXML
    protected void handleButtonActionEditPage() {
        Main.getInstance().sendEditPageController();
    }

    //削除処理
    @FXML
    protected void handleButtonActionDel() throws ClassNotFoundException {
        DatabaseFbooks db = new DatabaseFbooks();
        db.deleteBook(labelText);

        //確定ページへ
        Main.getInstance().sendFixController("削除されました。");
    }

    //メインページへ
    @FXML
    protected void handleButtonAction() {
        Main.getInstance().sendMainController();
    }
}