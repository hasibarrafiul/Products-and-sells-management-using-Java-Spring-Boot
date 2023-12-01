package product;
import java.io.IOException;
import java.sql.SQLException;

import dao.ProductDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


//これがURLです
@jakarta.servlet.annotation.WebServlet("/search")
public class ShowProducts extends jakarta.servlet.http.HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductDao dao;
	public String errors = "";
	
	//このメソッドはデータベースを呼び出し、製品のリストを取得しようとします。
	public ShowProducts() {
        super();
        
        try {
			dao = new ProductDao();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
	

	//これは取得リクエストを処理するコントローラーです
	/**
	* @param request これはクライアントから送信された http リクエストです。
	* @param response これはサーバーから送信された http 応答です
	* @return このメソッドはビュー JSP ファイルに値を返します。
	*/
	protected void doGet(HttpServletRequest req,
			HttpServletResponse res)
			throws ServletException, IOException {
		
		try {
			String search = req.getParameter("search");
			String savedOnlyName = req.getParameter("savedonlyname");
			String concurrncy = req.getParameter("concurrncy");
			String notDeleted = req.getParameter("notdeleted");
			String deletedBeforeUpdate = req.getParameter("deletedbeforeupdate");
			String alreadySold = req.getParameter("alreadysold");
			
			if(savedOnlyName != null && savedOnlyName.equals("1")) {
				errors = "セール中のため価格は編集できません。 ということで名前だけ更新しました。\n";
				req.setAttribute("errors", errors);
			}
			
			if(concurrncy != null && concurrncy.equals("1")) {
				errors = "他のユーザーが同時に値を更新していたため、値を更新できませんでした。\n";
				req.setAttribute("errors", errors);
			}
			
			if(notDeleted != null && notDeleted.equals("1")) {
				errors = "アイテムは別のセッションによってすでに削除されています。\n";
				req.setAttribute("errors", errors);
			}
			
			if(deletedBeforeUpdate != null && deletedBeforeUpdate.equals("1")) {
				errors = "アップデート前にデータが削除されてしまった。\n";
				req.setAttribute("errors", errors);
			}
			
			if(alreadySold != null && alreadySold.equals("1")) {
				errors = "この商品はすでに販売されているため、削除できません。\n";
				req.setAttribute("errors", errors);
			}
			
			res.getWriter().append("Served at: ").append(req.getContextPath());
			
			RequestDispatcher view = req.getRequestDispatcher("showProducts.jsp");
			
			//検索ボックスが空の場合はすべての値が返され、それ以外の場合は検索値が返されます。
			
				if(search == null || search == "") {
					try {
						req.setAttribute("products", dao.getAllProducts());
					} catch (Exception e) {
						errors = errors + "SQL例外が見つかりました \n";
						req.setAttribute("errors", errors);
					}
				} else {
					if(dao.getSearchedProducts(search).size() == 0) {
						errors = errors + "何もデータが見つかりませんでした。\n";
						req.setAttribute("errors", errors);
						req.setAttribute("products", dao.getAllProducts());
					}
					else {
						req.setAttribute("products", dao.getSearchedProducts(search));
					}
				}
				view.forward(req, res);
			} catch (SQLException e) {
				errors = errors + "SQL例外が見つかりました \n";
				req.setAttribute("errors", errors);
			} catch (NullPointerException e) {
				errors = errors + "ヌルポインタ例外発見 \n";
				req.setAttribute("errors", errors);
			} catch (NumberFormatException e) {
				errors = errors + "番号フォーマットの例外が見つかった \n";
				req.setAttribute("errors", errors);
			} catch (ServletException e) {
				errors = errors + "サーブレット例外が見つかりました \n";
				req.setAttribute("errors", errors);
			} catch (IOException e) {
				errors = errors + "IO 例外が見つかった \n";
				req.setAttribute("errors", errors);
			}
		
	}
}