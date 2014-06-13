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
    private Book[] labelText = null;

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

    @FXML
    private Label Titlelabel1;
    @FXML
    private Label Authorlabel1;
    @FXML
    private Label Companylabel1;
    @FXML
    private Label PubDaylabel1;
    @FXML
    private Label ReadStartlabel1;
    @FXML
    private Label ReadEndlabel1;
    @FXML
    private Label Memolabel1;
    @FXML
    private Label FixTitlelabel1;
    @FXML
    private Label FixAuthorlabel1;
    @FXML
    private Label FixCompanylabel1;
    @FXML
    private Label FixPubDaylabel1;
    @FXML
    private Label FixReadStartlabel1;
    @FXML
    private Label FixReadEndlabel1;
    @FXML
    private Label FixMemolabel1;

    /**
     * コンストラクタ
     */
    public SearResController(Book[] bookArray) throws ClassNotFoundException {
        labelText = bookArray.clone();
        for (int i = 0; i < bookArray.length; i++) {
            labelText[i].title = bookArray[i].getTitle();
            labelText[i].author = bookArray[i].getAuthor();
            labelText[i].company = bookArray[i].getCompany();
            labelText[i].publishday = bookArray[i].getPublishday();
            labelText[i].readstart = bookArray[i].getReadstart();
            labelText[i].readend = bookArray[i].getReadend();
            labelText[i].memo = bookArray[i].getMemo();
        }

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

        FixTitlelabel.setText(labelText[0].title);
        FixAuthorlabel.setText(labelText[0].author);
        FixCompanylabel.setText(labelText[0].company);
        FixPubDaylabel.setText(labelText[0].publishday);
        FixReadStartlabel.setText(labelText[0].readstart);
        FixReadEndlabel.setText(labelText[0].readend);
        FixMemolabel.setText(labelText[0].memo);

        if (labelText.length > 1) {
            Titlelabel1.setText("タイトル");
            Authorlabel1.setText("著者");
            Companylabel1.setText("出版社");
            PubDaylabel1.setText("出版日");
            ReadStartlabel1.setText("読書開始日");
            ReadEndlabel1.setText("読書終了日");
            Memolabel1.setText("メモ");

            FixTitlelabel1.setText(labelText[1].title);
            FixAuthorlabel1.setText(labelText[1].author);
            FixCompanylabel1.setText(labelText[1].company);
            FixPubDaylabel1.setText(labelText[1].publishday);
            FixReadStartlabel1.setText(labelText[1].readstart);
            FixReadEndlabel1.setText(labelText[1].readend);
            FixMemolabel1.setText(labelText[1].memo);
        }
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