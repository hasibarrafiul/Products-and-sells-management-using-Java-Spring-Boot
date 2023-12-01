package errors;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@jakarta.servlet.annotation.WebServlet("/error")
public class NoDataFound extends jakarta.servlet.http.HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	protected void doGet(HttpServletRequest req,
			HttpServletResponse res)
			throws ServletException, IOException {
		
		try {
			RequestDispatcher view = req.getRequestDispatcher("noDataFound.jsp");
			view.forward(req, res);
			
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
}