package jp.ascendia.application.fbooks;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddController extends AnchorPane implements Initializable {

  /**
   * コンストラクタ
   */
  public AddController() {
    loadFXML();
  }

  /**
   * FXMLのロード
   */
  private void loadFXML() {
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("AddPage.fxml"));
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
  }

  /**
   * ボタンクリックアクション
   */
  @FXML
  private TextField TitleField;
  @FXML
  private TextField AuthorField;
  @FXML
  private TextField CompanyField;
  @FXML
  private ComboBox<String> GenreCBox;
  @FXML
  private DatePicker ReadStartDate;
  @FXML
  private DatePicker ReadEndDate;
  @FXML
  private TextArea MemoArea;
  @FXML
  private Label MsgOutput;

  //登録処理
  @FXML
  protected void handleButtonActionAdd() throws ClassNotFoundException {
    Book input = new Book();
    LocalDate start = null, end = null;

    //必須入力項目が入力されていない場合、登録不可
    if ("".equals(TitleField.getText()) || "".equals(AuthorField.getText()) || "".equals(CompanyField.getText()) ||
        GenreCBox.getValue() == null) {
      MsgOutput.setText("必須の入力項目が入力されていません。");
      return;
    }

    if (ReadStartDate.getValue() != null)
      start = ReadStartDate.getValue();
    if (ReadEndDate.getValue() != null)
      end = ReadEndDate.getValue();
    //読書開始日が読書終了日より大きい場合、登録不可
    if (start != null && end != null) {
      if (start.compareTo(end) > 0) {
        MsgOutput.setText("読書開始日は読書終了日以前に設定してください。");
        return;
      }
    }

    DatabaseFbooks db = new DatabaseFbooks();
    Book searchTmp = new Book();
    //登録済みタイトルのチェック
    searchTmp.setTitle(TitleField.getText());
    Book[] searchResult = db.searchBook(searchTmp, 0);
    if (searchResult != null && searchResult.length > 0) {
      MsgOutput.setText("登録済みのタイトルです。");
      return;
    }

    input.setAll(TitleField.getText(), AuthorField.getText(), CompanyField.getText(),
        GenreCBox.getValue().toString(), start, end, MemoArea.getText());

    //データベース登録
    db.addBook(input);

    //確定ウィンドウ表示
    FixController controller = new FixController("登録されました！", null);
    Scene fixScene = new Scene(controller);
    Stage stage = new Stage();
    Main.fixStage = stage;
    stage.setWidth(400);
    stage.setHeight(200);
    stage.setTitle("登録完了");
    stage.setScene(fixScene);
    stage.initModality(Modality.WINDOW_MODAL);
    stage.initOwner(Main.stage);
    stage.show();
  }

  //メインページへ
  @FXML
  protected void handleButtonActionHomePage() {
    Main.getInstance().mainController();
  }

  //検索ページへ
  @FXML
  protected void handleButtonActionSearchPage() {
    Main.getInstance().searchController();
  }
}