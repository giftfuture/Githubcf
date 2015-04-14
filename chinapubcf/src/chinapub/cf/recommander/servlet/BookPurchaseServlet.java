package chinapub.cf.recommander.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chinapub.cf.recommander.model.BookList;
import chinapub.cf.recommander.model.table.RatingTable;

public class BookPurchaseServlet extends HttpServlet {
	/**
	 * @date 8 Oct,2014
	 * @author Nexus
	 */
	private static final long serialVersionUID = 1364867223755956624L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {

		String userID = request.getParameter(Parameters.USER_ID);
		BookList books;
		if (userID != null) {
			books = RatingTable.getBooksByUserID(userID);
		} else {
			throw new ServletException("user was not specified");
		}
		
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		doGet(request,response);
	}
}
