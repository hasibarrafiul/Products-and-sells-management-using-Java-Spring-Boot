package product;
import java.io.IOException;
import java.sql.SQLException;

import dao.ProductDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


//これがURLです
@jakarta.servlet.annotation.WebServlet("/addproduct")
public class AddProduct extends jakarta.servlet.http.HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductDao dao;
	public String errors = "";
	
	//このメソッドはデータベースを呼び出し、製品のリストを取得しようとします。
	public AddProduct() {
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
		res.getWriter().append("Served at: ").append(req.getContextPath());
		
		RequestDispatcher view = req.getRequestDispatcher("addProducts.jsp");
		
		
		//req.setAttribute("products", dao.getAllProducts());
		
		view.forward(req, res);
		} catch (ServletException e) {
			errors = errors + "サーブレット例外が見つかりました \n" ;
			req.setAttribute("errors", errors);
		} catch (IOException e) {
			errors = errors + "IO 例外が見つかった \n";
			req.setAttribute("errors", errors);
		}
	}
	
	protected void doPost(HttpServletRequest req,
			HttpServletResponse res)
			throws ServletException, IOException {
		String productName = req.getParameter("productname");
		String price = req.getParameter("productprice");
		int productprice = Integer.parseInt(price);
		
		try {
			dao.connection.setAutoCommit(false);
			dao.addProduct(productName, productprice);
			dao.connection.commit();
			res.sendRedirect("search");
		} catch (IOException e) {
			try {
				dao.connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			RequestDispatcher view = req.getRequestDispatcher("addProducts.jsp");
			errors = errors + "IO 例外が見つかった \n";
			req.setAttribute("errors", errors);
			view.forward(req, res);
		} catch (SQLException e) {
			try {
				dao.connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			RequestDispatcher view = req.getRequestDispatcher("addProducts.jsp");
			errors = errors + "SQL例外が見つかりました \n";
			req.setAttribute("errors", errors);
			//e.printStackTrace();
			view.forward(req, res);
		} catch (NullPointerException e) {
			try {
				dao.connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			RequestDispatcher view = req.getRequestDispatcher("addProducts.jsp");
			errors = errors + "ヌルポインタ例外発見 \n";
			req.setAttribute("errors", errors);
			view.forward(req, res);
		} catch (NumberFormatException e) {
			try {
				dao.connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			RequestDispatcher view = req.getRequestDispatcher("addProducts.jsp");
			errors = errors + "番号フォーマットの例外が見つかった \n";
			req.setAttribute("errors", errors);
			view.forward(req, res);
		}
	}
}