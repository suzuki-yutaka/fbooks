package jp.ascendia.application.fbooks;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class EditController extends AnchorPane implements Initializable {
  private static Book initText;
  private static Book searchText;

  /**
     * コンストラクタ
     */
  public EditController(Book searchResult, Book book) {
    initText = searchResult;
    searchText = book;

    loadFXML();
  }

  /**
   * FXMLのロード
   */
  private void loadFXML() {

    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("EditPage.fxml"));
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
    TitleField.setText(initText.getTitle());
    AuthorField.setText(initText.getAuthor());
    CompanyField.setText(initText.getCompany());
    GenreCBox.setValue(initText.getGenre());
    if (initText.getReadStart() != null && !"".equals(initText.getReadStart()))
      ReadStartDate.setValue(LocalDate.parse(initText.getReadStart()));
    if (initText.getReadEnd() != null && !"".equals(initText.getReadStart()))
      ReadEndDate.setValue(LocalDate.parse(initText.getReadEnd()));
    MemoArea.setText(initText.getMemo());
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

  //編集処理
  @FXML
  protected void handleButtonActionEdit() throws ClassNotFoundException {
    Book input = new Book();
    LocalDate start = null, end = null;
    Book[] searchResult = null;

    //必須入力項目が入力されていない場合、更新不可
    if ("".equals(TitleField.getText()) || "".equals(AuthorField.getText()) || "".equals(CompanyField.getText()) ||
        GenreCBox.getValue() == null) {
      MsgOutput.setText("必須の入力項目が入力されていません。");
      return;
    }

    if (ReadStartDate.getValue() != null)
      start = ReadStartDate.getValue();
    if (ReadEndDate.getValue() != null)
      end = ReadEndDate.getValue();
    //読書開始日が読書終了日より大きい場合、更新不可
    if (start != null && end != null) {
      if (start.compareTo(end) > 0) {
        MsgOutput.setText("読書開始日は読書終了日以前に設定してください。");
        return;
      }
    }

    input.setAll(TitleField.getText(), AuthorField.getText(), CompanyField.getText(),
        GenreCBox.getValue().toString(), start, end, MemoArea.getText());

    DatabaseFbooks db = new DatabaseFbooks();
    Book searchTmp = new Book();
    //登録済みタイトルのチェック
    if (input.getTitle().compareTo(initText.getTitle()) != 0) {
      searchTmp.setTitle(input.getTitle());
      //データベース検索
      searchResult = db.searchBook(searchTmp, 0);

      if (searchResult != null && searchResult.length > 0) {
        if (!searchResult[0].id.equals(initText.getId())) {
          MsgOutput.setText("登録済みのタイトルです。");
          return;
        }
      }
    }
    input.setId(initText.getId());

    //データベース更新
    db.updateBook(input);

    FixController controller = new FixController("編集内容が反映されました。", searchText);
    Main.fixStage.setWidth(400);
    Main.fixStage.setHeight(200);
    Main.fixStage.setTitle("編集完了");
    Main.fixStage.getScene().setRoot(controller);
  }

  @FXML
  protected void handleButtonActionClose() throws ClassNotFoundException {
    //編集ウィンドウを閉じる
    Main.fixStage.getScene().getWindow().hide();
  }
}