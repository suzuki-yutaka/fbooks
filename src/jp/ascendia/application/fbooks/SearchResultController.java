package jp.ascendia.application.fbooks;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * 検索結果出力に使用するクラス
 * @version 1.0
 * @author Yutaka Suzuki
 */
public class SearchResultController extends AnchorPane implements Initializable {

  /** 検索結果 */
  private static Book[] searchResult;

  /** 検索情報 */
  private static Book searchText;

  /** 編集削除対象の書籍タイトル */
  private static String chooseTitle;

  /** 文字入力用 */
  @FXML
  private TableView<Book> tableView;
  @FXML
  private TableColumn<Book, String> titleColumn;
  @FXML
  private TableColumn<Book, String> authorColumn;
  @FXML
  private TableColumn<Book, String> companyColumn;
  @FXML
  private TableColumn<Book, String> genreColumn;
  @FXML
  private TableColumn<Book, String> readStartColumn;
  @FXML
  private TableColumn<Book, String> readEndColumn;
  @FXML
  private TableColumn<Book, String> memoColumn;

  /** メッセージ出力用 */
  @FXML
  private Label msgOutput;

  /**
   * コンストラクタ
   *
   * @param sr 検索結果
   * @param st 検索文字
   */
  public SearchResultController(Book[] sr, Book st) {
    searchResult = sr;
    searchText = st;

    loadFXML();
  }

  /**
   * FXMLのロード
   */
  private void loadFXML() {

    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("SearchResult.fxml"));
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

    //カラムとBookクラスのプロパティ対応付け
    titleColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
    authorColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
    companyColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("company"));
    genreColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("genre"));
    readStartColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("readStart"));
    readEndColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("readEnd"));
    memoColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("memo"));

    //検索結果をtableViewへ登録
    for (int i = 0; i < searchResult.length; i++) {
      tableView.getItems().add(new Book(searchResult[i].getId(), searchResult[i].getTitle(),
          searchResult[i].getAuthor(), searchResult[i].getCompany(), searchResult[i].getGenre(),
          searchResult[i].getReadStart(), searchResult[i].getReadEnd(), searchResult[i].getMemo()));
    }

    //TableViewの行が選択された場合、該当するタイトルを取得する。
    TableView.TableViewSelectionModel<Book> selectionModel = tableView.getSelectionModel();
    selectionModel.selectedItemProperty().addListener(new ChangeListener<Book>() {
        @Override
        public void changed(ObservableValue<? extends Book> value, Book old, Book next) {
            chooseTitle = next.getTitle();
        }
    });

    msgOutput.setText(searchResult.length + "件見つかりました");
  }


  /**
   * ボタンクリックアクション
   * 編集処理
   */
  @FXML
  protected void handleButtonActionEdit() {
    try {
      Main.getInstance().editController(chooseTitle, searchText);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * ボタンクリックアクション
   * 削除処理
   */
  @FXML
  protected void handleButtonActionDel() {

    //データベース検索
    Book searchTmp = new Book("", chooseTitle,  "", "", "", "", "", "");
    DatabaseFbooks db = new DatabaseFbooks();
    Book[] result = db.searchBook(searchTmp, 0);

    //データ削除
    db.deleteBook(result[0]);
    try {
      Main.getInstance().fixController("削除されました。", searchText);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * ボタンクリックアクション
   * メインページへ遷移
   */
  @FXML
  protected void handleButtonActionHomePage() {
    Main.getInstance().mainController();
  }

  /**
   * ボタンクリックアクション
   * 登録ページへ遷移
   */
  @FXML
  protected void handleButtonActionAddPage() {
    Main.getInstance().addController();
  }

  /**
   * ボタンクリックアクション
   * 検索ページへ遷移
   */
  @FXML
  protected void handleButtonActionSearchPage() {
    Main.getInstance().searchController();
  }
}