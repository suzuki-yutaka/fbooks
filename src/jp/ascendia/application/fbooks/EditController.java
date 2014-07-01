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

/**
 * 書籍情報の編集時に使用するクラス
 * @version 1.0
 * @author Yutaka Suzuki
 */
public class EditController extends AnchorPane implements Initializable {

  /** 編集対象の図書情報 */
  private static Book initBook;

  /** 検索情報 */
  private static Book searchText;

  /** 文字入力用 */
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

  /** メッセージ出力用 */
  @FXML
  private Label MsgOutput;

  /**
   * コンストラクタ
   *
   * @param searchResult 検索結果
   * @param st 検索文字
   */
  public EditController(Book searchResult, Book st) {
    initBook = searchResult;
    searchText = st;

    loadFXML();
  }

  /**
   * FXMLのロード
   */
  private void loadFXML() {

    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("EditPage.fxml"));
    fxmlLoader.setRoot(this);

    fxmlLoader.setController(this);

    try {
      fxmlLoader.load();
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    TitleField.setText(initBook.getTitle());
    AuthorField.setText(initBook.getAuthor());
    CompanyField.setText(initBook.getCompany());
    GenreCBox.setValue(initBook.getGenre());
    if (initBook.getReadStart() != null && !"".equals(initBook.getReadStart()))
      ReadStartDate.setValue(LocalDate.parse(initBook.getReadStart()));
    if (initBook.getReadEnd() != null && !"".equals(initBook.getReadStart()))
      ReadEndDate.setValue(LocalDate.parse(initBook.getReadEnd()));
    MemoArea.setText(initBook.getMemo());
  }

  /**
   * ボタンクリックアクション
   * 編集処理
   */
  @FXML
  protected void handleButtonActionEdit() {
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
    if (input.getTitle().compareTo(initBook.getTitle()) != 0) {
      //データベース検索
      Book searchTmp = new Book();
      searchTmp.setTitle(input.getTitle());
      Book[] searchResult = db.searchBook(searchTmp, 0);

      //登録済みタイトルのチェック
      if (searchResult != null && searchResult.length > 0) {
        //データベースに同一タイトルが存在している場合
        if (!searchResult[0].getId().equals(initBook.getId())) {
          MsgOutput.setText("登録済みのタイトルです。");
          return;
        }
      }
    }

    //データベース更新
    input.setId(initBook.getId());
    db.updateBook(input);

    //編集完了ウィンドウ表示
    try {
      Main.getInstance().fixController("編集内容が反映されました。", searchText);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * ボタンクリックアクション
   * 編集ウィンドウを閉じる
   */
  @FXML
  protected void handleButtonActionClose() {
    Main.fixStage.getScene().getWindow().hide();
  }
}