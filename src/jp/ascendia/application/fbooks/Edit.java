package jp.ascendia.application.fbooks;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * 書籍情報の編集時に使用するクラス
 * @version 1.0
 * @author Yutaka Suzuki
 */
public class Edit extends FxmlLoad implements Initializable {

  /** 編集対象の書籍情報 */
  private static Book initBook[];

  /** 文字入力用 */
  @FXML
  private TextField titleField;
  @FXML
  private TextField authorField;
  @FXML
  private TextField companyField;
  @FXML
  private ComboBox<String> genreCBox;
  @FXML
  private DatePicker readStartDate;
  @FXML
  private DatePicker readEndDate;
  @FXML
  private TextArea memoArea;

  /** メッセージ出力用 */
  @FXML
  private Label msgOutput;

  /**
   * コンストラクタ
   *
   * @param title 編集対象の書籍タイトル
   * @param st 検索文字
   * @param fxml fxmlファイル名
   */
  public Edit(String title, Book st, String fxml) {
    super(title, st, fxml);
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    //データベース検索
    initBook = new DatabaseFbooks().searchBook(new Book("", text, "", "", "", "", "", ""));

    if (initBook != null && initBook.length > 0) {
      titleField.setText(initBook[0].getTitle());
      authorField.setText(initBook[0].getAuthor());
      companyField.setText(initBook[0].getCompany());
      genreCBox.setValue(initBook[0].getGenre());
      if (initBook[0].getReadStart() != null && !"".equals(initBook[0].getReadStart()))
        readStartDate.setValue(LocalDate.parse(initBook[0].getReadStart()));
      if (initBook[0].getReadEnd() != null && !"".equals(initBook[0].getReadStart()))
        readEndDate.setValue(LocalDate.parse(initBook[0].getReadEnd()));
      memoArea.setText(initBook[0].getMemo());
    }
  }

  /**
   * ボタンクリックアクション
   * 編集処理
   */
  @FXML
  protected void handleButtonActionEdit() {

    //入力値の取得
    Book input = new Book(titleField.getText(), authorField.getText(), companyField.getText(),
        genreCBox.getValue(), readStartDate.getValue(), readEndDate.getValue(), memoArea.getText());

    //入力値チェック
    String outputText = new ValueCheck().inputValueCheck(input);
    if (!outputText.equals("OK")) {
      msgOutput.setText(outputText);
      return;
    }

    DatabaseFbooks db = new DatabaseFbooks();
    //編集画面でタイトルを変更した場合
    if (input.getTitle().compareTo(initBook[0].getTitle()) != 0) {

      //データベース検索
      Book[] searchResult = db.searchBook(new Book("", input.getTitle(), "", "", "", "", "", ""));

      //登録済みタイトルのチェック
      if (searchResult != null && searchResult.length > 0) {
        //データベースに同一タイトルが存在している場合
        if (!searchResult[0].getId().equals(initBook[0].getId())) {
          msgOutput.setText("登録済みのタイトルです。");
          return;
        }
      }
    }

    //データベース更新
    input.setId(initBook[0].getId());
    db.updateBook(input);

    //編集完了ウィンドウ表示
    try {
      Controller.getInstance().editFixController("編集内容が反映されました。", searchText);
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
    Controller.getInstance().closeWindow();
  }
}