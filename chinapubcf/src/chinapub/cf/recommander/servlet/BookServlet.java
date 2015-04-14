package chinapub.cf.recommander.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.lang3.StringUtils;

/*import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import chinapub.cf.recommander.model.Book;*/
import chinapub.cf.recommander.model.BookList;
import chinapub.cf.recommander.model.table.RatingTable;

public class BookServlet extends HttpServlet {
	/**
	 * @date Jul 30,2014
	 * @author Fred
	 */
	private static final long serialVersionUID = -8036886827342406681L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {

		String userID = request.getParameter(Parameters.USER_ID);
		BookList books;
		if (StringUtils.isNotEmpty(userID)) {
			books = RatingTable.getBooksByUserID(userID);
		} else {
			throw new ServletException("user was not specified");
		}
		if (books != null) {
			try {
				writeJSON(response, books);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		doGet(request,response);
	}
		
	private void writeJSON(HttpServletResponse response, BookList books)
			throws IOException {
		response.setContentType("text/plain; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.print(books.toJSON());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
