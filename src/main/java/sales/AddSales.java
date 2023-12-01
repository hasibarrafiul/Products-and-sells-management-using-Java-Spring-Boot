package sales;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dao.ProductDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Sales;


//これがURLです
@jakarta.servlet.annotation.WebServlet("/addsales")
public class AddSales extends jakarta.servlet.http.HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductDao dao;
	public String errors = "";
	List<Sales> sales = new ArrayList<Sales>();
	LocalDate date = java.time.LocalDate.now();
	
	//このメソッドはデータベースを呼び出し、製品のリストを取得しようとします。
	public AddSales() {
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
			//これは、取得リクエストのすべての属性とデータを設定します。
			RequestDispatcher view = req.getRequestDispatcher("addSales.jsp");
			req.setAttribute("products", dao.getAllProducts());
			req.setAttribute("todayssales", dao.getSales(date.toString()));
			req.setAttribute("todaysdate", date);
			req.setAttribute("sales", sales);
			//System.out.println(dao.getSales(date.toString()));
			//req.setAttribute("sales", sales);
			view.forward(req, res);
			
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
			} catch (SQLException e) {
				errors = errors + "SQL例外が見つかりました \n";
				req.setAttribute("errors", errors);
			}
		
	}
	
	protected void doPost(HttpServletRequest req,
			HttpServletResponse res)
			throws ServletException, IOException {
		
		//これは、ポストリクエストのすべての属性とデータを設定します。
		String productCode = req.getParameter("productcode");
		String productQuantity = req.getParameter("productquantity");
		String savefield = req.getParameter("savefield");
		
		//this is for saving the value
		if(savefield != null && savefield.equals("Save")) {
			errors = "";
			//System.out.println("Save the value");
			for (Sales sale : sales) {
		         try {
		        	 //データを挿入できるか、数量を更新する必要があるかをチェックする。
					boolean found = dao.addSales(sale.getSalesDate(), Integer.parseInt(sale.getProductCode()), sale.getQuantity());
					if(found == false) {
						errors = "この商品は商品テーブルから削除されました。";
						req.setAttribute("errors", errors);
					}
				} catch (NumberFormatException e) {
					errors = errors + "番号フォーマットの例外が見つかった \n";
					req.setAttribute("errors", errors);
				} catch (SQLException e) {
					try {
						//これは数量を更新する。
						dao.updateSales(sale.getSalesDate(), Integer.parseInt(sale.getProductCode()), sale.getQuantity());
					} catch (NumberFormatException e1) {
						errors = errors + "番号フォーマットの例外が見つかった \n";
						req.setAttribute("errors", errors);
					} catch (SQLException e1) {
						errors = errors + "SQL例外が見つかりました \n";
						req.setAttribute("errors", errors);
					}
					
				}
		      }
			sales.clear();
		}
		
		//ローカルリストにデータを追加する
		Sales sale = new Sales();
		sale.setSalesDate(date.toString());
		if(productCode != null && productQuantity != null) {
			String[] separate = productCode.split(" ");
			try {
				if(dao.checkIfDeleted(Integer.parseInt(separate[0]))) {
					errors = errors + "この商品は削除されました。\n";
					req.setAttribute("errors", errors);
				} else {
					sale.setProductCode(Integer.parseInt(separate[0]));
					sale.setProductName(separate[1]);
					sale.setQuantity(Integer.parseInt(productQuantity));
					sales.add(sale);
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		//System.out.println(sales);
		RequestDispatcher view = req.getRequestDispatcher("addSales.jsp");
		//this are local data
		req.setAttribute("todaysdate", date);
		req.setAttribute("sales", sales);
		
		try {
			//これはデータベースからのデータである。
			req.setAttribute("todayssales", dao.getSales(date.toString()));
			req.setAttribute("products", dao.getAllProducts());
		} catch (SQLException e) {
			errors = errors + "SQL例外が見つかりました \n";
			req.setAttribute("errors", errors);
		}
		
		view.forward(req, res);
	}
}