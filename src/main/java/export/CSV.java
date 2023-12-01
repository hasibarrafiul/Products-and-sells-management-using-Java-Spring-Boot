package export;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dao.ProductDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.TotalSellByProduct;


//これがURLです
@jakarta.servlet.annotation.WebServlet("/export")
public class CSV extends jakarta.servlet.http.HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductDao dao;
	public String errors = "";
	List<TotalSellByProduct> sales = new ArrayList<TotalSellByProduct>();
	LocalDate date = java.time.LocalDate.now();
	
	//このメソッドはデータベースを呼び出し、製品のリストを取得しようとします。
	public CSV() {
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
			RequestDispatcher view = req.getRequestDispatcher("export.jsp");
			//req.setAttribute("products", dao.getAllProducts());
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
			}
		
	}
	
	protected void doPost(HttpServletRequest req,
			HttpServletResponse res)
			throws ServletException, IOException {

			
			String date = req.getParameter("month");
			int year = Integer.parseInt(date.split("-")[0]);
			int month = Integer.parseInt(date.split("-")[1]);
			if(year < 2020) {
				RequestDispatcher view = req.getRequestDispatcher("export.jsp");
				errors = "この年は 2020 年以上である必要があります。 \n";
				req.setAttribute("errors", errors);
				view.forward(req, res);
			}
			
			if((year > Integer.parseInt(java.time.LocalDate.now().toString().split("-")[0])) || (year == Integer.parseInt(java.time.LocalDate.now().toString().split("-")[0]) && 
					month > Integer.parseInt(java.time.LocalDate.now().toString().split("-")[1]))) {
				RequestDispatcher view = req.getRequestDispatcher("export.jsp");
				errors = "月は今月以下である必要があります。\n";
				req.setAttribute("errors", errors);
				view.forward(req, res);
				
			} else {
				try{
					res.setContentType("text/csv");
				    res.setHeader("Content-Disposition", "attachment; filename=\"sales_" + date + ".csv\"");
				    OutputStream outputStream = res.getOutputStream();
			    	int valueFound = dao.getTotalSellByMonth(date, outputStream);
			    	if(valueFound == 1) {
			    		
			    		res.sendRedirect("export");
			    	} else {
			    		//outputStream.flush();
				        //outputStream.close();
			    		System.out.println("not found");
			    		res.sendRedirect("error");
			    	}
			        
			    }catch (NullPointerException e) {
					errors = errors + "ヌルポインタ例外発見 \n";
					req.setAttribute("errors", errors);
				} catch (NumberFormatException e) {
					errors = errors + "番号フォーマットの例外が見つかった \n";
					req.setAttribute("errors", errors);
				} catch (IOException e) {
					errors = errors + "IO 例外が見つかった \n";
					req.setAttribute("errors", errors);
				} catch (SQLException e) {
					errors = errors + "SQL例外が見つかりました \n";
					req.setAttribute("errors", errors);
				}
			}
	}
}