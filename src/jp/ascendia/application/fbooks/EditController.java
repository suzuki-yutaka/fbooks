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

    //入力値の取得
    input.setAll(TitleField.getText(), AuthorField.getText(), CompanyField.getText(),
        GenreCBox.getValue(), ReadStartDate.getValue(), ReadEndDate.getValue(), MemoArea.getText());

    //入力値チェック
    ValueCheck vc = new ValueCheck();
    String outputText = vc.inputValueCheck(input);
    if (!outputText.equals("OK")) {
      MsgOutput.setText(outputText);
      return;
    }

    DatabaseFbooks db = new DatabaseFbooks();
    //編集画面でタイトルを変更した場合
    if (input.getTitle().compareTo(initText.getTitle()) != 0) {
      //データベース検索
      Book searchTmp = new Book();
      searchTmp.setTitle(input.getTitle());
      Book[] searchResult = db.searchBook(searchTmp, 0);

      //登録済みタイトルのチェック
      if (searchResult != null && searchResult.length > 0) {
        //データベースに同一タイトルが存在している場合
        if (!searchResult[0].id.equals(initText.getId())) {
          MsgOutput.setText("登録済みのタイトルです。");
          return;
        }
      }
    }

    //データベース更新
    input.setId(initText.getId());
    db.updateBook(input);

    //編集完了ウィンドウ表示
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