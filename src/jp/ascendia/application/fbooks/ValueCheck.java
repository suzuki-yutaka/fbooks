package jp.ascendia.application.fbooks;

/**
 * 入力された書籍情報をチェックするクラス
 * @version 1.0
 * @author Yutaka Suzuki
 */
public class ValueCheck {

  /**
   * 入力値チェック
   *
   * @param book 入力された書籍情報
   * @return エラーメッセージを返す
   */
  public String inputValueCheck(Book book) {

    //必須入力項目チェック
    if ("".equals(book.getTitle()) || "".equals(book.getAuthor()) || "".equals(book.getCompany())
        || book.getGenre() == null) {
      return "必須の入力項目が入力されていません。";
    }

    //空白文字（半角スペース、全角スペース、タブ、改行、復帰）チェック
    if (book.getTitle().matches("\\s*|　+") || book.getAuthor().matches("\\s+|　+") ||
        book.getCompany().matches("\\s*|　+") || book.getMemo().matches("\\s+|　+")) {
      return "スペースのみの入力はできません。";
    }

    //読書開始日が読書終了日より大きい場合のチェック
    if (book.getReadStart() != null && book.getReadEnd() != null) {
      if (book.getReadStart().compareTo(book.getReadEnd()) > 0) {
        return "読書開始日は読書終了日以前に設定してください。";
      }
    }

    return "OK";
  }

  /**
   * 全件検索のチェック
   *
   * @param book 入力された検索文字
   * @return 0:検索値有り、1:全件検索
   */
  public int searchAllCheck(Book book) {
    if (!"".equals(book.getTitle()) || !"".equals(book.getAuthor()) ||
        book.getGenre() != null || book.getReadStart() != null || book.getReadEnd() != null) {
      return 0;
    } else {
      return 1;
    }
  }
}