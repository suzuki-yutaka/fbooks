package jp.ascendia.application.fbooks;


public class ValueCheck {

  public static String inputValueCheck(Book book) {

    //必須入力項目が入力されていない場合、登録不可
    if ("".equals(book.getTitle()) || "".equals(book.getAuthor()) || "".equals(book.getCompany()) || book.getGenre() == null) {
      return "必須の入力項目が入力されていません。";
    }

    //読書開始日が読書終了日より大きい場合、登録不可
    if (book.getReadStart() != null && book.getReadEnd() != null) {
      if (book.getReadStart().compareTo(book.getReadEnd()) > 0) {
        return "読書開始日は読書終了日以前に設定してください。";
      }
    }

    return "OK";
  }

  public static int searchAllCheck(Book book) {
    //何れかの検索値が入力されていた場合
    if (!"".equals(book.getTitle()) || !"".equals(book.getAuthor()) ||
        book.getGenre() != null || book.getReadStart() != null || book.getReadEnd() != null) {
      return 0;
    } else {
      return 1;
    }
  }
}