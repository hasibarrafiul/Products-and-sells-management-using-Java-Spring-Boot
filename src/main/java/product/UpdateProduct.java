package product;
import java.io.IOException;
import java.sql.SQLException;

import dao.ProductDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Product;


//これがURLです
@jakarta.servlet.annotation.WebServlet("/update")
public class UpdateProduct extends jakarta.servlet.http.HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductDao dao;
	private String errors = "";
	private String productCode;
	Product product = new Product();
	
	//このメソッドはデータベースを呼び出し、製品のリストを取得しようとします。
	public UpdateProduct() {
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
			productCode = req.getParameter("product_code");
			
			res.getWriter().append("Served at: ").append(req.getContextPath());
			
			RequestDispatcher view = req.getRequestDispatcher("updateProduct.jsp");
			
			//検索ボックスが空の場合はすべての値が返され、それ以外の場合は検索値が返されます。
			
				if(productCode == null || productCode == "") {
					errors = errors + "製品が見つかりませんでした。\n";
					req.setAttribute("errors", errors);
				} else {
					//System.out.println(dao.getProduct(productCode).getProductName());
					product = dao.getProduct(productCode);
					req.setAttribute("product", product);
						
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
	
	//これはすべての投稿リクエストを処理します
	protected void doPost(HttpServletRequest req,
			HttpServletResponse res)
			throws ServletException, IOException {
		
		String productName = req.getParameter("productname");
		String price = req.getParameter("productprice");
		String deletefield = req.getParameter("deletefield");
		int productprice = Integer.parseInt(price);
		int code = Integer.parseInt(productCode);
		
		try {
			dao.connection.setAutoCommit(false);
			
			//これは削除ボタンが押されたかどうかをチェックします
			if(deletefield.equals("Delete")) {
				
				int checkIfExists = dao.deleteProduct(code);
				if(checkIfExists == 1) {
					if(dao.checkIfSold(code)) {
						res.sendRedirect("search?alreadysold=1");
					} 
					else {
						dao.connection.commit();
						res.sendRedirect("search");
					}
				} else {
					res.sendRedirect("search?notdeleted=1");
				}
				
			} else {
				
				//この条件は同時実行性をチェックします
				if(product.getUpdateDateTime().equals(dao.getUpdateTime(code))) {
					String priceString = product.getPrice();
					priceString = priceString.replaceAll("[^\\d.]", "");
					//この条件は、ユーザーが同じ値を送信しているかどうかをチェックします
					if(!product.getProductName().equals(productName) || !(Integer.parseInt(priceString) == productprice)) {
						
						int checkIfUpdates = dao.updateProduct(productName, productprice, code);
						
						//この条件により、商品がすでに販売されているかどうかが確認されます。 販売済みの商品のみ価格が更新されます。
						if(checkIfUpdates == 0) {
							int checkIfUpdatesOnlyName = dao.updateProductNameOnly(productName, code);
							dao.connection.commit();
							if(Integer.parseInt(priceString) == productprice) {
								res.sendRedirect("search");
							} else {
								if(checkIfUpdatesOnlyName == 1) {
									res.sendRedirect("search?savedonlyname=1");
								} else {
									res.sendRedirect("search?deletedbeforeupdate=1");
								}
							}
							
						} else {
							dao.connection.commit();
							res.sendRedirect("search");
						}
						
					} else {
						//System.out.println("Same price");
						res.sendRedirect("search");
					}
				} else {
					res.sendRedirect("search?concurrncy=1");
					System.out.println("Someone edited");
				}
			}
		} catch (IOException e) {
			try {
				dao.connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			RequestDispatcher view = req.getRequestDispatcher("updateProduct.jsp");
			errors = errors + "IO 例外が見つかった \n";
			req.setAttribute("errors", errors);
			view.forward(req, res);
		} catch (SQLException e) {
			try {
				dao.connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			RequestDispatcher view = req.getRequestDispatcher("updateProduct.jsp");
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
			RequestDispatcher view = req.getRequestDispatcher("updateProduct.jsp");
			errors = errors + "ヌルポインタ例外発見 \n";
			req.setAttribute("errors", errors);
			view.forward(req, res);
		} catch (NumberFormatException e) {
			try {
				dao.connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			RequestDispatcher view = req.getRequestDispatcher("updateProduct.jsp");
			errors = errors + "番号フォーマットの例外が見つかった \n";
			req.setAttribute("errors", errors);
			view.forward(req, res);
		}
	}
}